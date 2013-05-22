package org.pidster.tomcat.embed.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.pidster.tomcat.embed.Builder;
import org.pidster.tomcat.embed.HierarchicalBuilder;
import org.pidster.tomcat.embed.Tomcat;


public abstract class AbstractHierarchicalBuilder<P extends Builder<Tomcat>, T extends Builder<Tomcat>> implements HierarchicalBuilder<P, Tomcat> {

    private static final Logger logger = Logger.getLogger(AbstractHierarchicalBuilder.class.getName());

    private final P parent;

    private static ClassLoader loader = Thread.currentThread().getContextClassLoader();

    protected AbstractHierarchicalBuilder(P parent) {
        this.parent = parent;
    }

    protected static ClassLoader loader() {
        return loader;
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
