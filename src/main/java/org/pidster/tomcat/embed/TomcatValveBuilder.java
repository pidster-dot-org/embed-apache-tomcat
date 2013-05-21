package org.pidster.tomcat.embed;

import org.apache.catalina.Container;


public interface TomcatValveBuilder extends Builder<Tomcat>, TomcatContainerBuilder<TomcatHostBuilder, TomcatValveBuilder>, Collector<TomcatValveBuilder, Container> {

    TomcatHostBuilder parent();

}