package org.pidster.tomcat.embed;

import java.io.File;
import java.util.Properties;

import org.apache.catalina.Server;


public interface TomcatBuilder extends Builder<Tomcat>, Collector<TomcatBuilder, Server> {

    TomcatBuilder setProperties(Properties properties);

    TomcatBuilder setProperty(String name, String value);

    TomcatBuilder useConfig(String file);

    TomcatBuilder useConfig(File file);

    TomcatBuilder useClassLoader(ClassLoader classLoader);

    TomcatBuilder setUseShutdownHook(boolean useShutdownHook);

    TomcatBuilder setUseNaming(boolean useNaming);

    TomcatServerBuilder newServer();

    TomcatServerBuilder newServer(int port);

    TomcatServerBuilder newServer(String host, int port);

    TomcatServerBuilder newServer(int port, String password);

    TomcatServerBuilder newServer(String host, int port, String password);

    TomcatHostBuilder newStandardServer(int port, File baseDir);

    TomcatHostBuilder newStandardServer(File catalinaBase);

}