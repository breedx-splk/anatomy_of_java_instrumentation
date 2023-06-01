package io.opentelemetry.javaagent.instrumentation.hospital;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.instrumentation.api.instrumenter.Instrumenter;
import io.opentelemetry.instrumentation.hospital.HospitalVisitInstrumenter;
import net.hospital.model.Patient;

public class HospitalSingletons {

    private final static Instrumenter<Patient,Void> INSTRUMENTER = HospitalVisitInstrumenter.create(GlobalOpenTelemetry.get());

    public static Instrumenter<Patient, Void> instrumenter() {
        return INSTRUMENTER;
    }
}
