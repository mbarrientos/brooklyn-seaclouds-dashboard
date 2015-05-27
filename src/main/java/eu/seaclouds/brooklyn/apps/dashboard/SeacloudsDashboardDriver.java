package eu.seaclouds.brooklyn.apps.dashboard;

import brooklyn.entity.java.JavaSoftwareProcessDriver;

public interface SeacloudsDashboardDriver extends JavaSoftwareProcessDriver {
    Integer getPort();
}
