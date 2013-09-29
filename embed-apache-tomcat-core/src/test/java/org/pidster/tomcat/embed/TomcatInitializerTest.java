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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.pidster.tomcat.dummy.DummyContainerInitializer;


public class TomcatInitializerTest {

    private static final String TARGET = "http://127.0.0.1:9081/test/dummy";

    private TomcatRuntime runtime;

    private URL url;


    @Before
    public void setupTomcat() throws Exception {

        this.url = new URL(TARGET);

        Properties properties = new Properties();
        properties.put("catalina.base", "build/resources/test");
        properties.put("catalina.home", "build/resources/test");

        Tomcat tomcat = new TomcatFactory(properties).create()
            .newMinimalServer(url.getPort())
                .createApplication("test")
                .setStartStopThreads(1)
                .addServletContainerInitializer(DummyContainerInitializer.class)
            .build();

        this.runtime = tomcat.start(5000L);

        System.out.println("started");
    }

    @Test
    public void testInitializer() throws Exception {

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
