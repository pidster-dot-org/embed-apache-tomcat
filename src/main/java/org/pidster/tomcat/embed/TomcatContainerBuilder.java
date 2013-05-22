package org.pidster.tomcat.embed;

import java.beans.PropertyChangeListener;

import org.apache.catalina.Cluster;
import org.apache.catalina.ContainerListener;
import org.apache.catalina.Realm;
import org.apache.catalina.Valve;


public interface TomcatContainerBuilder<P extends Builder<Tomcat>, T extends Builder<Tomcat>> extends HierarchicalBuilder<P, Tomcat>, TomcatLifecyleBuilder<T> {

    T addValve(Class<? extends Valve> valveClass);

    T addValve(Valve valve);

    T addContainerListener(Class<? extends ContainerListener> listener);

    T addContainerListener(ContainerListener listener);

    T addPropertyChangeListener(Class<? extends PropertyChangeListener> listener);

    T addPropertyChangeListener(PropertyChangeListener listener);

    T setCluster(Cluster cluster);

    T setRealm(Realm realm);

    T setBackgroundProcessorDelay(int delay);

    T setStartStopThreads(int startStopThreads);

}