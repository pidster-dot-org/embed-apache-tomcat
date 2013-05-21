package org.pidster.tomcat.embed.impl;

import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.catalina.Cluster;
import org.apache.catalina.Container;
import org.apache.catalina.ContainerListener;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.Realm;
import org.pidster.tomcat.embed.Builder;
import org.pidster.tomcat.embed.TomcatContainerBuilder;
import org.pidster.tomcat.embed.Tomcat;


public abstract class AbstractContainerBuilder<P extends Builder<Tomcat>, T extends Builder<Tomcat>> extends AbstractParentalBuilder<P, T> implements TomcatContainerBuilder<P, T> {

    private static final Logger logger = Logger.getLogger(AbstractContainerBuilder.class.getName());

    private Container container;

    protected AbstractContainerBuilder(P parent) {
        super(parent);
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T addLifecycleListener(LifecycleListener listener) {
        logger.log(Level.FINE, "addLifecycleListener({0})", listener.getClass().getName());
        container.addLifecycleListener(listener);
        return (T) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T addContainerListener(ContainerListener listener) {
        logger.log(Level.FINE, "addContainerListener({0})", listener.getClass().getName());
        container.addContainerListener(listener);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T addPropertyChangeListener(PropertyChangeListener listener) {
        logger.log(Level.FINE, "addPropertyChangeListener({0})", listener.getClass().getName());
        container.addPropertyChangeListener(listener);
        return (T) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T setCluster(Cluster cluster) {
        logger.log(Level.FINE, "setCluster({0})", cluster.getClass().getName());
        cluster.setContainer(container);
        container.setCluster(cluster);
        return (T) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T setRealm(Realm realm) {
        logger.log(Level.FINE, "setRealm({0})", realm.getClass().getName());
        realm.setContainer(container);
        container.setRealm(realm);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T setBackgroundProcessorDelay(int delay) {
        container.setBackgroundProcessorDelay(delay);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T setStartStopThreads(int startStopThreads) {
        container.setStartStopThreads(startStopThreads);
        return (T) this;
    }

}
