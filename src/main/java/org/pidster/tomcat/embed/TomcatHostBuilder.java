package org.pidster.tomcat.embed;

import java.util.Map;

import org.apache.catalina.Context;


public interface TomcatHostBuilder extends TomcatContainerBuilder<TomcatServiceBuilder, TomcatHostBuilder>, Collector<TomcatHostBuilder, Context> {

    TomcatHostBuilder addApplication(String path, String name, String docBase, Map<String, String> config);

    TomcatApplicationBuilder createApplication(String path, String name, Map<String, String> config);

    TomcatApplicationBuilder createApplication(String path, String name);

}