package org.pidster.tomcat.embed.impl;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.catalina.Container;
import org.apache.catalina.Context;
import org.apache.catalina.Engine;
import org.apache.catalina.Host;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.Server;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Catalina;
import org.pidster.tomcat.embed.Callback;
import org.pidster.tomcat.embed.Tomcat;
import org.pidster.tomcat.embed.TomcatRuntime;
import org.pidster.tomcat.embed.TomcatRuntimeException;
import org.pidster.tomcat.embed.TomcatStatus;

public class TomcatRuntimeImpl implements Tomcat, TomcatRuntime {

    private static final Logger log = Logger.getLogger(TomcatRuntime.class.getName());

    private final Catalina catalina;

    private volatile TomcatStatus status;

    private final Semaphore semaphore = new Semaphore(0);

    TomcatRuntimeImpl(Catalina catalina) {
        this.catalina = catalina;
        this.status = TomcatStatus.UNKNOWN;

        catalina.getServer().addLifecycleListener(new LifecycleListener() {
            @Override
            public void lifecycleEvent(LifecycleEvent event) {
                String type = event.getType().toUpperCase();
                status = TomcatStatus.valueOf(type);

                if (Lifecycle.AFTER_START_EVENT.equals(event.getType()) || Lifecycle.AFTER_STOP_EVENT.equals(event.getType())) {
                    semaphore.release();
                }
            }
        });

    }

    @Override
    public TomcatStatus status() {
        return status;
    }

    @Override
    public Server getServer() {
        return new SafeServerImpl(catalina.getServer());
    }

    @Override
    public TomcatRuntimeImpl start() {
        log.log(Level.CONFIG, "Starting Tomcat");

        try {
            long started = System.currentTimeMillis();

            catalina.start();
            semaphore.acquire();
            log.log(Level.INFO, "Started Tomcat in {0}ms", System.currentTimeMillis() - started);
            return this;

        } catch (Exception e) {
            throw new TomcatRuntimeException(e);
        }
    }

    @Override
    public TomcatRuntimeImpl start(long timeout) {
        log.log(Level.CONFIG, "Starting Tomcat, will wait for {0}ms", timeout);

        try {
            long started = System.currentTimeMillis();

            catalina.start();
            semaphore.tryAcquire(timeout, TimeUnit.MILLISECONDS);
            log.log(Level.INFO, "Started Tomcat in {0}ms", System.currentTimeMillis() - started);
            return this;

        } catch (Exception e) {
            throw new TomcatRuntimeException(e);
        }
    }

    @Override
    public void start(final Callback<TomcatRuntime> callback) {
        log.log(Level.CONFIG, "Starting Tomcat with callback...");
        new Thread("tomcat-embed-startup") {
            @Override
            public void run() {
                try {
                    long started = System.currentTimeMillis();

                    catalina.start();
                    semaphore.acquire();

                    callback.success(TomcatRuntimeImpl.this);
                    log.log(Level.INFO, "Started Tomcat in {0}ms", System.currentTimeMillis() - started);
                } catch (Exception e) {
                    callback.failure(e);
                }
            }
        }.start();
    }

    @Override
    public TomcatRuntime deploy(String appName) {
        log.log(Level.CONFIG, "Deploying {0}", appName);

        String serviceName = "Catalina";
        Container container = catalina.getServer().findService(serviceName).getContainer();
        Engine engine = (Engine) container;
        Host host = (Host) engine.findChild(engine.getDefaultHost());

        Context context = new StandardContext();
        host.addChild(context);

        throw new RuntimeException("Not implemented yet");
//        return this;
    }

    @Override
    public TomcatRuntime undeploy(String appName) {
        log.log(Level.CONFIG, "Undeploying {0}", appName);
        throw new RuntimeException("Not implemented yet");
//      return this;
    }

    @Override
    public void close() throws Exception {
        stop();
    }

    @Override
    public void stop() {
        log.log(Level.CONFIG, "Stopping Tomcat");

        try {
            catalina.stop();
            semaphore.acquire();
        } catch (Exception e) {
            log.log(Level.WARNING, "Interrupted during shutdown", e);
        }
    }

    @Override
    public void stop(long timeout) {
        log.log(Level.CONFIG, "Stopping Tomcat, will wait for {0}ms", timeout);

        try {
            catalina.stop();
            semaphore.tryAcquire(timeout, TimeUnit.MILLISECONDS);

        } catch (Exception e) {
            log.log(Level.WARNING, "Interrupted during shutdown", e);
        }
    }

    @Override
    public void stopOnCompletion(Thread waiting) {
        try {
            log.log(Level.CONFIG, "Stopping Tomcat when thread {0} completes", waiting.getId());
            waiting.join();

        } catch (InterruptedException e) {
            log.log(Level.WARNING, "Interrupted TomcatRuntime", e);
        }
        finally {
            stop();
        }
    }

}
