package io.opentelemetry.instrumentation.hospital;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.instrumentation.api.instrumenter.Instrumenter;
import net.hospital.api.Hospital;
import net.hospital.api.PatientListener;
import net.hospital.model.Patient;

public class HospitalTelemetry {

    public static final String INSTRUMENTATION_NAME = "hospital-lib";
    private final Instrumenter<Patient,Void> instrumenter;

    public static HospitalTelemetry create(OpenTelemetry openTelemetry){
        Instrumenter<Patient, Void> instrumenter = HospitalVisitInstrumenter.create(openTelemetry);
        return new HospitalTelemetry(instrumenter);
    }

    private HospitalTelemetry(Instrumenter<Patient, Void> instrumenter) {
        this.instrumenter = instrumenter;
    }

    public void observeHospital(Hospital hospital){
        PatientListener otelListener = new OtelCheckInListener(instrumenter);
        hospital.registerPatientListener(otelListener);
    }
}
