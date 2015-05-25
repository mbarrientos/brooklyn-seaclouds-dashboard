package org.apache.brooklyn.seaclouds;

import brooklyn.entity.basic.SoftwareProcessImpl;

public class SeacloudsDashboardImpl extends SoftwareProcessImpl implements SeacloudsDashboard {
    
    @Override
    public Class getDriverInterface() {
        return SeacloudsDashboardDriver.class;
    }

    @Override
    public String getShortName() {
        return "SeaClouds Dashboard";
    }

    @Override
    protected void connectSensors() {
        super.connectSensors();
        connectServiceUpIsRunning();
    }

    @Override
    protected void disconnectSensors() {
        disconnectServiceUpIsRunning();
        super.disconnectSensors();
    }
}
