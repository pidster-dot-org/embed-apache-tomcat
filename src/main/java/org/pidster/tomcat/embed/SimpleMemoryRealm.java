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

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.realm.GenericPrincipal;
import org.apache.catalina.realm.RealmBase;

/**
 * @author pid[at]pidster.org
 *
 */
public class SimpleMemoryRealm extends RealmBase {

    private final Map<String, String> userPass = new HashMap<>();
    private final Map<String, List<String>> userRoles = new HashMap<>();
    private final Map<String, Principal> userPrincipals = new HashMap<>();

    @Override
    protected String getName() {
        return "SimpleMemory";
    }

    @Override
    protected String getPassword(String username) {
        return userPass.get(username);
    }

    @Override
    protected Principal getPrincipal(String username) {
        Principal p = userPrincipals.get(username);
        if (p == null) {
            String pass = userPass.get(username);
            if (pass != null) {
                p = new GenericPrincipal(username, pass,
                        userRoles.get(username));
                userPrincipals.put(username, p);
            }
        }
        return p;
    }

    public String addUserPassword(String username, String password) {
        return userPass.put(username, password);
    }

    public String removeUser(String username) {
        if (userPrincipals.containsKey(username)) {
            userPrincipals.remove(username);
        }
        return userPass.remove(username);
    }

    public String addUserRole(String username, String role) {
        return userPass.put(username, role);
    }

    public boolean removeUserRole(String username, String role) {
        if (userRoles.containsKey(username)) {
            List<String> roles = userRoles.get(username);
            if (roles.contains(role)) {
                roles.remove(role);
                userRoles.put(username, roles);
                return true;
            }
        }
        return false;
    }

    public List<String> removeRoles(String username) {
        return userRoles.remove(username);
    }

}
