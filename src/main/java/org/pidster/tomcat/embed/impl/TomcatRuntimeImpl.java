package org.pidster.tomcat.embed.impl;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.startup.Catalina;
import org.pidster.tomcat.embed.Tomcat;
import org.pidster.tomcat.embed.TomcatRuntime;

public class TomcatRuntimeImpl implements Tomcat, TomcatRuntime {

    private static final Logger log = Logger.getLogger(TomcatRuntime.class.getName());

    private final Catalina catalina;

    TomcatRuntimeImpl(Catalina catalina) {
        this.catalina = catalina;
    }

    @Override
    public TomcatRuntime start() {
        log.log(Level.INFO, "Starting Tomcat");

        try {
            catalina.start();
        } catch (Exception e) {
            throw new TomcatStartupException(e);
        }

        return this;
    }

    @Override
    public TomcatRuntime start(long timeout) {
        log.log(Level.INFO, "Starting Tomcat, will wait for {0}ms", timeout);

        final CountDownLatch latch = new CountDownLatch(1);
        catalina.getServer().addLifecycleListener(new LifecycleListener() {
            @Override
            public void lifecycleEvent(LifecycleEvent event) {
                if (Lifecycle.AFTER_START_EVENT.equals(event.getType())) {
                    latch.countDown();
                }
            }
        });

        try {
            catalina.start();

            latch.await(timeout, TimeUnit.MILLISECONDS);

        } catch (InterruptedException e) {
            log.log(Level.WARNING, "Interrupted during startup", e);
        } catch (Exception e) {
            throw new TomcatStartupException(e);
        }

        return this;
    }

    @Override
    public TomcatRuntime start(Runnable runnable) {
        log.log(Level.INFO, "Starting Tomcat with callback...");

        final CountDownLatch latch = new CountDownLatch(1);
        catalina.getServer().addLifecycleListener(new LifecycleListener() {
            @Override
            public void lifecycleEvent(LifecycleEvent event) {
                if (Lifecycle.AFTER_START_EVENT.equals(event.getType())) {
                    latch.countDown();
                }
            }
        });

        try {
            catalina.start();

            latch.await();

            runnable.run();

        } catch (InterruptedException e) {
            log.log(Level.WARNING, "Interrupted during startup", e);
        } catch (Exception e) {
            throw new TomcatStartupException(e);
        }

        return this;
    }

    @Override
    public TomcatRuntime deploy(String appName) {
        log.log(Level.INFO, "Deploying {0}", appName);
        throw new RuntimeException("Not implemented yet");
//        return this;
    }

    @Override
    public TomcatRuntime undeploy(String appName) {
        log.log(Level.INFO, "Undeploying {0}", appName);
        throw new RuntimeException("Not implemented yet");
//      return this;
    }

    @Override
    public void close() throws Exception {
        stop();
    }

    @Override
    public void stop() {
        log.log(Level.INFO, "Stopping Tomcat");
        catalina.stop();
    }

    @Override
    public void stop(long timeout) {
        log.log(Level.INFO, "Stopping Tomcat, will wait for {0}ms", timeout);

        final CountDownLatch latch = new CountDownLatch(1);
        catalina.getServer().addLifecycleListener(new LifecycleListener() {
            @Override
            public void lifecycleEvent(LifecycleEvent event) {
                if (Lifecycle.AFTER_STOP_EVENT.equals(event.getType())) {
                    latch.countDown();
                }
            }
        });

        try {
            catalina.stop();

            latch.await(timeout, TimeUnit.MILLISECONDS);

        } catch (Exception e) {
            log.log(Level.WARNING, "Interrupted during shutdown", e);
        }
    }

    @Override
    public void stopWhen(Thread waiting) {
        try {
            log.log(Level.INFO, "Stopping Tomcat when thread {0} completes", waiting.getId());

            waiting.join();

        } catch (InterruptedException e) {
            log.log(Level.WARNING, "Interrupted TomcatRuntime", e);
        }
        finally {
            catalina.stop();
        }
    }

}
