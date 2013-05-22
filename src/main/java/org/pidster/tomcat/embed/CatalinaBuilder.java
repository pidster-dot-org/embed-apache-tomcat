/*
   Copyright 2013 pid[at]pidster.org

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
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