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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * @author pid[at]pidster.org
 *
 */
public class InternalContainerInitializer implements ServletContainerInitializer {

    private final Set<String> roleNames = new HashSet<>();

    private final Map<String, Object> attributes = new HashMap<>();

    private final Map<String, String> initParameters = new HashMap<>();

    private final Set<ServletContextListener> instantiatedListeners = new HashSet<>();

    private final Set<FilterHolder> filterHolders = new HashSet<>();

    private final Set<ServletHolder> servletHolders = new HashSet<>();

    /*
     * (non-Javadoc)
     * @see javax.servlet.ServletContainerInitializer#onStartup(java.util.Set, javax.servlet.ServletContext)
     */
    @Override
    public void onStartup(Set<Class<?>> c, ServletContext context) throws ServletException {

        context.declareRoles(roleNames.toArray(new String[roleNames.size()]));

        Set<String> attrNames = attributes.keySet();
        for (String name : attrNames) {
            Object value = attributes.get(name);
            context.setAttribute(name, value);
        }

        Set<String> paramNames = initParameters.keySet();
        for (String name : paramNames) {
            String value = initParameters.get(name);
            context.setInitParameter(name, value);
        }

        for (ServletContextListener listener : instantiatedListeners) {
            context.addListener(listener);
        }

        for (FilterHolder filterHolder : filterHolders) {
            FilterRegistration.Dynamic filterReg = context.addFilter(filterHolder.name(), filterHolder.filter());
            filterReg.setInitParameters(filterHolder.initParameters());
            filterReg.setAsyncSupported(filterHolder.setAsyncSupported());
            if (filterHolder.servletNames().length > 0) {
                filterReg.addMappingForServletNames(filterHolder.dispatcherTypes(), filterHolder.isMatchAfter(), filterHolder.servletNames());
            }
            else {
                filterReg.addMappingForUrlPatterns(filterHolder.dispatcherTypes(), filterHolder.isMatchAfter(), filterHolder.urlPatterns());
            }
        }

        for (ServletHolder servletHolder : servletHolders) {
            ServletRegistration.Dynamic servletReg = context.addServlet(servletHolder.name(), servletHolder.servlet());
            servletReg.setInitParameters(servletHolder.initParameters());
            servletReg.setAsyncSupported(servletHolder.isAsyncSupported());
            servletReg.addMapping(servletHolder.urlPatterns());
            servletReg.setLoadOnStartup(servletHolder.loadOnStartup());
            if (servletHolder.securityElement() != null) {
            	servletReg.setServletSecurity(servletHolder.securityElement());
            }
        }

        // clear references
        roleNames.clear();
        attributes.clear();
        initParameters.clear();
        instantiatedListeners.clear();
        filterHolders.clear();
        servletHolders.clear();
    }

    /**
     * @param e
     * @return added
     */
    public boolean add(ServletContextListener e) {
        return instantiatedListeners.add(e);
    }

    /**
     * @param e
     * @return added
     */
    public boolean add(FilterHolder e) {
        return filterHolders.add(e);
    }

    /**
     * @param e
     * @return added
     */
    public boolean add(ServletHolder e) {
        return servletHolders.add(e);
    }

    /**
     * @param attribute
     * @param value
     */
    public void setContextAttribute(String attribute, Object value) {
        attributes.put(attribute, value);
    }

    /**
     * @param initParameter
     * @param value
     */
    public void setContextInitParameter(String initParameter, String value) {
        initParameters.put(initParameter, value);
    }

}
