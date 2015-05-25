package org.apache.brooklyn.seaclouds;

import brooklyn.entity.java.JavaSoftwareProcessDriver;

public interface SeacloudsDashboardDriver extends JavaSoftwareProcessDriver {
    Integer getPort();
}
