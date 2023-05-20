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
val otelInstVersion = "1.25.1";
val otelInstVersionAlpha = "1.25.1-alpha";

dependencies {
    api(platform("io.opentelemetry:opentelemetry-bom:$otelVersion"))
    api("io.opentelemetry.instrumentation:opentelemetry-instrumentation-api:$otelInstVersion")
    api("io.opentelemetry.instrumentation:opentelemetry-instrumentation-api-semconv:$otelInstVersionAlpha")
    api("io.opentelemetry:opentelemetry-api")
}