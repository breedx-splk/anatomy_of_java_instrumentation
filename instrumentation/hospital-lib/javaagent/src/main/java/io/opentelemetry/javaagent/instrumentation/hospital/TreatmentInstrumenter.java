package io.opentelemetry.javaagent.instrumentation.hospital;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.instrumentation.api.instrumenter.Instrumenter;

public class TreatmentInstrumenter {

    public static final String INSTRUMENTATION_NAME = "hospital-autoinstrumentation";

    public static Instrumenter<Treatment, Void> create(OpenTelemetry otel){
        TreatmentAttributeExtractor attributesExtractor = new TreatmentAttributeExtractor();
        return Instrumenter.<Treatment,Void>builder(otel, INSTRUMENTATION_NAME,
                        treatment -> treatment.patient().name() + " treated for " + treatment.ailment())
                .addAttributesExtractor(attributesExtractor)
                .buildInstrumenter();
    }

}
