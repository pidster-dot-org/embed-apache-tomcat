package org.pidster.tomcat.embed.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.pidster.tomcat.embed.Builder;
import org.pidster.tomcat.embed.Tomcat;


public abstract class AbstractParentalBuilder<P extends Builder<Tomcat>, T extends Builder<Tomcat>> implements Builder<Tomcat> {

    private static final Logger logger = Logger.getLogger(AbstractParentalBuilder.class.getName());

    private final P parent;

    private static ClassLoader loader = Thread.currentThread().getContextClassLoader();

    protected AbstractParentalBuilder(P parent) {
        this.parent = parent;
    }

    protected static ClassLoader loader() {
        return loader;
    }

    public P parent() {
        logger.log(Level.FINE, "parent() {0}", this.getClass().getName());
        return this.parent;
    }

    @Override
    public Tomcat build() {
        logger.log(Level.FINE, "build() {0}", this.getClass().getName());
        return parent().build();
    }

}
