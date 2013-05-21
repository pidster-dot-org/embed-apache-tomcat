package org.pidster.tomcat.embed;

import java.io.File;

import org.apache.catalina.Service;


public interface TomcatServerBuilder extends Builder<Tomcat>, Collector<TomcatServerBuilder, Service> {

    TomcatBuilder parent();

    TomcatServerBuilder setCatalinaBase(File catalinaBase);

    TomcatServerBuilder setCatalinaHome(File catalinaHome);

    TomcatServiceBuilder addService(String name);

    TomcatServiceBuilder addService(String name, String jvmRoute);

}