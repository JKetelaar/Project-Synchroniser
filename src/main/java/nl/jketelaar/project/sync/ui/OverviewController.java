package nl.jketelaar.project.sync.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import nl.jketelaar.project.sync.helpers.Storage;
import nl.jketelaar.project.sync.helpers.synchronize.Pull;
import nl.jketelaar.project.sync.helpers.synchronize.Push;
import nl.jketelaar.project.sync.helpers.synchronize.Sync;
import nl.jketelaar.project.sync.models.Host;
import nl.jketelaar.project.sync.models.Project;

import java.util.Arrays;
import java.util.List;

/**
 * @author JKetelaar
 */
public class OverviewController {

    @FXML
    private TableColumn<ProjectViewModel, String> tableProjectGroup;

    @FXML
    private TableColumn<ProjectViewModel, String> tableProjectName;

    @FXML
    private TableColumn<ProjectViewModel, String> tableProjectProject;

    @FXML
    private TableColumn<ProjectViewModel, String> tableProjectDestination;

    @FXML
    private TableColumn<ProjectViewModel, String> tableProjectStatus;

    @FXML
    private TableView<ProjectViewModel> tableView;

    @FXML
    private TextField search;

    private       FilteredList<ProjectViewModel>   filteredData;
    private       ObservableList<ProjectViewModel> data;
    private final Host                             host;
    private final List<Project>                    projects;
    private final Pull                             pull;
    private final Push                             push;

    public OverviewController() {

        Storage storage = Storage.setInstance(System.getProperty("user.home") + "/.pjsync/");

        this.host = storage.getHost();
        this.projects = Arrays.asList(storage.getProjects());

        this.pull = new Pull(this.host);
        this.push = new Push(this.host);
    }

    @FXML
    public void initialize() {
        data = FXCollections.observableArrayList();

        tableProjectGroup.setCellValueFactory(new PropertyValueFactory<>("group"));
        tableProjectName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableProjectProject.setCellValueFactory(new PropertyValueFactory<>("project"));
        tableProjectDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        tableProjectStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        filteredData = new FilteredList<>(data, p -> true);

        search.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(myObject -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            String lowerCaseFilter = newValue.toLowerCase();

            return String.valueOf(myObject.getName()).toLowerCase().contains(lowerCaseFilter) || String.valueOf(myObject.getProject()).toLowerCase().contains(lowerCaseFilter) || String.valueOf(myObject.getGroup()).toLowerCase().contains(lowerCaseFilter);
        }));

        this.refresh(null);
    }

    public void refresh(ActionEvent event) {
        data.clear();

        for (Project p : this.projects) {
            data.add(new ProjectViewModel(p));
        }

        SortedList<ProjectViewModel> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        tableView.setItems(sortedData);
    }

    public void pull(ActionEvent event) {
        ProjectViewModel project = this.getSelectedProject();

        if (!ProjectViewModel.Status.isActive(project.getStatus())) {
            project.setStatus(ProjectViewModel.Status.PULLING);
            this.executeSync(this.pull, project);
        }
    }

    public void push(ActionEvent event) {
        ProjectViewModel project = this.getSelectedProject();

        if (!ProjectViewModel.Status.isActive(project.getStatus())) {
            project.setStatus(ProjectViewModel.Status.PUSHING);
            this.executeSync(this.push, project);
        }
    }

    private void executeSync(Sync sync, ProjectViewModel project) {
        new Thread(() -> {
            try {
                sync.execute(project.getProjectObject());
                project.setStatus(ProjectViewModel.Status.NONE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private ProjectViewModel getSelectedProject() {
        return tableView.getSelectionModel().getSelectedItem();
    }
}
