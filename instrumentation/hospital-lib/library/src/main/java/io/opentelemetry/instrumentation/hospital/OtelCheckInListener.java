package io.opentelemetry.instrumentation.hospital;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import io.opentelemetry.instrumentation.api.instrumenter.Instrumenter;
import net.hospital.api.PatientListener;
import net.hospital.model.Patient;

import java.util.HashMap;
import java.util.Map;

class OtelCheckInListener implements PatientListener {

    private final Instrumenter<Patient, Void> instrumenter;
    private final Map<String, Context> patientContext = new HashMap<>();

    OtelCheckInListener(Instrumenter<Patient, Void> instrumenter) {
        this.instrumenter = instrumenter;
    }

    @Override
    public void onCheckIn(Patient patient) {
        Context parentContext = Context.current();
        if (instrumenter.shouldStart(parentContext, patient)) {
            Context context = instrumenter.start(parentContext, patient);
            patientContext.put(patient.name(), context);
        }
    }

    @Override
    public void onCheckOut(Patient patient) {
        Context context = patientContext.remove(patient.name());
        instrumenter.end(context, patient, null, null);
    }
}
