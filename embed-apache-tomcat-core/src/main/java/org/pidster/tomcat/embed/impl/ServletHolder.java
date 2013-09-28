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
package org.pidster.tomcat.embed.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;

/**
 * @author pid[at]pidster.org
 *
 */
public class ServletHolder {

    private final Servlet servlet;

    private final String name;

    private final Map<String, String> initParameters;

    private final boolean asyncSupported;

    private final String[] urlPatterns;

    private final boolean isMatchAfter;

    private final int loadOnStartup;

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
        super();
        this.servlet = servlet;
        this.name = name;
        this.initParameters = initParameters;
        this.asyncSupported = asyncSupported;
        this.isMatchAfter = isMatchAfter;
        this.loadOnStartup = loadOnStartup;
        this.urlPatterns = urlPatterns;
    }

    /**
     * @return
     */
    public String name() {
        return name;
    }

    /**
     * @return
     */
    public Servlet servlet() {
        return servlet;
    }

    /**
     * @return
     */
    public Map<String, String> initParameters() {
        return initParameters;
    }

    /**
     * @return
     */
    public boolean isAsyncSupported() {
        return asyncSupported;
    }

    /**
     * @return
     */
    public String[] urlPatterns() {
        return urlPatterns;
    }


    /**
     * @return
     */
    public boolean isMatchAfter() {
        return isMatchAfter;
    }

    /**
     * @return
     */
    public int loadOnStartup() {
        return loadOnStartup;
    }

}
