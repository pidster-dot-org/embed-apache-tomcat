package org.pidster.tomcat.embed;

import java.io.File;

import org.apache.catalina.Service;
import org.apache.catalina.deploy.ContextResource;


public interface TomcatServerBuilder extends TomcatLifecyleBuilder<TomcatServerBuilder>, Collector<TomcatServerBuilder, Service>, HierarchicalBuilder<CatalinaBuilder, Tomcat> {

    TomcatServerBuilder setSilentLogging(boolean silentLogging);

    TomcatServerBuilder setCatalinaBase(File catalinaBase);

    TomcatServerBuilder setCatalinaHome(File catalinaHome);

    TomcatServerBuilder enableNaming();

    TomcatServerBuilder setEnableNaming(boolean enableNaming);

    TomcatServerBuilder addGlobalResource(ContextResource resource);

    TomcatServiceBuilder addService();

    TomcatServiceBuilder addService(String jvmRoute);

    TomcatServiceBuilder addService(String name, String jvmRoute);

}