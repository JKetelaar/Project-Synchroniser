package nl.jketelaar.project.sync.models;

/**
 * @author JKetelaar
 */
public class Project {
    private String group;
    private String project;
    private String name;
    private String destination;

    public Project(String group, String project, String name, String destination) {
        this.group = group;
        this.project = project;
        this.name = name;
        this.destination = destination;
    }

    public String getGroup() {
        return group;
    }

    public Project setGroup(String group) {
        this.group = group;
        return this;
    }

    public String getProject() {
        return project;
    }

    public Project setProject(String project) {
        this.project = project;
        return this;
    }

    public String getName() {
        return name;
    }

    public Project setName(String name) {
        this.name = name;
        return this;
    }

    public String getDestination() {
        return destination;
    }

    public Project setDestination(String destination) {
        this.destination = destination;
        return this;
    }
}
