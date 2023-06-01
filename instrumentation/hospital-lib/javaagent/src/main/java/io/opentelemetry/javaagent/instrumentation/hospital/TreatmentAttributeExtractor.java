package io.opentelemetry.javaagent.instrumentation.hospital;

import io.opentelemetry.api.common.AttributesBuilder;
import io.opentelemetry.context.Context;
import io.opentelemetry.instrumentation.api.instrumenter.AttributesExtractor;

public class TreatmentAttributeExtractor implements AttributesExtractor<Treatment, Void> {

    @Override
    public void onStart(AttributesBuilder attributes, Context parentContext, Treatment treatment) {
        attributes.put("patient", treatment.patient().name());
        attributes.put("ailment", treatment.ailment().name());
        attributes.put("doctor", treatment.doctor().name());
    }

    @Override
    public void onEnd(AttributesBuilder attributes, Context context, Treatment treatment, Void unused, Throwable error) {

    }
}
