# The Anatomy of Java Instrumentation

In this talk, we will perform a detailed dissection of the OpenTelemetry Java agent’s instrumentation framework. We will explore the connective tissue that binds the agent to an application’s behavior and learn how observable tracing telemetry is created.


* brief background about agent instrumentation
* what is a -javaagent?
* brief background about otel/tracing
* so here is opentelemetry-java-agent
    * look at all these instrumented libraries!
    * how do they work?
    * what if my favorite library is missing and I want to make a new one?
* .
* it’s not magic!
    * but maybe it’s hacky
* how does it get its hooks into classloading?
* instrumentation api / instrumentation module
* matching - class/method 
    * byte buddy
        * agentbuilder
    * target the advice!
* an example library to instrument
    * 
* compare byte code pre/post instrumentation
* agent extension
