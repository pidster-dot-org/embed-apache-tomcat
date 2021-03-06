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
package org.pidster.tomcat.embed;

import java.util.Collections;
import java.util.Map;

/**
 * @author pidster
 * 
 */
public interface Tomcat extends Buildable {

    String HTTP11 = "HTTP/1.1";

    String AJP13 = "AJP/1.3";

    String PROTOCOL_NIO = "org.apache.coyote.http11.Http11NioProtocol";

    String PROTOCOL_BIO = "org.apache.coyote.http11.Http11Protocol";

    String PROTOCOL_AJP = "org.apache.coyote.ajp.AjpNioProtocol";

    int DEFAULT_SHUTDOWN_PORT = 8005;

    int DEFAULT_HTTP_PORT = 8080;

    int DEFAULT_AJP_PORT = 8009;

    int DEFAULT_SSL_PORT = 8443;

    int DEFAULT_EXECUTOR_MIN = 5;

    int DEFAULT_EXECUTOR_MAX = 200;

    String DEFAULT_SERVICE_NAME = "Catalina";

    String DEFAULT_EXECUTOR_NAME = "tomcatThreadPool";

    String CATALINA_HOME = "catalina.home";

    String CATALINA_BASE = "catalina.home";

    Map<String, String> EMPTY_MAP = Collections.<String, String> emptyMap();

    /**
     * Start the embedded instance synchronously
     * 
     * @return runtime
     */
    TomcatRuntime start();

    /**
     * Start the embedded instance synchronously, but return after the timeout
     * 
     * @param timeout
     * @return runtime
     */
    TomcatRuntime start(long timeout);

    /**
     * Start the embedded instance synchronously, calling the callback with the
     * runtime instance when complete
     * 
     * @param callback
     */
    void start(Callback<TomcatRuntime> callback);

}
