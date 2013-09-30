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

import org.apache.catalina.Service;
import org.apache.tomcat.util.descriptor.web.ContextResource;

/**
 * @author pidster
 * 
 */
public interface TomcatServerBuilder extends TomcatLifecyleBuilder<TomcatServerBuilder>, Collector<TomcatServerBuilder, Service>, HierarchicalBuilder<CatalinaBuilder, Tomcat> {

    /**
     * @param silentLogging
     * @return this builder
     */
    TomcatServerBuilder setSilentLogging(boolean silentLogging);

    /**
     * @param catalinaBase
     * @return this builder
     */
    TomcatServerBuilder setCatalinaBase(File catalinaBase);

    /**
     * @param catalinaHome
     * @return this builder
     */
    TomcatServerBuilder setCatalinaHome(File catalinaHome);

    /**
     * @return this builder
     */
    TomcatServerBuilder enableNaming();

    /**
     * @param enableNaming
     * @return this builder
     */
    TomcatServerBuilder setEnableNaming(boolean enableNaming);

    /**
     * @param resource
     * @return this builder
     */
    TomcatServerBuilder addGlobalResource(ContextResource resource);

    /**
     * @return this builder
     */
    TomcatServiceBuilder addService();

    /**
     * @param jvmRoute
     * @return this builder
     */
    TomcatServiceBuilder addService(String jvmRoute);

    /**
     * @param name
     * @param jvmRoute
     * @return this builder
     */
    TomcatServiceBuilder addService(String name, String jvmRoute);

}