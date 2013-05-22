package org.pidster.tomcat.embed;

import java.util.Collections;
import java.util.Map;


public interface Tomcat extends Buildable {

    public static final String HTTP11 = "HTTP/1.1";

    public static final String AJP13 = "AJP/1.3";

    public static final String PROTOCOL_NIO = "org.apache.coyote.http11.Http11NioProtocol";

    public static final String PROTOCOL_BIO = "org.apache.coyote.http11.Http11Protocol";

    public static final String PROTOCOL_AJP = "org.apache.coyote.ajp.AjpNioProtocol";

    public static final int DEFAULT_HTTP_PORT = 8080;

    public static final int DEFAULT_AJP_PORT = 8009;

    public static final int DEFAULT_SSL_PORT = 8443;

    public static final String DEFAULT_SERVICE_NAME = "Tomcat";

    public static final Map<String, String> EMPTY = Collections.<String, String> emptyMap();

    TomcatRuntime start();

    TomcatRuntime start(long timeout);

    void start(Callback<TomcatRuntime> callback);

}
