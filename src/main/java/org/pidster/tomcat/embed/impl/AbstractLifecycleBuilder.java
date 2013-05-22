package org.pidster.tomcat.embed.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.catalina.Container;
import org.apache.catalina.LifecycleListener;
import org.pidster.tomcat.embed.Builder;
import org.pidster.tomcat.embed.Tomcat;
import org.pidster.tomcat.embed.TomcatLifecyleBuilder;


public abstract class AbstractLifecycleBuilder<P extends Builder<Tomcat>, T extends Builder<Tomcat>> extends AbstractParentalBuilder<P, T> implements TomcatLifecyleBuilder<T> {

    private static final Logger logger = Logger.getLogger(AbstractLifecycleBuilder.class.getName());

    protected AbstractLifecycleBuilder(P parent) {
        super(parent);
    }

    @Override
    public T addLifecycleListener(Class<? extends LifecycleListener> listenerClass) {
        try {
            LifecycleListener listener = listenerClass.newInstance();
            return addLifecycleListener(listener);
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public T addLifecycleListener(LifecycleListener listener) {
        logger.log(Level.FINE, "addLifecycleListener({0})", listener.getClass().getName());
        getContainer().addLifecycleListener(listener);
        return (T) this;
    }

}
