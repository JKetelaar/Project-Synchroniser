package nl.jketelaar.project.sync.models;

/**
 * @author JKetelaar
 */
public class Host {

    private String host;
    private int    port;
    private String username;
    private String basePath;

    public Host(String host, int port, String username, String basePath) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.basePath = basePath;
    }

    public Host(String host, String username, String basePath) {
        this.host = host;
        this.port = 22;
        this.username = username;
        this.basePath = basePath;
    }

    public String getHost() {
        return host;
    }

    public Host setHost(String host) {
        this.host = host;
        return this;
    }

    public int getPort() {
        return port;
    }

    public Host setPort(int port) {
        this.port = port;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Host setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getBasePath() {
        return basePath;
    }

    public Host setBasePath(String basePath) {
        this.basePath = basePath;
        return this;
    }
}
