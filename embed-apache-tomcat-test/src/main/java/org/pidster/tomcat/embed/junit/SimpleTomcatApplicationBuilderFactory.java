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
package org.pidster.tomcat.embed.junit;

import java.io.File;

import javax.servlet.ServletContainerInitializer;

import org.pidster.tomcat.embed.TomcatApplicationBuilder;
import org.pidster.tomcat.embed.TomcatFactory;

/**
 * @author pidster
 * 
 */
public class SimpleTomcatApplicationBuilderFactory implements TomcatApplicationBuilderFactory {

    @Override
    public TomcatApplicationBuilder getBuilder(TomcatServerConfig annotation) {

        String baseDir = annotation.baseDir();
        if ("".equals(baseDir)) {
            baseDir = System.getProperty("user.dir");
        }

        File baseFile = new File(baseDir);
        String appName = annotation.appName();
        if ("".equals(appName)) {
            appName = "test";
        }

        File appDir = new File(baseDir, String.format("webapps/%s", appName));
        if (!appDir.exists()) {
            appDir.mkdirs();
        }

        TomcatApplicationBuilder builder = new TomcatFactory().create().newMinimalServer(baseFile, annotation.port()).createApplication(annotation.appName()).setStartStopThreads(1).withDefaultConfig();

        for (Class<? extends ServletContainerInitializer> initializer : annotation.value()) {
            builder.addServletContainerInitializer(initializer);
        }

        return builder;
    }

}
