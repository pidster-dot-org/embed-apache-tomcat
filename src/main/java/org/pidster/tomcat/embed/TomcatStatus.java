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

import org.apache.catalina.Lifecycle;

/**
 * @author pid[at]pidster.org
 *
 */
public enum TomcatStatus {

    ERROR("error"),

    UNKNOWN("unknown"),

    BEFORE_INIT(Lifecycle.BEFORE_INIT_EVENT),

    AFTER_INIT(Lifecycle.AFTER_INIT_EVENT),

    START(Lifecycle.START_EVENT),

    AFTER_START(Lifecycle.AFTER_START_EVENT),

    BEFORE_START(Lifecycle.BEFORE_START_EVENT),

    STOP(Lifecycle.STOP_EVENT),

    BEFORE_STOP(Lifecycle.BEFORE_STOP_EVENT),

    AFTER_STOP(Lifecycle.AFTER_STOP_EVENT),

    AFTER_DESTROY(Lifecycle.AFTER_DESTROY_EVENT),

    BEFORE_DESTROY(Lifecycle.BEFORE_DESTROY_EVENT),

    PERIODIC(Lifecycle.PERIODIC_EVENT),

    CONFIGURE_START(Lifecycle.CONFIGURE_START_EVENT),

    CONFIGURE_STOP(Lifecycle.CONFIGURE_STOP_EVENT),

    ; // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    private final String status;

    private TomcatStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }

}
