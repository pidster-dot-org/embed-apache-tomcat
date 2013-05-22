package org.pidster.tomcat.embed;

import static org.pidster.tomcat.embed.Tomcat.EMPTY;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Properties;

import org.apache.catalina.servlets.DefaultServlet;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TomcatTest {

    private TomcatRuntime runtime;

    @Before
    public void setupTomcat() throws Exception {

        Properties properties = new Properties();
        properties.put("catalina.base", "src/test/resources");
        properties.put("catalina.home", "src/test/resources");

        File catalinaBase = new File("src/test/resources");

        Tomcat tomcat = new TomcatFactory(properties).create()
            .newServer("localhost", 8005, "SHUTDOWN")
                .setCatalinaBase(catalinaBase)
                .setCatalinaHome(catalinaBase)
                .addService("Catalina", "node1")
                    // .addStandardConnectors()
                    .setBackgroundProcessorDelay(0)
                    .setStartStopThreads(0)
                    .addExecutor("embed-pool-1", "tomcat-exec1-", 200, 5, EMPTY)
                    .addConnector("HTTP/1.1", 8090, EMPTY)
                    .addConnector("AJP/1.3", 8019, EMPTY)
//                        .setCluster(cluster)
//                        .setRealm(realm)
                        .addHost("localhost", "webapps")
//                            .setCluster(cluster)
//                            .setRealm(realm)
                            .createApplication("/test0", "test0", EMPTY)
                                .withDefaultConfig()
                                .addServletContextListener(DummyListener.class, EMPTY)
                                .addServletFilter(DummyFilter.class, EMPTY, "/*")
                                .addServlet("dummy", DummyServlet.class, EMPTY, "/foo")
//                                .setCluster(cluster)
//                                .setRealm(realm)
                                .parent()
                            .createApplication("/test1", "test1", EMPTY)
                                .addServletContextListener(DummyListener.class, EMPTY)
                                .addServletFilter(DummyFilter.class, EMPTY, "/*")
                                .addServlet(DummyServlet.class, EMPTY, "/foo")
                                .addServlet("default", DefaultServlet.class, EMPTY, "/")
                                .addValve(DummyValve.class)
                                .addWelcomeFile("index.html")
                            .parent()
                            .addApplication("/test2", "test2", "test2", EMPTY)
                            .addApplication("/test3", "test3", "test3", EMPTY)
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
