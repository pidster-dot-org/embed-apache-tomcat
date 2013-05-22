package org.pidster.tomcat.embed.impl;

import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.tomcat.util.IntrospectionUtils;
import org.pidster.tomcat.embed.TomcatComponentException;

public class InstanceConfigurer {

    private static final Logger logger = Logger.getLogger(InstanceConfigurer.class.getName());

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

            logger.log(Level.FINE, "Instantiated class {0}", subclass.getName());

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

            logger.log(Level.FINE, "Set field {0} to {1}? {2}", new Object[] { name, value, setProperty });
        }
    }

}
