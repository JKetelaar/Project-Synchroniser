package nl.jketelaar.project.sync.helpers.synchronize;

import nl.jketelaar.project.sync.models.Host;
import nl.jketelaar.project.sync.models.Project;

/**
 * @author JKetelaar
 */
public class Pull extends Sync {

    public Pull(Host host) {
        super(host);
    }

    public void setRSync(Project project) {
        this.getRSync().reset();

        this.getRSync().source(String.format("%s@%s:%s", this.getHost().getUsername(), this.getHost().getHost(), getHostSource(project)))
                .destination(getLocalSource(project))
                .recursive(true)
                .exclude("@eaDir", "thumbs.db", ".DS_Store")
                .perms(true)
                .progress(true);
    }

    @Override
    public void execute(Project project) throws Exception {
        super.execute(project);

        this.getOutput().monitor(this.getRSync().builder());
    }
}
