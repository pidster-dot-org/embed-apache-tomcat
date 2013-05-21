package org.pidster.tomcat.embed.impl;

public class TomcatStartupException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TomcatStartupException(String message, Throwable cause) {
        super(message, cause);
    }

    public TomcatStartupException(String message) {
        super(message);
    }

    public TomcatStartupException(Throwable cause) {
        super(cause);
    }

}
