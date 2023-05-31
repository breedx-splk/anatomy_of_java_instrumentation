package io.opentelemetry.instrumentation.hospital;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.instrumentation.api.instrumenter.Instrumenter;
import net.hospital.model.Patient;

import static io.opentelemetry.instrumentation.hospital.HospitalTelemetry.INSTRUMENTATION_NAME;

class HospitalVisitInstrumenter {

    static Instrumenter<Patient,Void> create(OpenTelemetry otel){
        PatientAttributeExtractor attributesExtractor = new PatientAttributeExtractor();
        return Instrumenter.<Patient,Void>builder(otel, INSTRUMENTATION_NAME,
                        patient -> patient.name() + " visit")
                .addAttributesExtractor(attributesExtractor)
                .buildInstrumenter();
    }

    private HospitalVisitInstrumenter(){
    }
}
