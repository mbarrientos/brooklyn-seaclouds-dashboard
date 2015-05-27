package eu.seaclouds.brooklyn.apps;

import brooklyn.cli.Main;
import io.airlift.command.Command;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class provides a static main entry point for launching a custom Brooklyn-based app.
 * <p>
 * It inherits the standard Brooklyn CLI options from {@link Main},
 * plus adds a few more shortcuts for favourite blueprints to the {@link LaunchCommand}.
 */
public class SeacloudsMain extends Main {

    private static final Logger log = LoggerFactory.getLogger(SeacloudsMain.class);

    public static final String DEFAULT_LOCATION = "localhost";

    public static void main(String... args) {
        log.debug("CLI invoked with args "+Arrays.asList(args));
        new SeacloudsMain().execCli(args);
    }

    @Override
    protected String cliScriptName() {
        return "start.sh";
    }

    @Override
    protected Class<? extends BrooklynCommand> cliLaunchCommand() {
        return LaunchCommand.class;
    }

    @Command(name = "launch", description = "Starts a Brooklyn server. ")    
    public static class LaunchCommand extends Main.LaunchCommand {
        @Override
        public Void call() throws Exception {
            return super.call();
        }
    }
}