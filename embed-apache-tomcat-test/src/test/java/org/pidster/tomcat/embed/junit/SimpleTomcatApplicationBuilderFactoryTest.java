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

import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Method;

import org.junit.Test;
import org.pidster.tomcat.embed.TomcatApplicationBuilder;

/**
 * @author pidster
 *
 */
public class SimpleTomcatApplicationBuilderFactoryTest {

    @Test
    @TomcatServerConfig
    public void test() throws Exception {
        Method m = SimpleTomcatApplicationBuilderFactoryTest.class.getMethod("test");
        TomcatServerConfig annotation = m.getAnnotation(TomcatServerConfig.class);

        assertNotNull(annotation);

        SimpleTomcatApplicationBuilderFactory factory = new SimpleTomcatApplicationBuilderFactory();
        TomcatApplicationBuilder builder = factory.getBuilder(annotation);

        assertNotNull(builder);
    }

}
