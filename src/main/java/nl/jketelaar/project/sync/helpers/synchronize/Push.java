package nl.jketelaar.project.sync.helpers.synchronize;

import nl.jketelaar.project.sync.models.Host;
import nl.jketelaar.project.sync.models.Project;

/**
 * @author JKetelaar
 */
public class Push extends Sync {
    public Push(Host host) {
        super(host);
    }

    public void setRSync(Project project) {
        this.getRSync().reset();

        this.getRSync().source(getLocalSource(project))
                .destination(String.format("%s@%s:%s", this.getHost().getUsername(), this.getHost().getHost(), getHostSource(project)))
                .recursive(true)
                .exclude("@eaDir", "thumbs.db", ".DS_Store")
                .perms(true)
                .progress(true);
    }

    public void execute(Project project) throws Exception {
        super.execute(project);

        this.getOutput().monitor(this.getRSync().builder());
    }
}
