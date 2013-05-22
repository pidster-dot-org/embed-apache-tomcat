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
