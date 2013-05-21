package org.pidster.tomcat.embed;

import java.util.Collections;
import java.util.Map;


public interface Tomcat extends Buildable {

    public static final Map<String, String> EMPTY = Collections.<String, String> emptyMap();

    TomcatRuntime start();

    TomcatRuntime start(long timeout);

    void start(Callback<TomcatRuntime> callback);

}
