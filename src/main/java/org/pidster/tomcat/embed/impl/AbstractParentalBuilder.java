package org.pidster.tomcat.embed.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.catalina.Container;
import org.pidster.tomcat.embed.Builder;
import org.pidster.tomcat.embed.ParentalBuilder;
import org.pidster.tomcat.embed.Tomcat;


public abstract class AbstractParentalBuilder<P extends Builder<Tomcat>, T extends Builder<Tomcat>> implements ParentalBuilder<P, Tomcat> {

    private static final Logger logger = Logger.getLogger(AbstractParentalBuilder.class.getName());

    private final P parent;

    private Container container;

    private static ClassLoader loader = Thread.currentThread().getContextClassLoader();

    protected AbstractParentalBuilder(P parent) {
        this.parent = parent;
    }

    protected static ClassLoader loader() {
        return loader;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public Container getContainer() {
        return container;
    }

    @Override
    public P parent() {
        logger.log(Level.FINEST, "parent() {0}", this.getClass().getName());
        return this.parent;
    }

    @Override
    public Tomcat build() {
        logger.log(Level.FINEST, "build() {0}", this.getClass().getName());
        return parent().build();
    }

}
