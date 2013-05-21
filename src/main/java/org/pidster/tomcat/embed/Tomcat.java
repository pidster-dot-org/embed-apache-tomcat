package org.pidster.tomcat.embed;

import java.util.Collections;
import java.util.Map;

public interface Tomcat {

    public static final Map<String, String> EMPTY = Collections.<String, String> emptyMap();

    TomcatRuntime start();

    TomcatRuntime start(long timeout);

    TomcatRuntime start(Runnable runnable);

}
