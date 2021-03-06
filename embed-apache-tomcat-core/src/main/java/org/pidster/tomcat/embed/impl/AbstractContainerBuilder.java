/*
   Copyright 2013 pidster

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package org.pidster.tomcat.embed.impl;

import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.catalina.Cluster;
import org.apache.catalina.Container;
import org.apache.catalina.ContainerListener;
import org.apache.catalina.Realm;
import org.apache.catalina.Valve;
import org.pidster.tomcat.embed.Builder;
import org.pidster.tomcat.embed.Tomcat;
import org.pidster.tomcat.embed.TomcatContainerBuilder;

/**
 * @author pidster
 * 
 * @param <P>
 * @param <T>
 */
public abstract class AbstractContainerBuilder<P extends Builder<Tomcat>, T extends Builder<Tomcat>> extends AbstractLifecycleBuilder<P, T> implements TomcatContainerBuilder<P, T> {

    private static final Logger LOGGER = Logger.getLogger(AbstractContainerBuilder.class.getName());

    private Container container;

    protected AbstractContainerBuilder(P parent) {
        super(parent);
    }

    protected final void setContainer(Container container) {
        super.setLifecycle(container);
        this.container = container;
    }

    protected final Container getContainer() {
        return container;
    }

    @Override
    public final T addValve(Class<? extends Valve> clazz) {
        Valve instance = InstanceConfigurer.newInstance(clazz);
        return addValve(instance);
    }

    @Override
    @SuppressWarnings("unchecked")
    public final T addValve(Valve valve) {
        LOGGER.log(Level.FINE, "addValve({0})", valve.getClass().getName());
        container.getPipeline().addValve(valve);
        return (T) this;
    }

    @Override
    public final T addContainerListener(Class<? extends ContainerListener> clazz) {
        ContainerListener instance = InstanceConfigurer.newInstance(clazz);
        return addContainerListener(instance);
    }

    @Override
    @SuppressWarnings("unchecked")
    public final T addContainerListener(ContainerListener listener) {
        LOGGER.log(Level.FINE, "addContainerListener({0})", listener.getClass().getName());
        container.addContainerListener(listener);
        return (T) this;
    }

    @Override
    public final T addPropertyChangeListener(Class<? extends PropertyChangeListener> clazz) {
        PropertyChangeListener instance = InstanceConfigurer.newInstance(clazz);
        return addPropertyChangeListener(instance);
    }

    @Override
    @SuppressWarnings("unchecked")
    public final T addPropertyChangeListener(PropertyChangeListener listener) {
        LOGGER.log(Level.FINE, "addPropertyChangeListener({0})", listener.getClass().getName());
        container.addPropertyChangeListener(listener);
        return (T) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final T setCluster(Cluster cluster) {
        LOGGER.log(Level.FINE, "setCluster({0})", cluster.getClass().getName());
        cluster.setContainer(getContainer());
        container.setCluster(cluster);
        return (T) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final T setRealm(Realm realm) {
        LOGGER.log(Level.FINE, "setRealm({0})", realm.getClass().getName());
        realm.setContainer(getContainer());
        container.setRealm(realm);
        return (T) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final T setBackgroundProcessorDelay(int delay) {
        container.setBackgroundProcessorDelay(delay);
        return (T) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final T setStartStopThreads(int startStopThreads) {
        container.setStartStopThreads(startStopThreads);
        return (T) this;
    }

}
