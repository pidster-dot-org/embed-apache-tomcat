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
public interface Constants {

    String SERVER = "org.apache.catalina.core.StandardServer";

    String SERVICE = "org.apache.catalina.core.StandardService";

    String ENGINE = "org.apache.catalina.core.StandardEngine";

    String EXECUTOR = "org.apache.catalina.core.StandardThreadExecutor";

    String HOST = "org.apache.catalina.core.StandardHost";

    String CONTEXT = "org.apache.catalina.core.StandardContext";

    String EXECUTOR_NAME_ATTR = "executorName";
}
