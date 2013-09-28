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
package org.pidster.tomcat.dummy;

import java.util.EnumSet;
import java.util.Set;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * @author pid[at]pidster.org
 *
 */
public class DummyContainerInitializer implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> c, ServletContext context)
            throws ServletException {

        context.addListener(new DummyListener());

        FilterRegistration.Dynamic filterReg = context.addFilter("filter", new DummyFilter());
        filterReg.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");

        ServletRegistration.Dynamic servletReg = context.addServlet("dummy", new DummyServlet());
        servletReg.addMapping("/dummy");
    }

}
