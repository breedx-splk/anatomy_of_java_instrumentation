package io.opentelemetry.instrumentation.hospital;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.instrumentation.api.instrumenter.Instrumenter;
import net.hospital.model.Patient;

public class HospitalSingletons {

    private final static Instrumenter<Patient,Void> INSTRUMENTER = HospitalVisitInstrumenter.create(GlobalOpenTelemetry.get());

    static Instrumenter<Patient,Void> instrumenter(){
        return INSTRUMENTER;
    }
}
