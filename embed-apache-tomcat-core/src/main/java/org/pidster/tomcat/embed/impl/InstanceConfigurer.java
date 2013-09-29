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

import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.tomcat.util.IntrospectionUtils;
import org.pidster.tomcat.embed.TomcatComponentException;

public class InstanceConfigurer {

    private static final Logger LOGGER = Logger.getLogger(InstanceConfigurer.class.getName());

    public static <Z> Z newInstance(Class<Z> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            throw new TomcatComponentException(e);
        } catch (IllegalAccessException e) {
            throw new TomcatComponentException(e);
        }
    }

    public static <Z> Z instantiate(ClassLoader loader, Class<Z> iface, String implClass, Map<String, String> config) {

        try {
            Class<?> clazz = loader.loadClass(implClass);
            Class<? extends Z> subclass = clazz.asSubclass(iface);

            Z instance = subclass.newInstance();

            LOGGER.log(Level.FINE, "Instantiated class {0}", subclass.getName());

            configure(instance, config);

            return instance;

        } catch (ClassNotFoundException e) {
            throw new TomcatComponentException(e);
        } catch (InstantiationException e) {
            throw new TomcatComponentException(e);
        } catch (IllegalAccessException e) {
            throw new TomcatComponentException(e);
        }
    }

    public static void configure(Object instance, Map<String, String> config) {
        Set<String> names = config.keySet();
        for (String name : names) {
            String value = config.get(name);

            boolean setProperty = IntrospectionUtils.setProperty(instance, name, value);

            LOGGER.log(Level.FINE, "Set field {0} to {1}? {2}", new Object[] { name, value, setProperty });
        }
    }

}
