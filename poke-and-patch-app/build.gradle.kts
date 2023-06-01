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

application {
    mainClass.set("io.example.LibraryInstrumentedWizBangApp")
    applicationDefaultJvmArgs = listOf(
        "-Dotel.service.name=PatchAndPoke"
    )
}

task("runWithAgent", JavaExec::class) {
    mainClass.set("io.example.WizBangApp")
    classpath = sourceSets["main"].runtimeClasspath
    jvmArgs = listOf(
        "-javaagent:../opentelemetry-javaagent.jar",
        "-Dotel.javaagent.extensions=../instrumentation/hospital-lib/javaagent/build/libs/hospital-autoinstrumentation-all.jar",
        "-Dotel.service.name=PatchAndPoke",
//        "-Dotel.javaagent.debug=true"
    )
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
    runtimeOnly(project(":instrumentation:hospital-lib:javaagent"))
}