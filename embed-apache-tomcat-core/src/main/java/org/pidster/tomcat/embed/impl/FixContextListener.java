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
package org.pidster.tomcat.embed.impl;

import org.apache.catalina.Context;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.authenticator.NonLoginAuthenticator;
import org.apache.tomcat.util.descriptor.web.LoginConfig;

/**
 *
 */
class FixContextListener implements LifecycleListener {

    @Override
    public void lifecycleEvent(LifecycleEvent event) {
        try {
            Context context = (Context) event.getLifecycle();
            if (event.getType().equals(Lifecycle.CONFIGURE_START_EVENT)) {
                context.setConfigured(true);
            }
            // LoginConfig is required to process @ServletSecurity
            // annotations
            if (context.getLoginConfig() == null) {
                context.setLoginConfig(new LoginConfig("NONE", null, null, null));
                context.getPipeline().addValve(new NonLoginAuthenticator());
            }
        } catch (ClassCastException e) {
            return;
        }
    }
}
