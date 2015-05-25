package org.apache.brooklyn.seaclouds;

import brooklyn.entity.basic.Attributes;
import brooklyn.entity.basic.Entities;
import brooklyn.entity.basic.EntityLocal;
import brooklyn.entity.basic.lifecycle.ScriptHelper;
import brooklyn.entity.java.JavaSoftwareProcessSshDriver;
import brooklyn.location.basic.SshMachineLocation;
import brooklyn.util.collections.MutableMap;
import brooklyn.util.net.Networking;
import brooklyn.util.os.Os;
import brooklyn.util.ssh.BashCommands;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.net.URI;
import java.util.List;

import static java.lang.String.format;

public class SeacloudsDashboardSshDriver extends JavaSoftwareProcessSshDriver implements SeacloudsDashboardDriver {

    public SeacloudsDashboardSshDriver(EntityLocal entity, SshMachineLocation machine) {
        super(entity, machine);
    }

    @Override
    protected String getLogFileLocation() {
        return Os.mergePathsUnix(getRunDir(), "console.out");
    }

    @Override
    public void preInstall() {
        resolver = Entities.newDownloader(this);
        setExpandedInstallDir(Os.mergePaths(
                getInstallDir(), 
                resolver.getUnpackedDirectoryName(format("dashboard-%s", getVersion()))));
    }
    
    @Override
    public void install() {
        List<String> urls = resolver.getTargets();
        String saveAs = resolver.getFilename();

        List<String> commands = ImmutableList.<String>builder()
                .addAll(BashCommands.commandsToDownloadUrlsAs(urls, saveAs))
                .build();

        newScript(INSTALLING)
                .body.append(commands)
                .execute();
    }

    @Override
    public void customize() {
        log.debug("Customizing {}", entity);
        Networking.checkPortsValid(MutableMap.of("dashboardPort", getPort()));
        ScriptHelper customizeScript = newScript(CUSTOMIZING)
                .body.append(
                        format("cp -R %s/* .", getInstallDir()),
                        format("mkdir %s/seaclouds-dashboard", getRunDir())
                );
        customizeScript.gatherOutput(true);
        int res = customizeScript.execute();

    }

    @Override
    public void launch() {
        newScript(MutableMap.of(USE_PID_FILE, getPidFile()), LAUNCHING)
                .failOnNonZeroResultCode()
                .body.append(
                format("nohup java " + 
                                "-Ddeployer.endpoint=%s " +
                                "-Ddeployer.username=%s " +
                                "-Ddeployer.password=%s " +
                                "-Dmonitor.endpoint=%s " +
                                "-Dplanner.endpoint=%s " +
                                "-Dsla.endpoint=%s " +
                                "-Ddw.server.applicationConnectors[0].port=%s " +
                                "-Ddw.server.adminConnectors[0].port=%s " + 
                                "-jar dashboard.jar server " +
                                "> %s 2>&1 &",
                        entity.getConfig(SeacloudsDashboard.DEPLOYER_ENDPOINT),
                        entity.getConfig(SeacloudsDashboard.DEPLOYER_USERNAME),
                        entity.getConfig(SeacloudsDashboard.DEPLOYER_PASSWORD),
                        entity.getConfig(SeacloudsDashboard.MONITOR_ENDPOINT),
                        entity.getConfig(SeacloudsDashboard.PLANNER_ENDPOINT),
                        entity.getConfig(SeacloudsDashboard.SLA_ENDPOINT),
                        entity.getAttribute(SeacloudsDashboard.DASHBOARD_PORT),
                        entity.getAttribute(SeacloudsDashboard.DASHBOARD_ADMIN_PORT),
                        getLogFileLocation()))
                .execute();

        String mainUri = format("http://%s:%d", 
                entity.getAttribute(Attributes.HOSTNAME), 
                entity.getAttribute(SeacloudsDashboard.DASHBOARD_PORT));
        entity.setAttribute(Attributes.MAIN_URI, URI.create(mainUri));
    }

    public String getPidFile() {
        return Os.mergePathsUnix(getRunDir(), "seaclouds-dashboard.pid");
    }
    
    @Override
    public boolean isRunning() {
        return newScript(MutableMap.of(USE_PID_FILE, getPidFile()), CHECK_RUNNING).execute() == 0;
    }

    @Override
    public void stop() {
        newScript(ImmutableMap.of(USE_PID_FILE, getPidFile()), STOPPING).execute();
    }

    @Override
    public Integer getPort() {
        return entity.getAttribute(SeacloudsDashboard.DASHBOARD_PORT);
    }
}
