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
    implementation(project(":hospital-lib"))
    implementation("io.opentelemetry:opentelemetry-api:")
    implementation("io.opentelemetry:opentelemetry-sdk")
}