/*
   Copyright 2013 pid[at]pidster.org

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