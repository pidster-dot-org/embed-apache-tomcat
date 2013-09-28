package org.pidster.tomcat.embed.junit;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.servlet.ServletContainerInitializer;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface TomcatServerConfig {

	int port() default 48080;

	long timeout() default 1000L;

	String baseDir() default "";

	String appName() default "";
	
	Class<? extends ServletContainerInitializer>[] value() default {};

	Class<? extends TomcatApplicationBuilderFactory> builderFactory() default SimpleTomcatApplicationBuilderFactory.class;

}
