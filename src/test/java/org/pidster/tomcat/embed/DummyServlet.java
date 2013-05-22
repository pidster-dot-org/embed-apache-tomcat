package org.pidster.tomcat.embed;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DummyServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String foo = (String) req.getAttribute("man");
        String chu = (String) req.getAttribute("chu");
        resp.getWriter().println(String.format("foo:%s %n", foo));
        resp.getWriter().println(String.format("chu:%s %n", chu));
        resp.getWriter().println(String.format("thread:%s %n", Thread.currentThread().getName()));
    }

}
