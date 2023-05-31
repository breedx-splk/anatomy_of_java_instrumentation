package io.opentelemetry.instrumentation.hospital;

import io.opentelemetry.api.common.AttributesBuilder;
import io.opentelemetry.context.Context;
import io.opentelemetry.instrumentation.api.instrumenter.AttributesExtractor;
import net.hospital.model.Patient;

public class PatientAttributeExtractor implements AttributesExtractor<Patient,Void> {
    @Override
    public void onStart(AttributesBuilder attributes, Context parentContext, Patient patient) {
        attributes.put("firstName", patient.firstName());
        attributes.put("lastName", patient.lastName());
        attributes.put("initialAilmentCount", patient.ailments().size());
    }

    @Override
    public void onEnd(AttributesBuilder attributes, Context context, Patient patient, Void unused, Throwable error) {
        //noop
    }
}
