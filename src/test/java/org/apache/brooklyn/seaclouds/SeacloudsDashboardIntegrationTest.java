package org.apache.brooklyn.seaclouds;

import brooklyn.entity.Application;
import brooklyn.entity.Entity;
import brooklyn.entity.trait.Startable;
import brooklyn.launcher.camp.SimpleYamlLauncher;
import brooklyn.test.Asserts;
import brooklyn.test.EntityTestUtils;
import eu.seaclouds.brooklyn.apps.dashboard.SeacloudsDashboard;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SeacloudsDashboardIntegrationTest {
    
    @Test(groups = "Integration")
    public void testFromYaml() {
        SimpleYamlLauncher launcher = new SimpleYamlLauncher();
        launcher.setShutdownAppsOnExit(true);
        Application app = launcher.launchAppYaml("dashboard-single.yaml").getApplication();

        final SeacloudsDashboard dashboard = (SeacloudsDashboard)
                findEntityChildByDisplayName(app, "SeaClouds dashboard");
        
        Asserts.succeedsEventually(new Runnable() {
            public void run() {
                EntityTestUtils.assertAttributeEqualsEventually(dashboard, Startable.SERVICE_UP, true);
                EntityTestUtils.assertAttributeEquals(dashboard, SeacloudsDashboard.DASHBOARD_PORT, 8000);
                EntityTestUtils.assertAttributeEquals(dashboard, SeacloudsDashboard.DASHBOARD_PORT, 8001);
                
                dashboard.stop();
                Assert.assertFalse(dashboard.getAttribute(Startable.SERVICE_UP));
            }
        });
    }

    private Entity findEntityChildByDisplayName(Application app, String displayName){
        for(Object entity: app.getChildren().toArray())
            if(((Entity)entity).getDisplayName().equals(displayName)){
                return (Entity)entity;
            }
        return null;
    }
    
}

