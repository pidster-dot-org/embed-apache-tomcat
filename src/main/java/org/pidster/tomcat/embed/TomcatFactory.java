package org.pidster.tomcat.embed;

import java.util.ServiceLoader;

public class TomcatFactory {

    public TomcatBuilder create() {
        ServiceLoader<TomcatBuilder> impls = ServiceLoader.load(TomcatBuilder.class);
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
