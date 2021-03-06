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

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletSecurityElement;

/**
 * @author pid[at]pidster.org
 * 
 */
public final class ServletHolder {

    private final Servlet servlet;

    private final String name;

    private final Map<String, String> initParameters;

    private final boolean asyncSupported;

    private final String[] urlPatterns;

    private final boolean isMatchAfter;

    private final int loadOnStartup;

    private final ServletSecurityElement securityElement;

    public ServletHolder(Servlet servlet, String... urlPatterns) {
        this(servlet, servlet.getClass().getName(), new HashMap<String, String>(), true, true, 0, urlPatterns);
    }

    public ServletHolder(Servlet servlet, Map<String, String> initParameters, String... urlPatterns) {
        this(servlet, servlet.getClass().getName(), initParameters, true, true, 0, urlPatterns);
    }

    public ServletHolder(Servlet servlet, String name, Map<String, String> initParameters, String... urlPatterns) {
        this(servlet, name, initParameters, true, true, 0, urlPatterns);
    }

    public ServletHolder(Servlet servlet, String name, Map<String, String> initParameters, boolean asyncSupported, boolean isMatchAfter, int loadOnStartup, String... urlPatterns) {
        this(servlet, name, initParameters, asyncSupported, isMatchAfter, loadOnStartup, null, urlPatterns);
    }

    public ServletHolder(Servlet servlet, String name, Map<String, String> initParameters, boolean asyncSupported, boolean isMatchAfter, int loadOnStartup, ServletSecurityElement securityElement, String... urlPatterns) {
        super();
        this.servlet = servlet;
        this.name = name;
        this.initParameters = initParameters;
        this.asyncSupported = asyncSupported;
        this.isMatchAfter = isMatchAfter;
        this.loadOnStartup = loadOnStartup;
        this.securityElement = securityElement;
        this.urlPatterns = Arrays.copyOf(urlPatterns, urlPatterns.length);
    }

    /**
     * @return name
     */
    public String name() {
        return name;
    }

    /**
     * @return servlet
     */
    public Servlet servlet() {
        return servlet;
    }

    /**
     * @return initParameters
     */
    public Map<String, String> initParameters() {
        return Collections.unmodifiableMap(initParameters);
    }

    /**
     * @return asyncSupported
     */
    public boolean isAsyncSupported() {
        return asyncSupported;
    }

    /**
     * @return urlPatterns
     */
    public String[] urlPatterns() {
        return Arrays.copyOf(urlPatterns, urlPatterns.length);
    }

    /**
     * @return isMatchAfter
     */
    public boolean isMatchAfter() {
        return isMatchAfter;
    }

    /**
     * @return loadOnStartup
     */
    public int loadOnStartup() {
        return loadOnStartup;
    }

    /**
     * @return securityElement
     */
    public ServletSecurityElement securityElement() {
        return securityElement;
    }

}
