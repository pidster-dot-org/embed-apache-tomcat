package org.pidster.tomcat.embed;

public interface Collector<T, C> {

    T collect(C child);

}
