package org.apache.brooklyn.seaclouds;

import brooklyn.entity.basic.Entities;
import brooklyn.entity.proxying.EntitySpec;
import brooklyn.entity.trait.Startable;
import brooklyn.location.basic.LocalhostMachineProvisioningLocation;
import brooklyn.test.EntityTestUtils;
import brooklyn.test.entity.TestApplication;
import com.google.common.collect.ImmutableList;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SeacloudsDashboardIntegrationTest {
    
    private TestApplication app;
    private LocalhostMachineProvisioningLocation localhostProvisioningLocation;

    @BeforeMethod(alwaysRun = true)
    public void setUp() throws Exception {
        localhostProvisioningLocation = new LocalhostMachineProvisioningLocation();
        app = TestApplication.Factory.newManagedInstanceForTests();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() throws Exception {
        if (app != null) Entities.destroyAll(app.getManagementContext());
    }

    @Test(groups = "Integration")
    public void testCanStartAndStop() throws Exception {
        SeacloudsDashboard entity = app.createAndManageChild(
                EntitySpec.create(SeacloudsDashboard.class)
                        .configure(SeacloudsDashboard.DEPLOYER_ENDPOINT, "localhost:8081")
                        .configure(SeacloudsDashboard.DEPLOYER_USERNAME, "localhost:8081")
                        .configure(SeacloudsDashboard.DEPLOYER_PASSWORD, "localhost:8081")
                        .configure(SeacloudsDashboard.MONITOR_ENDPOINT, "localhost:8081")
                        .configure(SeacloudsDashboard.PLANNER_ENDPOINT, "localhost:8081")
                        .configure(SeacloudsDashboard.SLA_ENDPOINT, "localhost:8081")
        );
        app.start(ImmutableList.of(localhostProvisioningLocation));

        EntityTestUtils.assertAttributeEqualsEventually(entity, Startable.SERVICE_UP, true);

        entity.stop();
        Assert.assertFalse(entity.getAttribute(Startable.SERVICE_UP));
    }
}
