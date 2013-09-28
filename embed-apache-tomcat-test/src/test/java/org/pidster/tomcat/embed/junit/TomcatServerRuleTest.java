package org.pidster.tomcat.embed.junit;

import org.apache.tomcat.websocket.server.WsSci;
import org.junit.Rule;
import org.junit.Test;

public class TomcatServerRuleTest {

	@Rule
	public TomcatServerRule server = new TomcatServerRule();

	@Test
	@TomcatServerConfig(port=48080)
	public void testOne() {
		System.out.println("port1: " + server.getPort());
		// server.deploy("foo");
	}

	@Test
	@TomcatServerConfig(port=48081, value={WsSci.class})
	public void testTwo() {
		System.out.println("port2: " + server.getPort());
	}

	@Test
	@TomcatServerConfig(port=48082, value={TestServletContainerInitializer.class})
	public void testThree() {
		System.out.println("port3: " + server.getPort());
	}

}
