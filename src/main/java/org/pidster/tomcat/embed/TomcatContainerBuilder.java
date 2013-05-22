package org.pidster.tomcat.embed;

import java.beans.PropertyChangeListener;

import org.apache.catalina.Cluster;
import org.apache.catalina.Container;
import org.apache.catalina.ContainerListener;
import org.apache.catalina.Realm;


public interface TomcatContainerBuilder<P extends Builder<Tomcat>, T extends Builder<Tomcat>> extends ParentalBuilder<P, Tomcat>, TomcatLifecyleBuilder<T> {

    Container getContainer();

    T addContainerListener(ContainerListener listener);

    T addPropertyChangeListener(PropertyChangeListener listener);

    T setCluster(Cluster cluster);

    T setRealm(Realm realm);

    T setBackgroundProcessorDelay(int delay);

    T setStartStopThreads(int startStopThreads);

}