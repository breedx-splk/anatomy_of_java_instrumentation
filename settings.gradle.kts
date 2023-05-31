rootProject.name = "anatomy-of-java-instrumentation"

include("hospital-lib")
include(":instrumentation:hospital-lib:library")
include(":instrumentation:hospital-lib:javaagent")
include("poke-and-patch-app")
