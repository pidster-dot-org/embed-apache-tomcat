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

import org.pidster.tomcat.embed.Builder;
import org.pidster.tomcat.embed.HierarchicalBuilder;
import org.pidster.tomcat.embed.Tomcat;

/**
 * @author pidster
 * 
 * @param <P>
 * @param <T>
 */
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
