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

import org.apache.catalina.Server;

/**
 * @author pidster
 *
 */
public interface TomcatRuntime extends AutoCloseable {

	/**
	 * @param appName to be deployed
     * @return this runtime
	 */
    TomcatRuntime deploy(String appName);

    /**
     * @param appName to be undeployed
     * @return this runtime
     */
    TomcatRuntime undeploy(String appName);

    /**
     * @return status
     */
    TomcatStatus status();

    /**
     * @return the internal server instance
     */
    Server getServer();

    /**
     * Stop the runtime
     */
    void stop();

    /**
     * Stop after the supplied timeout
     * 
     * @param timeout
     */
    void stop(long timeout);

    /**
     * Stop when the thread supplied terminates, by joining on that thread
     * 
     * @param waiting
     */
    void stopOnCompletion(Thread waiting);

}
