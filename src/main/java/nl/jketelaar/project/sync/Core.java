package nl.jketelaar.project.sync;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.jketelaar.project.sync.helpers.Storage;
import nl.jketelaar.project.sync.helpers.synchronize.Pull;
import nl.jketelaar.project.sync.models.Host;
import nl.jketelaar.project.sync.models.Project;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author JKetelaar
 */
public class Core extends Application {

    private final Host          host;
    private final List<Project> projects;

    public Core() {
        Storage storage = Storage.setInstance(System.getProperty("user.home") + "/.pjsync/");

        this.host = storage.getHost();
        this.projects = Arrays.asList(storage.getProjects());
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ui/overview.fxml"));
            primaryStage.setTitle("Registration Form FXML Application");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (false) {
            long time = System.currentTimeMillis();
            for (Project project : this.projects) {

                Pull pull = new Pull(this.host);
                try {
                    pull.execute(project);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println(String.format("Took %d seconds to synchronize the project (%s)", (int) (System.currentTimeMillis() - time) / 1000, project.getName()));
            }
        }

        primaryStage.show();
    }
}
