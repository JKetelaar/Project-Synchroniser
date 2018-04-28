package nl.jketelaar.project.sync.helpers.synchronize;

import com.github.fracpete.processoutput4j.output.ConsoleOutputProcessOutput;
import com.github.fracpete.rsync4j.RSync;
import nl.jketelaar.project.sync.models.Host;
import nl.jketelaar.project.sync.models.Project;

/**
 * @author JKetelaar
 */
public abstract class Sync {

    private Host                       host;
    private RSync                      rSync;
    private ConsoleOutputProcessOutput output;

    public Sync(Host host) {
        this.host = host;
        this.rSync = new RSync();
        this.output = new ConsoleOutputProcessOutput();
    }

    public void execute(Project project) throws Exception {
        this.setRSync(project);
    }

    public Host getHost() {
        return host;
    }

    public String getHostSource(Project project) {
        return this.host.getBasePath() + "/" + project.getGroup() + "/" + project.getProject().replace(" ", "\\ ") + "/";
    }

    public String getLocalSource(Project project) {
        return project.getDestination() + "/" + project.getGroup() + "/" + project.getProject() + "/";
    }

    public RSync getRSync() {
        return rSync;
    }

    public abstract void setRSync(Project project);

    public ConsoleOutputProcessOutput getOutput() {
        return output;
    }
}
