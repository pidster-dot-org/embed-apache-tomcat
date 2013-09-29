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

import java.util.Map;

import org.apache.catalina.Host;

/**
 * @author pidster
 *
 */
public interface TomcatServiceBuilder extends TomcatContainerBuilder<TomcatServerBuilder, TomcatServiceBuilder>, Collector<TomcatServiceBuilder, Host> {

	/**
	 * Adds the standard pair of HTTP and AJP connectors
	 * 
	 * @return this builder
	 */
    TomcatServiceBuilder addStandardConnectors();

    /**
     * @param port
     * @param minSize
     * @param maxSize
     * @param config
	 * @return this builder
     */
    TomcatServiceBuilder addBioExecutor(int port, int minSize, int maxSize, Map<String, String> config);

    /**
     * @param port
     * @param minSize
     * @param maxSize
     * @param config
	 * @return this builder
     */
    TomcatServiceBuilder addNioExecutor(int port, int minSize, int maxSize, Map<String, String> config);

    /**
     * @param name
     * @param prefix
     * @param minSize
     * @param maxSize
     * @param config
	 * @return this builder
     */
    TomcatServiceBuilder addExecutor(String name, String prefix, int minSize, int maxSize, Map<String, String> config);

    /**
     * @param protocol
     * @param port
	 * @return this builder
     */
    TomcatServiceBuilder addConnector(String protocol, int port);

    /**
     * @param protocol
     * @param port
     * @param config
	 * @return this builder
     */
    TomcatServiceBuilder addConnector(String protocol, int port, Map<String, String> config);

    /**
     * @param name
     * @param appBase
     * @return host builder
     */
    TomcatHostBuilder addHost(String name, String appBase);

}