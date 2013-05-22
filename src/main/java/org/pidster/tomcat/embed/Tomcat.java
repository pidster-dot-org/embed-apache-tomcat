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
