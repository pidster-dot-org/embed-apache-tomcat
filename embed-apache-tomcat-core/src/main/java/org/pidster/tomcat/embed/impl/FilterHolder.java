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
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;

/**
 * @author pid[at]pidster.org
 * 
 */
public final class FilterHolder {

    private final Filter filter;

    private final String name;

    private final Map<String, String> initParameters;

    private final boolean asyncSupported;

    private final String[] servletNames;

    private final String[] urlPatterns;

    private final boolean isMatchAfter;

    private final EnumSet<DispatcherType> dispatcherTypes;

    public FilterHolder(Filter filter, String... urlPatterns) {
        this(filter, filter.getClass().getName(), new HashMap<String, String>(), true, new String[] {}, urlPatterns, true, EnumSet.of(DispatcherType.REQUEST));
    }

    public FilterHolder(Filter filter, Map<String, String> initParameters, String... urlPatterns) {
        this(filter, filter.getClass().getName(), initParameters, true, new String[] {}, urlPatterns, true, EnumSet.of(DispatcherType.REQUEST));
    }

    public FilterHolder(Filter filter, Map<String, String> initParameters, EnumSet<DispatcherType> dispatcherTypes, String... urlPatterns) {
        this(filter, filter.getClass().getName(), initParameters, true, new String[] {}, urlPatterns, true, dispatcherTypes);
    }

    public FilterHolder(Filter filter, String name, Map<String, String> initParameters, boolean asyncSupported, String[] servletNames, String[] urlPatterns, boolean isMatchAfter, EnumSet<DispatcherType> dispatcherTypes) {
        super();
        this.filter = filter;
        this.name = name;
        this.initParameters = initParameters;
        this.asyncSupported = asyncSupported;
        this.servletNames = Arrays.copyOf(servletNames, servletNames.length);
        this.urlPatterns = Arrays.copyOf(urlPatterns, urlPatterns.length);
        this.isMatchAfter = isMatchAfter;
        this.dispatcherTypes = dispatcherTypes;
    }

    /**
     * @return name
     */
    public String name() {
        return name;
    }

    /**
     * @return filter
     */
    public Filter filter() {
        return filter;
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
    public boolean setAsyncSupported() {
        return asyncSupported;
    }

    /**
     * @return urlPatterns
     */
    public String[] urlPatterns() {
        return Arrays.copyOf(urlPatterns, urlPatterns.length);
    }

    /**
     * @return servletNames
     */
    public String[] servletNames() {
        return Arrays.copyOf(servletNames, servletNames.length);
    }

    /**
     * @return dispatcherTypes
     */
    public EnumSet<DispatcherType> dispatcherTypes() {
        return dispatcherTypes;
    }

    /**
     * @return isMatchAfter
     */
    public boolean isMatchAfter() {
        return isMatchAfter;
    }

}
