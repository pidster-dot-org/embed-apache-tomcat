package org.pidster.tomcat.embed;

import java.io.File;
import java.util.Properties;

import org.apache.catalina.Server;


public interface CatalinaBuilder extends Builder<Tomcat>, Collector<CatalinaBuilder, Server> {

    CatalinaBuilder addProperties(Properties properties);

    CatalinaBuilder setProperty(String name, String value);

    CatalinaBuilder useConfig(String file);

    CatalinaBuilder useConfig(File file);

    CatalinaBuilder useClassLoader(ClassLoader classLoader);

    CatalinaBuilder setUseShutdownHook(boolean useShutdownHook);

    CatalinaBuilder setUseNaming(boolean useNaming);

    TomcatServerBuilder newServer();

    TomcatServerBuilder newServer(int port);

    TomcatServerBuilder newServer(String host, int port);

    TomcatServerBuilder newServer(int port, String password);

    TomcatServerBuilder newServer(String host, int port, String password);

    TomcatHostBuilder newMinimalServer();

    TomcatHostBuilder newMinimalServer(int http);

    TomcatHostBuilder newMinimalServer(int port, File baseDir);

    TomcatHostBuilder newMinimalServer(File baseDir);

    TomcatHostBuilder newMinimalServer(File baseDir, int http);

    TomcatHostBuilder newMinimalServer(int port, File baseDir, int http);

    TomcatHostBuilder newStandardServer();

    TomcatHostBuilder newStandardServer(File baseDir);

    TomcatHostBuilder newStandardServer(int httpPort, int ajpPort);

    TomcatHostBuilder newStandardServer(File baseDir, int httpPort, int ajpPort);

    TomcatHostBuilder newStandardServer(int port, File baseDir);

    TomcatHostBuilder newStandardServer(int port, File baseDir, int httpPort, int ajpPort);


}