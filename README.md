# The Anatomy of Java Instrumentation

In this talk, we will perform a detailed dissection of the OpenTelemetry Java agent’s instrumentation
framework. We will explore the connective tissue that binds the agent to an application’s behavior and
learn how observable telemetry is created automatically.

* software telemetry signals?
   * traces - record of a call tree
      * often distributed across network services
      * service-to-service calls with duration and metadata
   * metrics - point in time numerical measurements
      * sum (counter), gauge, histogram
   * logs 
      * usually text, sometimes structured, sometimes in (trace) context
* brief background context about (javaagent) instrumentation
    * add one commandline arg
        * out pops telemetry
            * show traces, show metrics
        * rich visibility with little user effort!
        * BUT HOW? (teaser)
    * vendors did this - wily, new relic (invented APM)
    * other vendors followed - datadog, appdynamics, dynatrace
    * user overwhelm and vendor lock-in
* going open source
    * opencensus and opentracing
        * combine forces become opentelemetry
    * provide oss governance and a specification
    * language specific apis, sdks, instrumentations (agent)
        * java is a primary one
* what is a -javaagent?
    * how many know what it is?
    * how many have written one?
    * public static void premain(String agentArgs, Instrumentation inst) 
        * called before application entrypoint
        * Instrumentation.addTransformer(...)
        * Instrumentation.redefineClasses(...)
        * Instrumentation.retransformClasses()
* it’s not magic!
    * but maybe it’s hacky
* so here is opentelemetry-java-instrumentation agent
    * look at all these instrumented libraries!
    * how do they work?
    * what if my favorite library is missing and I want to make a new one?
* how does it get its hooks into classloading?
* otel instrumentation api
    * library instrumentation vs. agent instrumentation
    * InstrumentationModule
* matching - class/method 
    * byte buddy
        * agentbuilder
    * target the advice!
* an example library to instrument
    * 
* compare byte code pre/post instrumentation
* agent extension
