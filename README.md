# Embed Apache Tomcat

Just like the name says, this project provides a fluent API for embedding Apache Tomcat.

Embed a Tomcat instance for testing using the following code, where `MyContainerInitializer.class` is a ServletContainerInitializer implementation used to programmatically bootstrap a Servlet 3.0 application.

    Properties properties = new Properties();
    properties.put("catalina.base", "build/resources/test");

    Tomcat tomcat = new TomcatFactory(properties).create()
      .newMinimalServer(url.getPort())
          .createApplication("test-make-dirs")
          .makeDirs()
          .addServletContainerInitializer(MyContainerInitializer.class)
      .build();

Alternatively, add Servlet components individually:
        
    Tomcat tomcat = new TomcatFactory(properties).create()
      .newMinimalServer(url.getPort())
          .createApplication("test")
          .addServletContextListener(DummyListener.class)
          .addServletFilter(DummyFilter.class, "/*")
          .addServlet(DummyServlet.class, "/dummy")
      .build();

These minimal Tomcat instances are examples of minimal configurations, but the container can be configured entirely programmatically, specifying Valves, Connectors, LifecycleListeners etc as required.

## Testing usage

You can embed Tomcat in a unit test (e.g. JUnit) as follows:

    public class SomeTest {

        private TomcatRuntime runtime;

        @Before
        public void setupTomcat() throws Exception {

            Tomcat tomcat = new TomcatFactory().create()
                .newMinimalServer(8082)
                    .createApplication("test")
                    .addServlet(DummyServlet.class, "/dummy")
                .build();

            this.runtime = tomcat.start(5000L);  // wait for start
        }

        @Test
        public void testServlet() throws Exception {
        	// do some HTTP tests
        }

        @After
        public void teardown() {
            runtime.stop(5000L); // wait for stop
        }
    }


## Build configuration

Using Gradle and the current snapshot, the dependency is:

    repositories {
        maven { url 'https://repository.apache.org/content/groups/snapshots' }
    }

    dependencies {
		    compile "org.pidster:embed-apache-tomcat-core:0.2"
    }

