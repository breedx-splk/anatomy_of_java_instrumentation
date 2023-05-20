rootProject.name = "anatomy-of-java-instrumentation"

include("hospital-lib")
include("poke-and-patch-app")
include(":instrumentation:hospital-lib:library")
include(":instrumentation:hospital-lib:javaagent")