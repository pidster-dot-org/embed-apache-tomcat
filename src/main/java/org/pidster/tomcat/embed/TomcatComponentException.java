package org.pidster.tomcat.embed;

public class TomcatComponentException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TomcatComponentException(String message, Throwable cause) {
        super(message, cause);
    }

    public TomcatComponentException(String message) {
        super(message);
    }

    public TomcatComponentException(Throwable cause) {
        super(cause);
    }

}
