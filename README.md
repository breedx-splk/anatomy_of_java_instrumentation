# The Anatomy of Java Instrumentation

In this talk, we will perform a detailed dissection of the OpenTelemetry Java agent instrumentation
framework. We will explore the connective tissue that binds the agent to an application’s behavior and
learn how observable telemetry is created automatically. With this new understanding as our scalpel, 
we will slice into an example library to create a brand new instrumentation.

## Outline

See [outline.md] for talk outline.

## Repo structure

Here is a summary of the projects in this repo:

* hospital-lib - A general-purpose library for all things medical!
* surgeon-app - An example application 
* instrumentation