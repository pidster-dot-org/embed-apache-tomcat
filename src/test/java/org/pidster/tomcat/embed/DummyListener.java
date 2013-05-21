package org.pidster.tomcat.embed;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DummyListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("foo", "bar");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().removeAttribute("foo");
    }

}
