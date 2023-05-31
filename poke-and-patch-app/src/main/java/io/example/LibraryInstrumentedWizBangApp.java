package io.example;

import io.opentelemetry.instrumentation.hospital.HospitalTelemetry;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.autoconfigure.AutoConfiguredOpenTelemetrySdk;
import net.hospital.api.Hospital;
import net.hospital.impl.ExampleHospital;

/**
 * Pretend this is a full-fledged UI
 */
public class LibraryInstrumentedWizBangApp {

    public static void main(String[] args) throws Exception {
        Hospital hospital = ExampleHospital.create();
        addInstrumentation(hospital);
        new WizBangApp(hospital).run();
    }

    private static void addInstrumentation(Hospital hospital) {
        AutoConfiguredOpenTelemetrySdk autoconfigureSdk = AutoConfiguredOpenTelemetrySdk.initialize();
        OpenTelemetrySdk otelSdk = autoconfigureSdk.getOpenTelemetrySdk();
        HospitalTelemetry hospitalTelemetry = HospitalTelemetry.create(otelSdk);
        hospitalTelemetry.observeHospital(hospital);
    }
}
