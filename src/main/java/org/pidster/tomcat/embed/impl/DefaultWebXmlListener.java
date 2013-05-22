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

import org.apache.catalina.Context;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;

/**
 * @author pid[at]pidster.org
 *
 */
class DefaultWebXmlListener implements LifecycleListener {

    @Override
    public void lifecycleEvent(LifecycleEvent event) {
        if (Lifecycle.BEFORE_START_EVENT.equals(event.getType())) {
            initWebappDefaults((Context) event.getLifecycle());
        }
    }

    public static void initWebappDefaults(Context context) {

        // Default servlet

        

//        Wrapper servlet = addServlet(
//                ctx, "default", "org.apache.catalina.servlets.DefaultServlet");
//        servlet.setLoadOnStartup(1);
//        servlet.setOverridable(true);
//
//        // JSP servlet (by class name - to avoid loading all deps)
//        servlet = addServlet(
//                ctx, "jsp", "org.apache.jasper.servlet.JspServlet");
//        servlet.addInitParameter("fork", "false");
//        servlet.setLoadOnStartup(3);
//        servlet.setOverridable(true);
//
//        // Servlet mappings
//        ctx.addServletMapping("/", "default");
//        ctx.addServletMapping("*.jsp", "jsp");
//        ctx.addServletMapping("*.jspx", "jsp");
//
//        // Sessions
//        ctx.setSessionTimeout(30);
//
//        // MIME mappings
//        for (int i = 0; i < DEFAULT_MIME_MAPPINGS.length;) {
//            ctx.addMimeMapping(DEFAULT_MIME_MAPPINGS[i++],
//                    DEFAULT_MIME_MAPPINGS[i++]);
//        }

        // Welcome files
//        ctx.addWelcomeFile("index.html");
//        ctx.addWelcomeFile("index.htm");
//        ctx.addWelcomeFile("index.jsp");
    }

}