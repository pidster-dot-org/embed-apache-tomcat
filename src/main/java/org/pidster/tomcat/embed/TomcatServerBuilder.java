package org.pidster.tomcat.embed;

import java.io.File;

import org.apache.catalina.Service;


public interface TomcatServerBuilder extends TomcatLifecyleBuilder<TomcatServerBuilder>, Collector<TomcatServerBuilder, Service>, ParentalBuilder<TomcatBuilder, Tomcat> {

    TomcatServerBuilder setCatalinaBase(File catalinaBase);

    TomcatServerBuilder setCatalinaHome(File catalinaHome);

    TomcatServiceBuilder addService(String name);

    TomcatServiceBuilder addService(String name, String jvmRoute);

}