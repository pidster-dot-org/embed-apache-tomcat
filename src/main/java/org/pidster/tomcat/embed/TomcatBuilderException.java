package org.pidster.tomcat.embed;

public class TomcatBuilderException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TomcatBuilderException(String message, Throwable cause) {
        super(message, cause);
    }

    public TomcatBuilderException(String message) {
        super(message);
    }

    public TomcatBuilderException(Throwable cause) {
        super(cause);
    }

}
