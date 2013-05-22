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

import org.apache.catalina.Service;
import org.apache.catalina.deploy.ContextResource;


public interface TomcatServerBuilder extends TomcatLifecyleBuilder<TomcatServerBuilder>, Collector<TomcatServerBuilder, Service>, HierarchicalBuilder<CatalinaBuilder, Tomcat> {

    TomcatServerBuilder setSilentLogging(boolean silentLogging);

    TomcatServerBuilder setCatalinaBase(File catalinaBase);

    TomcatServerBuilder setCatalinaHome(File catalinaHome);

    TomcatServerBuilder enableNaming();

    TomcatServerBuilder setEnableNaming(boolean enableNaming);

    TomcatServerBuilder addGlobalResource(ContextResource resource);

    TomcatServiceBuilder addService();

    TomcatServiceBuilder addService(String jvmRoute);

    TomcatServiceBuilder addService(String name, String jvmRoute);

}