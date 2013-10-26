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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.servlet.ServletContainerInitializer;

/**
 * @author pidster
 * 
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface TomcatServerConfig {

    /**
     * Port zero will cause a random(ish) port to be selected
     */
    int DEFAULT_HTTP_PORT = 0;

    /**
     * 
     */
    long DEFAULT_TIMEOUT = 1000L;

    /**
     * @return port
     */
    int port() default DEFAULT_HTTP_PORT;

    /**
     * @return timeout
     */
    long timeout() default DEFAULT_TIMEOUT;

    /**
     * @return baseDir
     */
    String baseDir() default "";

    /**
     * @return appName
     */
    String appName() default "";

    /**
     * @return initializer
     */
    Class<? extends ServletContainerInitializer>[] value() default {};

}
