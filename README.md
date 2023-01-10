# The Anatomy of Java Instrumentation

In this talk, we will perform a detailed dissection of the OpenTelemetry Java agent instrumentation
framework. We will explore the connective tissue that binds the agent to an application’s behavior and
learn how observable telemetry is created automatically. With this new understanding as our scalpel, 
we will slice into an example library to create a brand new instrumentation.

## Approach (preliminary)

* 5 minutes of background context + terminology
* 5 minutes gross anatomy (javaagent and bytecode weaving)
* 10 minutes of instrumentation api and wiring diagram
* 5 minute tour of example library + app
* 5 minutes of building library instrumentation
* 5 minutes of building agent instrumentation
* 5 minutes wiring an agent extension
* 5 minute demo

## Outline

* software telemetry signals?
   * traces - record of a call tree
      * often distributed across network services
      * service-to-service calls with duration and metadata
   * metrics - point in time numerical measurements
      * counter, gauge, histogram
   * logs 
      * usually human-readable text, sometimes structured, sometimes in (trace) context
* brief background context about (javaagent) instrumentation
    * users add just one commandline arg
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
    * `TypeInstrumentation`
        * responsible for specifying what to transform
        * what class(es) to transform
        * what methods should get "Advice" classes applied
            * see below
        * classLoaderOptimization - (shortcut) "when _can_ this instrumentation apply"
    * advice classes
        * place instrumentation code into target bytecode
        * `@Advice.OnMethodEnter/Exit` annotations
        * (similar to AOP join points)
* an example toy library to instrument
    * prime factors (or similar)
    * here's what it looks like as a hostile implementation
        * no observability hooks in place
        * would normally have to target implementation itself
    * here's what it looks like improved
        * hooks to observe the library behavior (observer pattern?)
            * (if you write a library, please do this!)
        * note: just pure API, no implementation (yet)
* an example application that uses our library
    * show code
    * curl a quick demo
        * look at how simple/boring our traces are!
    * we want additional visibility, especially around this lib
* let's build library instrumentation
    * register our lib instrumentation with library code hooks
    * implement opentelemetry calls
        * create, start, end span
    * (we could just use this in our app and call it done)
        * requires us to manage otel instance
        * we like the agent
* let's build javaagent instrumentation
    * create the `InstrumentationModule` impl
    * create our single `TypeInstrumentation`
* time to close up the patient!
    * build this as an agent *extension jar*
    * modify our startup to use the extension
    * run the app, hit the endpoint
    * look at our new trace!
* looking at a speciment in the microscope:
    * compare byte code pre/post instrumentation
        * decompiled original library `.class` file
        * dumped class bytes after instrumentation
        * see, there's our instrumentation code...



## Resources 

* [writing-instrumentation](https://github.com/open-telemetry/opentelemetry-java-instrumentation/blob/main/docs/contributing/writing-instrumentation.md)
* [writing instrumentation module](https://github.com/open-telemetry/opentelemetry-java-instrumentation/blob/main/docs/contributing/writing-instrumentation-module.md)
