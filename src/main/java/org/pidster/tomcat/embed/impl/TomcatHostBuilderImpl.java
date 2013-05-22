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
package org.pidster.tomcat.embed.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.pidster.tomcat.embed.Tomcat;
import org.pidster.tomcat.embed.TomcatApplicationBuilder;
import org.pidster.tomcat.embed.TomcatBuilderException;
import org.pidster.tomcat.embed.TomcatHostBuilder;
import org.pidster.tomcat.embed.TomcatServiceBuilder;


public class TomcatHostBuilderImpl extends AbstractContainerBuilder<TomcatServiceBuilder, TomcatHostBuilder> implements TomcatHostBuilder {

    private final Host host;

    private final Map<String, TomcatApplicationBuilder> applications = new HashMap<>();

    protected TomcatHostBuilderImpl(TomcatServiceBuilderImpl parent, Map<String, String> config) {
        super(parent);
        String className = "org.apache.catalina.core.StandardHost";
        this.host = InstanceConfigurer.instantiate(loader(), Host.class, className, config);

        setContainer(host);
    }

    @Override
    public TomcatHostBuilder collect(Context child) {
        child.setParent(host);
        host.addChild(child);
        return this;
    }

    @Override
    public TomcatServiceBuilder parent() {
        return super.parent().collect(host);
    }

    @Override
    public TomcatHostBuilder addApplication(String path, String name, String docBase, Map<String, String> config) {

        if (applications.containsKey(path)) {
            throw new TomcatBuilderException("Path already exists: " + path);
        }

        Map<String, String> aconfig = new HashMap<>();
        if (config != null && !config.isEmpty()) {
            aconfig.putAll(config);
        }

        aconfig.put("path", path);
        aconfig.put("name", name);
        aconfig.put("docBase", docBase);

        TomcatApplicationBuilderImpl applicationBuilder = new TomcatApplicationBuilderImpl(this, aconfig);

        // return this
        return applicationBuilder.parent();
    }

    @Override
    public TomcatApplicationBuilder createApplication(String name) {
        return createApplication(String.format("/%s", name), name, Tomcat.EMPTY);
    }

    @Override
    public TomcatApplicationBuilder createApplication(String path, String name) {
        return createApplication(path, name, Tomcat.EMPTY);
    }

    @Override
    public TomcatApplicationBuilder createApplication(String path, String name, Map<String, String> config) {

        if (applications.containsKey(path)) {
            throw new TomcatBuilderException("Path already exists: " + path);
        }

        Map<String, String> aconfig = new HashMap<>();
        if (config != null && !config.isEmpty()) {
            aconfig.putAll(config);
        }

        aconfig.put("path", path);
        aconfig.put("name", name);
        aconfig.put("docBase", name);

        return new TomcatApplicationBuilderImpl(this, aconfig);
    }

}
