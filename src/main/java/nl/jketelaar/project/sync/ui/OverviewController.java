package nl.jketelaar.project.sync.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import nl.jketelaar.project.sync.helpers.Storage;
import nl.jketelaar.project.sync.models.Project;

import java.util.Arrays;
import java.util.List;

/**
 * @author JKetelaar
 */
public class OverviewController {

    @FXML
    private TreeTableView tableView;

    @FXML
    private TreeTableColumn tableProjectName;

    @FXML
    private TreeTableColumn tableProjectGroup;

    TreeItem<Project> projectNode;

    public OverviewController() {
        projectNode = new TreeItem<>();
        List<Project>       projectList = Arrays.asList(Storage.getInstance().getProjects());
        TreeItem<Project>[] projects    = new TreeItem[projectList.size()];

        for (int i = 0; i < projectList.size(); i++) {
            projects[i] = new TreeItem<>(projectList.get(i));
        }

        projectNode.getChildren().addAll(projects);
    }

    @FXML
    public void initialize() {
        this.tableProjectName = new TreeTableColumn<Project, String>();
    }
}
