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

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleListener;
import org.pidster.tomcat.embed.Builder;
import org.pidster.tomcat.embed.Tomcat;
import org.pidster.tomcat.embed.TomcatComponentException;
import org.pidster.tomcat.embed.TomcatLifecyleBuilder;

/**
 * @author pidster
 * 
 * @param <P>
 * @param <T>
 */
public abstract class AbstractLifecycleBuilder<P extends Builder<Tomcat>, T extends Builder<Tomcat>> extends AbstractHierarchicalBuilder<P, T> implements TomcatLifecyleBuilder<T> {

    private static final Logger LOGGER = Logger.getLogger(AbstractLifecycleBuilder.class.getName());

    private Lifecycle lifecycle;

    protected AbstractLifecycleBuilder(P parent) {
        super(parent);
    }

    protected final void setLifecycle(Lifecycle lifecycle) {
        this.lifecycle = lifecycle;
    }

    @Override
    public final T addLifecycleListener(Class<? extends LifecycleListener> listenerClass) {
        try {
            LifecycleListener listener = listenerClass.newInstance();
            return addLifecycleListener(listener);
        } catch (InstantiationException e) {
            throw new TomcatComponentException(e);
        } catch (IllegalAccessException e) {
            throw new TomcatComponentException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public final T addLifecycleListener(LifecycleListener listener) {
        LOGGER.log(Level.FINE, "addLifecycleListener() {0}", listener.getClass().getName());
        lifecycle.addLifecycleListener(listener);
        return (T) this;
    }

}
