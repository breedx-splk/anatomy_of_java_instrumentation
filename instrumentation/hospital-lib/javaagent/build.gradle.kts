plugins {
    java
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks {
    shadowJar {
        archiveBaseName.set("hospital-autoinstrumentation")
        relocate("io.opentelemetry.instrumentation", "io.opentelemetry.javaagent.shaded.instrumentation")
        dependencies {
            include(project(":instrumentation:hospital-lib:library"))
        }
    }
}

val otelVersion = "1.26.0";
val otelInstVersion = "1.26.0";
val otelInstVersionAlpha = "1.26.0-alpha";

dependencies {
    compileOnly(platform("io.opentelemetry:opentelemetry-bom:$otelVersion"))

    compileOnly("com.google.auto.service:auto-service-annotations:1.0.1")
    annotationProcessor("com.google.auto.service:auto-service:1.0.1")

    compileOnly(project(":hospital-lib"))
    implementation(project(":instrumentation:hospital-lib:library"))
    compileOnly("io.opentelemetry.instrumentation:opentelemetry-instrumentation-api:$otelInstVersion")
//    api("io.opentelemetry.instrumentation:javaagent-extension-api:$otelInstVersion")
    compileOnly("io.opentelemetry.javaagent:opentelemetry-javaagent-extension-api:${otelInstVersionAlpha}")
    compileOnly("io.opentelemetry.instrumentation:opentelemetry-instrumentation-api-semconv:$otelInstVersionAlpha")
    compileOnly("io.opentelemetry:opentelemetry-api")

}