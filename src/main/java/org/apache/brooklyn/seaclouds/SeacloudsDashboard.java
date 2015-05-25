package org.apache.brooklyn.seaclouds;

import brooklyn.catalog.Catalog;
import brooklyn.config.ConfigKey;
import brooklyn.entity.basic.ConfigKeys;
import brooklyn.entity.basic.SoftwareProcess;
import brooklyn.entity.proxying.ImplementedBy;
import brooklyn.entity.trait.HasShortName;
import brooklyn.event.basic.BasicAttributeSensorAndConfigKey;
import brooklyn.event.basic.PortAttributeSensorAndConfigKey;
import brooklyn.util.flags.SetFromFlag;

@Catalog(name = "SeaClouds Dashboard", description = "SeaClouds Dashboard", iconUrl =
        "classpath:///seaclouds.png")
@ImplementedBy(SeacloudsDashboardImpl.class)
public interface SeacloudsDashboard extends SoftwareProcess, HasShortName {

    @SetFromFlag("version")
    ConfigKey<String> SUGGESTED_VERSION =
            ConfigKeys.newConfigKeyWithDefault(SoftwareProcess.SUGGESTED_VERSION, "0.1.0-SNAPSHOT");

    @SetFromFlag("downloadUrl")
    BasicAttributeSensorAndConfigKey<String> DOWNLOAD_URL = new BasicAttributeSensorAndConfigKey.StringAttributeSensorAndConfigKey(
            SoftwareProcess.DOWNLOAD_URL, "https://www.dropbox.com/s/y1qwy16d9lf64a9/dashboard.jar");

    @SetFromFlag("dashboardPort")
    PortAttributeSensorAndConfigKey DASHBOARD_PORT = new PortAttributeSensorAndConfigKey(
            "seaclouds.dashboard.port", "", 8000);

    @SetFromFlag("dashboardAdminPort")
    PortAttributeSensorAndConfigKey DASHBOARD_ADMIN_PORT = new PortAttributeSensorAndConfigKey(
            "seaclouds.dashboard.adminPort", "", 8001);
    
    @SetFromFlag("deployer.endpoint")
    ConfigKey<String> DEPLOYER_ENDPOINT = 
            ConfigKeys.newStringConfigKey("seaclouds.dashboard.deployer.endpoint", "Endpoint address for the SeaClouds Deployer");

    @SetFromFlag("deployer.endpoint")
    ConfigKey<String> DEPLOYER_USERNAME =
            ConfigKeys.newStringConfigKey("seaclouds.dashboard.deployer.endpoint", "Endpoint address for the SeaClouds Deployer");

    @SetFromFlag("deployer.endpoint")
    ConfigKey<String> DEPLOYER_PASSWORD =
            ConfigKeys.newStringConfigKey("seaclouds.dashboard.deployer.endpoint", "Endpoint address for the SeaClouds Deployer");

    @SetFromFlag("monitor.endpoint")
    ConfigKey<String> MONITOR_ENDPOINT =
            ConfigKeys.newStringConfigKey("seaclouds.dashboard.monitor.endpoint", "Endpoint address for the SeaClouds Monitor");

    @SetFromFlag("planner.endpoint")
    ConfigKey<String> PLANNER_ENDPOINT =
            ConfigKeys.newStringConfigKey("seaclouds.dashboard.planner.endpoint", "Endpoint address for the SeaClouds Planner");

    @SetFromFlag("sla.endpoint")
    ConfigKey<String> SLA_ENDPOINT =
            ConfigKeys.newStringConfigKey("seaclouds.dashboard.sla.endpoint", "Endpoint address for the SeaClouds SLA core");
    
    
}
