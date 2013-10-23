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

import org.pidster.tomcat.embed.Tomcat;
import org.pidster.tomcat.embed.TomcatApplicationBuilder;
import org.pidster.tomcat.embed.TomcatFactory;

/**
 * @author pidster
 * 
 */
public class SimpleTomcatApplicationBuilderFactory implements TomcatApplicationBuilderFactory {

    @Override
    public TomcatApplicationBuilder getBuilder(TomcatServerConfig annotation) {

        File baseFile = getBaseDir(annotation);
        String appName = getAppName(annotation);
        int port = getPort(annotation);

        File appDir = new File(baseFile, String.format("webapps/%s", appName));
        if (!appDir.exists() && !appDir.mkdirs()) {
            throw new IllegalStateException("Unable to create app dir at: " + appDir);
        }

        TomcatApplicationBuilder builder = new TomcatFactory().create()
            .newMinimalServer(baseFile, port)
                .createApplication(appName)
                .setStartStopThreads(1)
                .withDefaultConfig();

        if (annotation != null) {
            for (Class<? extends ServletContainerInitializer> initializer : annotation.value()) {
                builder.addServletContainerInitializer(initializer);
            }
        }

        return builder;
    }

    /**
     * @param annotation
     * @return port
     */
    private int getPort(TomcatServerConfig annotation) {
        int port;
        if (annotation == null) {
            port = TomcatServerConfig.DEFAULT_HTTP_PORT;
        } else {
            port = annotation.port();
        }
        return port;
    }

    /**
     * @param annotation
     * @return name
     */
    private String getAppName(TomcatServerConfig annotation) {
        String appName;
        if (annotation == null || "".equals(annotation.appName())) {
            appName = "test";
        } else {
            appName = annotation.appName();
        }
        return appName;
    }

    /**
     * @param annotation
     * @return baseDir
     */
    private File getBaseDir(TomcatServerConfig annotation) {
        String baseDir;
        if (annotation == null || "".equals(annotation.baseDir())) {
            if (System.getProperties().containsKey(Tomcat.CATALINA_BASE)) {
                baseDir = System.getProperty(Tomcat.CATALINA_BASE);
            } else {
                baseDir = System.getProperty("user.dir") + File.separator + "build";
            }
        }
        else {
            baseDir = annotation.baseDir();
        }

        return new File(baseDir);
    }

}
