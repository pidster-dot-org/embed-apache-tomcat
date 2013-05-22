package org.pidster.tomcat.embed;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import java.util.Properties;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class TomcatSimpleTest {

    private static final String TARGET = "http://127.0.0.1:8081/test0/foo";

    private TomcatRuntime runtime;

    private URL url;


    @Before
    public void setupTomcat() throws Exception {

        this.url = new URL(TARGET);

        Properties properties = new Properties();
        properties.put("catalina.base", "src/test/resources");
        properties.put("catalina.home", "src/test/resources");

        Tomcat tomcat = new TomcatFactory(properties).create()
            .newMinimalServer(url.getPort())
                .createApplication("test0")
                .addServletContextListener(DummyListener.class)
                .addServletFilter(DummyFilter.class, "/*")
                .addServlet(DummyServlet.class, "/foo")
            .build();

        this.runtime = tomcat.start(5000L);
    }

    @Test
    public void testFoo() throws Exception {

        HttpURLConnection connection = connect(url);
        connection.connect();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {

            int responseCode = connection.getResponseCode();
            Assert.assertEquals(200, responseCode);

            while (br.ready()) {
                System.out.println(br.readLine());
            }
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
