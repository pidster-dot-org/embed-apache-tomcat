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

/**
 * A Callback is used to pass the result of an operation back to a caller that
 * may be asynchronous
 * 
 * @author pidster
 * 
 */
public interface Callback<T> {

    /**
     * @param result
     *            of operation
     */
    void onSuccess(T result);

    /**
     * @param throwable
     *            when failed
     */
    void onFailure(Throwable throwable);

}
