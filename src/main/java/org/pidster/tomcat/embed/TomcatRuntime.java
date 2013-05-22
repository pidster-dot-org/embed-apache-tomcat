package org.pidster.tomcat.embed;

import org.apache.catalina.Server;

public interface TomcatRuntime extends AutoCloseable {

    TomcatRuntime deploy(String appName);

    TomcatRuntime undeploy(String appName);

    TomcatStatus status();

    Server getServer();

    void stop();

    void stop(long timeout);

    void stopOnCompletion(Thread waiting);

}
