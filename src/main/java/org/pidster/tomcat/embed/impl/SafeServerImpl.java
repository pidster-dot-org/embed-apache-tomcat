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

import java.io.File;

import javax.naming.Context;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.Server;
import org.apache.catalina.Service;
import org.apache.catalina.deploy.NamingResourcesImpl;
import org.apache.catalina.startup.Catalina;

/**
 * @author pid[at]pidster.org
 *
 */
public class SafeServerImpl implements Server {

    private final Server server;

    /**
     * @param server
     */
    public SafeServerImpl(Server server) {
        this.server = server;
    }

    @Override
    public NamingResourcesImpl getGlobalNamingResources() {
        return server.getGlobalNamingResources();
    }

    @Override
    public Context getGlobalNamingContext() {
        return server.getGlobalNamingContext();
    }

    @Override
    public int getPort() {
        return server.getPort();
    }

    @Override
    public String getAddress() {
        return server.getAddress();
    }

    @Override
    public String getShutdown() {
        return server.getShutdown();
    }

    @Override
    public ClassLoader getParentClassLoader() {
        return server.getParentClassLoader();
    }

    @Override
    public Catalina getCatalina() {
        return null;
    }

    @Override
    public File getCatalinaBase() {
        return server.getCatalinaBase();
    }

    @Override
    public File getCatalinaHome() {
        return server.getCatalinaHome();
    }

    @Override
    public Service findService(String name) {
        return server.findService(name);
    }

    @Override
    public Service[] findServices() {
        return server.findServices();
    }

    @Override
    public void removeService(Service service) {
        server.removeService(service);
    }

    @Override
    public void addLifecycleListener(LifecycleListener listener) {
        server.addLifecycleListener(listener);
    }

    @Override
    public LifecycleListener[] findLifecycleListeners() {
        return server.findLifecycleListeners();
    }

    @Override
    public void removeLifecycleListener(LifecycleListener listener) {
        server.removeLifecycleListener(listener);
    }

    @Override
    public LifecycleState getState() {
        return server.getState();
    }

    @Override
    public String getStateName() {
        return server.getStateName();
    }

    @Override
    public void setShutdown(String shutdown) {
        server.setShutdown(shutdown);
    }

    /* (non-Javadoc)
     * @see org.apache.catalina.Lifecycle#init()
     */
    @Override
    public void init() throws LifecycleException {
        throw new UnsupportedOperationException("Server.init() method should not be called directly");
    }

    /* (non-Javadoc)
     * @see org.apache.catalina.Lifecycle#start()
     */
    @Override
    public void start() throws LifecycleException {
        throw new UnsupportedOperationException("Server.start() method should not be called directly");
    }

    /* (non-Javadoc)
     * @see org.apache.catalina.Lifecycle#stop()
     */
    @Override
    public void stop() throws LifecycleException {
        throw new UnsupportedOperationException("Server.stop() method should not be called directly");
    }

    /* (non-Javadoc)
     * @see org.apache.catalina.Lifecycle#destroy()
     */
    @Override
    public void destroy() throws LifecycleException {
        throw new UnsupportedOperationException("Server.destroy() method should not be called directly");
    }

    /* (non-Javadoc)
     * @see org.apache.catalina.Server#setGlobalNamingResources(org.apache.catalina.deploy.NamingResources)
     */
    @Override
    public void setGlobalNamingResources(NamingResourcesImpl globalNamingResources) {
        throw new UnsupportedOperationException("GlobalNamingResources can't be changed at runtime");
    }

    /* (non-Javadoc)
     * @see org.apache.catalina.Server#setPort(int)
     */
    @Override
    public void setPort(int port) {
        throw new UnsupportedOperationException("Server port can't be changed at runtime");
    }

    /* (non-Javadoc)
     * @see org.apache.catalina.Server#setAddress(java.lang.String)
     */
    @Override
    public void setAddress(String address) {
        throw new UnsupportedOperationException("Server address can't be changed at runtime");
    }


    @Override
    public void setParentClassLoader(ClassLoader parent) {
        throw new UnsupportedOperationException("ParentClassLoader can't be changed at runtime");
    }

    @Override
    public void setCatalina(Catalina catalina) {
        throw new UnsupportedOperationException("Catalina can't be changed at runtime");
    }

    @Override
    public void setCatalinaBase(File catalinaBase) {
        throw new UnsupportedOperationException("catalina.base can't be changed at runtime");
    }

    @Override
    public void setCatalinaHome(File catalinaHome) {
        throw new UnsupportedOperationException("catalina.home can't be changed at runtime");
    }

    @Override
    public void addService(Service service) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void await() {
        throw new UnsupportedOperationException();
    }


}
