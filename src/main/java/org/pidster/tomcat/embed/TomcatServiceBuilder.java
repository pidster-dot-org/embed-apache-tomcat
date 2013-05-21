package org.pidster.tomcat.embed;

import java.util.Map;

import org.apache.catalina.Host;


public interface TomcatServiceBuilder extends Builder<Tomcat>, ContainerBuilder<TomcatServerBuilder, TomcatServiceBuilder>, Collector<TomcatServiceBuilder, Host> {

    public static final String PROTOCOL_NIO = "org.apache.coyote.http11.Http11NioProtocol";

    public static final String PROTOCOL_BIO = "org.apache.coyote.http11.Http11Protocol";

    public static final String PROTOCOL_AJP = "org.apache.coyote.ajp.AjpNioProtocol";

    TomcatServerBuilder parent();

    TomcatServiceBuilder addStandardConnectors();

    TomcatServiceBuilder addBioExecutor(int port, int minSize, int maxSize, Map<String, String> config);

    TomcatServiceBuilder addNioExecutor(int port, int minSize, int maxSize, Map<String, String> config);

    TomcatServiceBuilder addExecutor(String name, String prefix, int minSize, int maxSize, Map<String, String> config);

    TomcatServiceBuilder addConnector(String protocol, int port);

    TomcatServiceBuilder addConnector(String protocol, int port, Map<String, String> config);

    TomcatHostBuilder addHost(String name, String appBase);

}