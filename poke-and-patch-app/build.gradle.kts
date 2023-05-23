plugins {
    application
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

dependencies {
    implementation(platform("io.opentelemetry:opentelemetry-bom:$otelVersion"))
    implementation("io.opentelemetry:opentelemetry-sdk-extension-autoconfigure:$otelVersion-alpha")
    implementation("io.opentelemetry:opentelemetry-exporter-otlp:$otelVersion")
    implementation(project(":hospital-lib"))
    implementation(project(":instrumentation:hospital-lib:library"))
    implementation("io.opentelemetry:opentelemetry-api:")
    implementation("io.opentelemetry:opentelemetry-sdk")
}