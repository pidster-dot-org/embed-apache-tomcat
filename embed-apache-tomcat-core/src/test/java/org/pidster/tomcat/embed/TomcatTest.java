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
package org.pidster.tomcat.embed;

import static org.pidster.tomcat.embed.Tomcat.EMPTY_MAP;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.catalina.servlets.DefaultServlet;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.pidster.tomcat.dummy.DummyFilter;
import org.pidster.tomcat.dummy.DummyListener;
import org.pidster.tomcat.dummy.DummyServlet;
import org.pidster.tomcat.dummy.DummyValve;

public class TomcatTest {

    private TomcatRuntime runtime;

    @Before
    public void setupTomcat() throws Exception {

        File catalinaBase = new File("build/resources/test");

        Tomcat tomcat = new TomcatFactory().create()
            .newServer("localhost", 8005, "SHUTDOWN")
                .setCatalinaBase(catalinaBase)
                .setCatalinaHome(catalinaBase)
                .addService("Catalina", "node1")
                    // .addStandardConnectors()
                    .setBackgroundProcessorDelay(0)
                    .setStartStopThreads(0)
                    .addExecutor("embed-pool-1", "tomcat-exec1-", 200, 5, EMPTY_MAP)
                    .addConnector("HTTP/1.1", 8090, EMPTY_MAP)
                    .addConnector("AJP/1.3", 8019, EMPTY_MAP)
//                        .setCluster(cluster)
//                        .setRealm(realm)
                        .addHost("localhost", "webapps")
//                            .setCluster(cluster)
//                            .setRealm(realm)
                            .createApplication("/test0", "test0")
                                .withDefaultConfig()
                                .addServletContextListener(DummyListener.class)
                                .addServletFilter(DummyFilter.class, "/*")
                                .addServlet(DummyServlet.class, "/foo")
//                                .setCluster(cluster)
//                                .setRealm(realm)
                                .addValve(DummyValve.class)
                                .parent()
                            .createApplication("/test1", "test1", EMPTY_MAP)
                                .addServletContextListener(DummyListener.class, EMPTY_MAP)
                                .addServletFilter(DummyFilter.class, EMPTY_MAP, "/*")
                                .addServlet(DummyServlet.class, EMPTY_MAP, "/foo")
                                .addServlet("default", DefaultServlet.class, EMPTY_MAP, "/")
                                .addValve(DummyValve.class)
                                .addWelcomeFile("index.html")
                            .parent()
                            .addApplication("/test2", "test2", "test2", EMPTY_MAP)
                            .addApplication("/test3", "test3", "test3", EMPTY_MAP)
                        .build();

        this.runtime = tomcat.start(5000L);
    }

    @Test
    public void test0IndexHtml() throws Exception {

        URL url = new URL("http://127.0.0.1:8090/test0/index.html");

        HttpURLConnection connection = connect(url);
        connection.connect();

        try (InputStream is = connection.getInputStream()) {

            int responseCode = connection.getResponseCode();
            Assert.assertEquals(200, responseCode);
        }
        catch (IOException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void test0IndexFile() throws Exception {

        URL url = new URL("http://127.0.0.1:8090/test0/");

        HttpURLConnection connection = connect(url);
        connection.connect();

        try (InputStream is = connection.getInputStream()) {

            int responseCode = connection.getResponseCode();
            Assert.assertEquals(200, responseCode);
        }
        catch (IOException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void test0Foo() throws Exception {

        URL url = new URL("http://127.0.0.1:8090/test0/foo");

        HttpURLConnection connection = connect(url);
        connection.connect();

        try (InputStream is = connection.getInputStream()) {

            int responseCode = connection.getResponseCode();
            Assert.assertEquals(200, responseCode);
        }
        catch (IOException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void test1IndexFile() throws Exception {

        URL url = new URL("http://127.0.0.1:8090/test1/");

        HttpURLConnection connection = connect(url);
        connection.connect();

        try (InputStream is = connection.getInputStream()) {

            int responseCode = connection.getResponseCode();
            Assert.assertEquals(200, responseCode);
        }
        catch (IOException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @After
    public void teardown() {
        if (runtime == null) {
            return;
        }

        runtime.stop(5000L);
    }

    private HttpURLConnection connect(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestMethod("GET");
        connection.addRequestProperty("Host", "localhost");
        connection.addRequestProperty("Accept", "*");

        return connection;
    }

}
