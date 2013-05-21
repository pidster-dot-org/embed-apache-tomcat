package org.pidster.tomcat.embed;

import java.beans.PropertyChangeListener;

import org.apache.catalina.Cluster;
import org.apache.catalina.ContainerListener;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.Realm;


public interface ContainerBuilder<P extends Builder<Tomcat>, T extends Builder<Tomcat>> {

    T addLifecycleListener(LifecycleListener listener);

    T addContainerListener(ContainerListener listener);

    T addPropertyChangeListener(PropertyChangeListener listener);

    T setCluster(Cluster cluster);

    T setRealm(Realm realm);

    T setBackgroundProcessorDelay(int delay);

    T setStartStopThreads(int startStopThreads);

}