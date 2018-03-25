package nl.jketelaar.project.sync.helpers;

import com.google.gson.Gson;
import nl.jketelaar.project.sync.models.Host;
import nl.jketelaar.project.sync.models.Project;

import java.io.*;
import java.util.List;

/**
 * @author JKetelaar
 */
public class Storage {

    private static final String hostJson     = "host.json";
    private static final String projectsJson = "projects.json";
    private static Storage instance;
    private final String configDirectory;
    private final File   hostFile;
    private final File   projectsFile;
    private       Gson   gson;

    private Storage(String configDirectory) {
        this.configDirectory = configDirectory;
        this.hostFile = new File(this.configDirectory, hostJson);
        this.projectsFile = new File(this.configDirectory, projectsJson);

        this.createConfigDirectory();

        this.gson = new Gson();
    }

    public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage(System.getProperty("user.home") + "/.pjsync/");
        }

        return instance;
    }

    public static Storage setInstance(String configDirectory) {
        instance = new Storage(configDirectory);

        return instance;
    }

    private void createConfigDirectory() {
        File dir = new File(this.configDirectory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public void writeHost(Host host) {
        try (FileWriter writer = new FileWriter(this.hostFile)) {
            gson.toJson(host, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Host getHost() {
        if (this.hostFile.exists()) {
            try (Reader reader = new FileReader(this.hostFile)) {
                return gson.fromJson(reader, Host.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public void writeProjects(Project[] projects) {
        try (FileWriter writer = new FileWriter(this.projectsFile)) {
            gson.toJson(projects, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeProjects(List<Project> projects) {
        Project[] p = new Project[projects.size()];
        this.writeProjects(projects.toArray(p));
    }

    public Project[] getProjects() {
        if (this.projectsFile.exists()) {
            try (Reader reader = new FileReader(this.projectsFile)) {
                return gson.fromJson(reader, Project[].class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
