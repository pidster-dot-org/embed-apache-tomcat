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

import java.util.Map;

import org.apache.catalina.Context;


public interface TomcatHostBuilder extends TomcatContainerBuilder<TomcatServiceBuilder, TomcatHostBuilder>, Collector<TomcatHostBuilder, Context> {

    TomcatHostBuilder addApplication(String path, String name, String docBase, Map<String, String> config);

    TomcatApplicationBuilder createApplication(String path, String name, Map<String, String> config);

    TomcatApplicationBuilder createApplication(String path, String name);

    TomcatApplicationBuilder createApplication(String name);

}