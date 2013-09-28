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
package org.pidster.tomcat.embed.sandbox;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pidster.tomcat.dummy.DummyServlet;
import org.pidster.tomcat.embed.Tomcat;
import org.pidster.tomcat.embed.TomcatFactory;
import org.pidster.tomcat.embed.TomcatRuntime;


public class TomcatSimpleTest {

    private TomcatRuntime runtime;

    @Before
    public void setupTomcat() throws Exception {

        Tomcat tomcat = new TomcatFactory().create()
            .newMinimalServer(8082)
                .createApplication("test")
                .addServlet(DummyServlet.class, "/dummy")
            .build();

        this.runtime = tomcat.start(5000L);
    }

    @Test
    public void testServlet() throws Exception {
    	// do some HTTP tests
    }

    @After
    public void teardown() {
        runtime.stop(5000L);
    }

}
