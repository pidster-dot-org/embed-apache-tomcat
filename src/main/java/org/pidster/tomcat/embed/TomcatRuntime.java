package org.pidster.tomcat.embed;

public interface TomcatRuntime extends AutoCloseable {

    TomcatRuntime deploy(String appName);

    TomcatRuntime undeploy(String appName);

    TomcatStatus status();

    void stop();

    void stop(long timeout);

    void stopOnCompletion(Thread waiting);

}
