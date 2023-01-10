# The Anatomy of Java Instrumentation

In this talk, we will perform a detailed dissection of the OpenTelemetry Java agent’s instrumentation
framework. We will explore the connective tissue that binds the agent to an application’s behavior and
learn how observable telemetry is created automatically.

## Approach

* 5 minutes of background + terminology
* 5 minutes gross anatomy (javaagent and bytecode weaving)
* 10 minutes of api and building instrumentation 
* 5 minutes of library
* 5 minutes of library instrumentation
* 5 minutes of agent instrumentation
* 5 minutes wiring extension
* 5 minute demo

## Outline

* software telemetry signals?
   * traces - record of a call tree
      * often distributed across network services
      * service-to-service calls with duration and metadata
   * metrics - point in time numerical measurements
      * sum (counter), gauge, histogram
   * logs 
      * usually human-readable text, sometimes structured, sometimes in (trace) context
* brief background context about (javaagent) instrumentation
    * add one commandline arg
        * out pops telemetry
            * show traces, show metrics
        * rich visibility with little user effort!
        * BUT HOW? (teaser)
    * vendors did this - wily, new relic (invented APM)
        * propietary / closed-source "secret sauce"
    * other vendors followed - datadog, appdynamics, dynatrace
    * user overwhelm and vendor lock-in
* going open source
    * motivation includes:
        * stop duplicating work
        * commoditize instrumentation
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
* let's talk `ClassFileTransformer`
    * it’s not magic!
        * but maybe it’s hacky
    * class file looks (like this)
    * transformed could contain additional code (like this)
    * handling class bytes is nontrival
        * `bytes[]` -> `bytes[]`
    * libraries exist to help us: asm, bytebuddy
* so here is the `opentelemetry-java-instrumentation` agent
    * look at all these instrumented libraries!
    * how do they work?
    * what if my favorite library is missing and I want to contribute a new one?
* how does the agent load these instrumentations?
    * `InstrumentationLoader` 
        * leverages `@AutoService` to load all `InstrumentationModule`
        * passes each to `InstrumentationModuleInstaller`
            * accumulates all into bytebuddy `AgentBuilder`
        * eventually `agentBuilder.installOn(instrumentation)`
* otel instrumentation api
    * library instrumentation vs. agent instrumentation
    * `InstrumentationModule`
        * a logical grouping for related instrumentations
        * responsible for supplying relevant `TypeInstrumentation`s
        * hundreds and hundreds of TypeInstrumentations
* matching - class/method 
    * byte buddy
        * agentbuilder
    * target the advice!
* an example library to instrument
    * 
* compare byte code pre/post instrumentation
* agent extension
