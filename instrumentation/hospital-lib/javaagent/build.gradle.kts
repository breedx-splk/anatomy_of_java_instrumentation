plugins {
    `java-library`
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

val otelVersion = "1.26.0";
val otelInstVersion = "1.26.0";
val otelInstVersionAlpha = "1.26.0-alpha";

dependencies {
    api(platform("io.opentelemetry:opentelemetry-bom:$otelVersion"))
    annotationProcessor("com.google.auto.service:auto-service:1.0.1")
    compileOnly("com.google.auto.service:auto-service-annotations:1.0.1")
    api(project(":hospital-lib"))
    api(project(":instrumentation:hospital-lib:library"))
    api("io.opentelemetry.instrumentation:opentelemetry-instrumentation-api:$otelInstVersion")
//    api("io.opentelemetry.instrumentation:javaagent-extension-api:$otelInstVersion")
    api("io.opentelemetry.javaagent:opentelemetry-javaagent-extension-api:${otelInstVersionAlpha}")
    api("io.opentelemetry.instrumentation:opentelemetry-instrumentation-api-semconv:$otelInstVersionAlpha")
    api("io.opentelemetry:opentelemetry-api")

}