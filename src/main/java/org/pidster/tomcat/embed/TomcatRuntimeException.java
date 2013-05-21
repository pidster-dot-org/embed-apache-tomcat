package org.pidster.tomcat.embed;

public class TomcatRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TomcatRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public TomcatRuntimeException(String message) {
        super(message);
    }

    public TomcatRuntimeException(Throwable cause) {
        super(cause);
    }

}
