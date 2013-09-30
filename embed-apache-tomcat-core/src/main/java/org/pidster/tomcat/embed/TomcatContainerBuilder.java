/*
 * Copyright 2013 pidster
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.pidster.tomcat.embed;

import java.beans.PropertyChangeListener;

import org.apache.catalina.Cluster;
import org.apache.catalina.ContainerListener;
import org.apache.catalina.Realm;
import org.apache.catalina.Valve;

/**
 * @author pidster
 * 
 * @param <P>
 *            parent builder
 * @param <T>
 *            this builder
 */
public interface TomcatContainerBuilder<P extends Builder<Tomcat>, T extends Builder<Tomcat>> extends HierarchicalBuilder<P, Tomcat>, TomcatLifecyleBuilder<T> {

    /**
     * @param valveClass
     * @return this type
     */
    T addValve(Class<? extends Valve> valveClass);

    /**
     * @param valve
     * @return this type
     */
    T addValve(Valve valve);

    /**
     * @param listener
     * @return this type
     */
    T addContainerListener(Class<? extends ContainerListener> listener);

    /**
     * @param listener
     * @return this type
     */
    T addContainerListener(ContainerListener listener);

    /**
     * @param listener
     * @return this type
     */
    T addPropertyChangeListener(Class<? extends PropertyChangeListener> listener);

    /**
     * @param listener
     * @return this type
     */
    T addPropertyChangeListener(PropertyChangeListener listener);

    /**
     * @param cluster
     * @return this type
     */
    T setCluster(Cluster cluster);

    /**
     * @param realm
     * @return this type
     */
    T setRealm(Realm realm);

    /**
     * @param delay
     * @return this type
     */
    T setBackgroundProcessorDelay(int delay);

    /**
     * @param startStopThreads
     * @return this type
     */
    T setStartStopThreads(int startStopThreads);

}