package nl.jketelaar.project.sync.ui;

import javafx.beans.property.SimpleStringProperty;
import nl.jketelaar.project.sync.models.Project;

/**
 * @author JKetelaar
 */
public class ProjectViewModel {
    private final SimpleStringProperty group;
    private final SimpleStringProperty project;
    private final SimpleStringProperty name;
    private final SimpleStringProperty destination;
    private final SimpleStringProperty status;

    private Project projectObject;

    public ProjectViewModel(String group, String project, String name, String destination) {
        this.group = new SimpleStringProperty(group);
        this.project = new SimpleStringProperty(project);
        this.name = new SimpleStringProperty(name);
        this.destination = new SimpleStringProperty(destination);
        this.status = new SimpleStringProperty();

        this.setStatus(Status.NONE);
    }

    public ProjectViewModel(Project project) {
        this(project.getGroup(), project.getProject(), project.getName(), project.getDestination());

        this.projectObject = project;
    }

    public String getGroup() {
        return group.get();
    }

    public SimpleStringProperty groupProperty() {
        return group;
    }

    public void setGroup(String group) {
        this.group.set(group);
    }

    public String getProject() {
        return project.get();
    }

    public SimpleStringProperty projectProperty() {
        return project;
    }

    public void setProject(String project) {
        this.project.set(project);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDestination() {
        return destination.get();
    }

    public SimpleStringProperty destinationProperty() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination.set(destination);
    }

    public Project getProjectObject() {
        return projectObject;
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public void setStatus(Status status) {
        this.status.set(status.getName());
    }

    public enum Status {
        PUSHING("Pushing", true),
        PULLING("Pulling", true),
        OTHER("Other", true),
        NONE("None", false);

        private String  name;
        private boolean active;

        Status(String name, boolean active) {
            this.name = name;
            this.active = active;
        }

        public String getName() {
            return name;
        }

        public boolean isActive() {
            return active;
        }

        public static boolean isActive(String name) {
            for (Status status : Status.values()) {
                if (status.getName().equals(name)) {
                    return status.active;
                }
            }
            return false;
        }
    }
}
