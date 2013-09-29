/*
 * Copyright 2013 pidster
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.pidster.tomcat.embed;

import java.io.File;
import java.util.Properties;

import org.apache.catalina.Server;

/**
 * @author pidster
 *
 */
public interface CatalinaBuilder extends Builder<Tomcat>, Collector<CatalinaBuilder, Server> {

	/**
	 * @param properties
     * @return this builder
	 */
    CatalinaBuilder addProperties(Properties properties);

    /**
     * @param name
     * @param value
     * @return this builder
     */
    CatalinaBuilder setProperty(String name, String value);

    /**
     * @param file
     * @return this builder
     */
    CatalinaBuilder useConfig(String file);

    /**
     * @param file
     * @return this builder
     */
    CatalinaBuilder useConfig(File file);

    /**
     * @param classLoader
     * @return this builder
     */
    CatalinaBuilder useClassLoader(ClassLoader classLoader);

    /**
     * @param useShutdownHook
     * @return this builder
     */
    CatalinaBuilder setUseShutdownHook(boolean useShutdownHook);

    /**
     * @param useNaming
     * @return this builder
     */
    CatalinaBuilder setUseNaming(boolean useNaming);

    /**
     * @return server
     */
    TomcatServerBuilder newServer();

    /**
     * @param port
     * @return server
     */
    TomcatServerBuilder newServer(int port);

    /**
     * @param host
     * @param port
     * @return server
     */
    TomcatServerBuilder newServer(String host, int port);

    /**
     * @param port
     * @param password
     * @return server
     */
    TomcatServerBuilder newServer(int port, String password);

    /**
     * @param host
     * @param port
     * @param password
     * @return server
     */
    TomcatServerBuilder newServer(String host, int port, String password);

    /**
     * @return host
     */
    TomcatHostBuilder newMinimalServer();

    /**
     * @param http
     * @return host
     */
    TomcatHostBuilder newMinimalServer(int http);

    /**
     * @param port
     * @param baseDir
     * @return host
     */
    TomcatHostBuilder newMinimalServer(int port, File baseDir);

    /**
     * @param baseDir
     * @return host
     */
    TomcatHostBuilder newMinimalServer(File baseDir);

    /**
     * @param baseDir
     * @param http
     * @return host
     */
    TomcatHostBuilder newMinimalServer(File baseDir, int http);

    /**
     * @param port
     * @param baseDir
     * @param http
     * @return host
     */
    TomcatHostBuilder newMinimalServer(int port, File baseDir, int http);

    /**
     * @return host
     */
    TomcatHostBuilder newStandardServer();

    /**
     * @param baseDir
     * @return host
     */
    TomcatHostBuilder newStandardServer(File baseDir);

    /**
     * @param httpPort
     * @param ajpPort
     * @return host
     */
    TomcatHostBuilder newStandardServer(int httpPort, int ajpPort);

    /**
     * @param baseDir
     * @param httpPort
     * @param ajpPort
     * @return host
     */
    TomcatHostBuilder newStandardServer(File baseDir, int httpPort, int ajpPort);

    /**
     * @param port
     * @param baseDir
     * @return host
     */
    TomcatHostBuilder newStandardServer(int port, File baseDir);

    /**
     * @param port
     * @param baseDir
     * @param httpPort
     * @param ajpPort
     * @return host
     */
    TomcatHostBuilder newStandardServer(int port, File baseDir, int httpPort, int ajpPort);


}