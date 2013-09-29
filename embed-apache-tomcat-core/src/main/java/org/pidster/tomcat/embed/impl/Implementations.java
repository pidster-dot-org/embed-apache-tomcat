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
package org.pidster.tomcat.embed.impl;

/**
 * @author pidster
 *
 */
public class Implementations {

	public static final String SERVER = "org.apache.catalina.core.StandardServer";

	public static final String SERVICE = "org.apache.catalina.core.StandardService";

	public static final String ENGINE = "org.apache.catalina.core.StandardEngine";

	public static final String EXECUTOR = "org.apache.catalina.core.StandardThreadExecutor";

	public static final String HOST = "org.apache.catalina.core.StandardHost";

	public static final String CONTEXT = "org.apache.catalina.core.StandardContext";

    public static final String[] SILENT_CLASSES = new String[] {
        "org.apache.coyote.AbstractProtocol",
        "org.apache.coyote.ajp.AjpNioProtocol",
        "org.apache.coyote.http11.Http11Protocol",
        "org.apache.coyote.http11.Http11NioProtocol",
        "org.apache.catalina.core.ApplicationContext",
        "org.apache.catalina.core.AprLifecycleListener",
        "org.apache.catalina.core.StandardService",
        "org.apache.catalina.core.StandardEngine",
        "org.apache.catalina.mbeans.GlobalResourcesLifecycleListener",
        "org.apache.catalina.startup.Catalina",
        "org.apache.catalina.startup.ContextConfig",
        "org.apache.tomcat.util.net.NioSelectorPool",
    };
}
