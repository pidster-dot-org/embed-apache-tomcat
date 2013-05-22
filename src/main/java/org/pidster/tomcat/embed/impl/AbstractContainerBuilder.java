package org.pidster.tomcat.embed.impl;

import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.catalina.Cluster;
import org.apache.catalina.ContainerListener;
import org.apache.catalina.Realm;
import org.pidster.tomcat.embed.Builder;
import org.pidster.tomcat.embed.TomcatContainerBuilder;
import org.pidster.tomcat.embed.Tomcat;


public abstract class AbstractContainerBuilder<P extends Builder<Tomcat>, T extends Builder<Tomcat>> extends AbstractLifecycleBuilder<P, T> implements TomcatContainerBuilder<P, T> {

    private static final Logger logger = Logger.getLogger(AbstractContainerBuilder.class.getName());

    protected AbstractContainerBuilder(P parent) {
        super(parent);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T addContainerListener(ContainerListener listener) {
        logger.log(Level.FINE, "addContainerListener({0})", listener.getClass().getName());
        getContainer().addContainerListener(listener);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T addPropertyChangeListener(PropertyChangeListener listener) {
        logger.log(Level.FINE, "addPropertyChangeListener({0})", listener.getClass().getName());
        getContainer().addPropertyChangeListener(listener);
        return (T) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T setCluster(Cluster cluster) {
        logger.log(Level.FINE, "setCluster({0})", cluster.getClass().getName());
        cluster.setContainer(getContainer());
        getContainer().setCluster(cluster);
        return (T) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T setRealm(Realm realm) {
        logger.log(Level.FINE, "setRealm({0})", realm.getClass().getName());
        realm.setContainer(getContainer());
        getContainer().setRealm(realm);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T setBackgroundProcessorDelay(int delay) {
        getContainer().setBackgroundProcessorDelay(delay);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T setStartStopThreads(int startStopThreads) {
        getContainer().setStartStopThreads(startStopThreads);
        return (T) this;
    }

}
