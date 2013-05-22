package org.pidster.tomcat.embed;

import java.util.ServiceLoader;

public class TomcatFactory {

    public CatalinaBuilder create() {
        ServiceLoader<CatalinaBuilder> impls = ServiceLoader.load(CatalinaBuilder.class);
        if(!impls.iterator().hasNext()) {
            throw new RuntimeException("No implementation");
        }

        try {
            return impls.iterator().next();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
