package org.pidster.tomcat.embed;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Properties;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class TomcatSimpleTest {

    private TomcatRuntime runtime;

    @Before
    public void setupTomcat() throws Exception {

        Properties properties = new Properties();
        properties.put("catalina.base", "src/test/resources");
        properties.put("catalina.home", "src/test/resources");

        File catalinaBase = new File("src/test/resources");

        Tomcat tomcat = new TomcatFactory().create()
            .newStandardServer(catalinaBase)
                .createApplication("/test0", "test0")
                .withDefaultConfig()
                .addServletContextListener(DummyListener.class)
                .addServletFilter(DummyFilter.class, "/*")
                .addServlet(DummyServlet.class, "/foo")
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
