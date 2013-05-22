package org.pidster.tomcat.embed.impl;

import org.pidster.tomcat.embed.Builder;
import org.pidster.tomcat.embed.HierarchicalBuilder;
import org.pidster.tomcat.embed.Tomcat;


public abstract class AbstractHierarchicalBuilder<P extends Builder<Tomcat>, T extends Builder<Tomcat>> implements HierarchicalBuilder<P, Tomcat> {

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
        return parent;
    }

    @Override
    public Tomcat build() {
        return parent().build();
    }

}
