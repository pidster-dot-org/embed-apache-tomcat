package org.pidster.tomcat.embed.junit;

import org.pidster.tomcat.embed.TomcatApplicationBuilder;

public interface TomcatApplicationBuilderFactory {

	TomcatApplicationBuilder getBuilder(TomcatServerConfig annotation);

}
