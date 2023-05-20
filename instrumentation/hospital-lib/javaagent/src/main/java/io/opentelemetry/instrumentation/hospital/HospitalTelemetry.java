package io.opentelemetry.instrumentation.hospital;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.instrumentation.api.instrumenter.Instrumenter;
import io.opentelemetry.instrumentation.api.instrumenter.InstrumenterBuilder;
import io.opentelemetry.instrumentation.api.instrumenter.SpanNameExtractor;
import net.hospital.model.Patient;

public class HospitalTelemetry {

    private static final String INSTRUMENTATION_NAME = "hospital-lib";
    private final Instrumenter<Patient,Void> instrumenter;

//    public static HospitalTelemetry create(OpenTelemetry openTelemetry){
//        Instrumenter<Patient, Void> instrumenter =
//            Instrumenter.builder(openTelemetry, INSTRUMENTATION_NAME, new SpanNameExtractor<Patient>() {
//                @Override
//                public String extract(Patient patient) {
//                    return patient.name() + " visit";
//                }
//            })
//            .addAttributesExtractor(xxx)
//            .buildInstrumenter();
//
//        return new HospitalTelemetry(instrumenter);
//    }

    private HospitalTelemetry(Instrumenter<Patient, Void> instrumenter) {
        this.instrumenter = instrumenter;
    }
}
