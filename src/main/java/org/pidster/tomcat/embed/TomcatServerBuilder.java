package org.pidster.tomcat.embed;

import java.io.File;

import org.apache.catalina.Service;
import org.apache.catalina.deploy.ResourceBase;


public interface TomcatServerBuilder extends TomcatLifecyleBuilder<TomcatServerBuilder>, Collector<TomcatServerBuilder, Service>, HierarchicalBuilder<TomcatBuilder, Tomcat> {

    TomcatServerBuilder setCatalinaBase(File catalinaBase);

    TomcatServerBuilder setCatalinaHome(File catalinaHome);

    TomcatServerBuilder addGlobalResource(ResourceBase resource);

    TomcatServiceBuilder addService(String name);

    TomcatServiceBuilder addService(String name, String jvmRoute);

}