/*
 * Copyright 2013 pidster
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.pidster.tomcat.embed.junit;

import org.apache.tomcat.websocket.server.WsSci;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author pidster
 * 
 */
public class TomcatServerRuleTest {

    @Rule
    public final TomcatServerRule server = new TomcatServerRule();

    @Test
    @TomcatServerConfig(port = 48080)
    public void testOne() {
        System.out.println("port1: " + server.getPort());
    }

    @Test
    @TomcatServerConfig(port = 48081, value = { WsSci.class })
    public void testTwo() {
        System.out.println("port2: " + server.getPort());
    }

    @Test
    @TomcatServerConfig(port = 48082, value = { TestServletContainerInitializer.class })
    public void testThree() {
        System.out.println("port3: " + server.getPort());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testDeploy() {
        server.deploy("foo");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testUndeploy() {
        server.undeploy("foo");
    }

    @Test(expected = IllegalStateException.class)
    public void testAddServletContainerInitializerAfterStart() {
        server.addInitializer(new TestServletContainerInitializer());
    }

}
