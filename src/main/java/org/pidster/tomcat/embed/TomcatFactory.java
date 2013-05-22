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

import java.util.Properties;
import java.util.ServiceLoader;

public class TomcatFactory {

    private final Properties properties;

    public TomcatFactory() {
        this.properties = new Properties();
        properties.put("catalina.base", System.getProperty("user.dir"));
        properties.put("catalina.home", System.getProperty("user.dir"));
    }

    public TomcatFactory(Properties properties) {
        this();
        this.properties.putAll(properties);
    }

    public CatalinaBuilder create() {
        ServiceLoader<CatalinaBuilder> impls = ServiceLoader.load(CatalinaBuilder.class);
        if(!impls.iterator().hasNext()) {
            throw new RuntimeException("No implementation");
        }

        try {
            CatalinaBuilder builder = impls.iterator().next();
            builder.addProperties(properties);
            return builder;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
