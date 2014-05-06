Embedding jBPM 6 into a Java EE application
====
Official documentation : http://docs.jboss.org/jbpm/v6.0.1/userguide/
<br/>
Used http://docs.jboss.org/jbpm/v6.0.1/userguide/pt05.html to do this template
<br/>
About :
- jBPM 6.0.1.Final : workflow engine
- Java EE 6.0 environment
- TomEE 1.6.0.1 / JBoss AS 7.1.1
- JPA 2.0 (jBPM dependency for persistence)
- CDI 1.0 (Container environment for jBPM integration with CDI)
- JTA 1.1 (Container environment for jBPM transactions)
- HSQLDB database (embedded in container)
- Hibernate 4.2 (jBPM dependency), not found yet how to use any JPA implementation

Build & Deployment & Run :
- This application is a webapp. Build it with Maven.
- Declaring datasource in TomEE : replace $TOMEE_HOME/conf/tomee.xml by jbpm6/container/tomee.xml provided in sources. 
- Declaring datasource in JBoss AS : add datasource in $JBOSS_HOME/standalone/configuration/standalone.xml from jbpm6/container/standalone.xml 
- @see Files to edit to change application server
- mvn clean install
- Drop war in container
- Start container
- Go to http://localhost:8080/jbpm6 and follow the instructions

Files to edit to change application server :
- pom.xml (hibernate & dom4j dependencies)
- persistence.xml (hibernate dialect)

/!\ Important  notes /!\
- jBPM editor is not enough to create BPMN 2.0 processes (Does not provide all BPMN2 activities). Use http://www.eclipse.org/bpmn2-modeler/
- Look in the code of Handler (e.g. ServiceTaskHandler, ReceiveTaskHandler) for modelisation or develop your own handlers
- Knowledge session execute command is synchronized
- Custom UserGroupCallback. Validates user and groups and is declared in RuntimeEnvironment.
- Custom deployment service to inject into FormProvider. Copy paste of KModuleDeploymentService without @Kjar annotation. This custom service solves injection problem with CDI
- One RuntimeManager (per strategy) can live in the entire application. If RuntimeManager is injected multiples times (and so produced multiple times), jBPM will complain and will throw an exception.
- Use ProcessManagerBean (custom singleton EJB) and only this class to manage runtime and processes. Implement all calls to jBPM API here (for transactions).
- This example does not check if jBPM processes restart if server restarts

Useful links :
- http://www.mastertheboss.com/jbpm6/introduction-to-jbpm-6
- http://www.mastertheboss.com/jbpm6/jbpm-6-web-application-example
- https://github.com/jsvitak/jbpm-6-examples/
- http://mswiderski.blogspot.ch/