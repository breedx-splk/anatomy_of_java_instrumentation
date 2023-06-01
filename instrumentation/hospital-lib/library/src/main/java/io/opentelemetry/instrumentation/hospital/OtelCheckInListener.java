package io.opentelemetry.instrumentation.hospital;

import io.opentelemetry.context.Context;
import io.opentelemetry.instrumentation.api.instrumenter.Instrumenter;
import net.hospital.api.PatientListener;
import net.hospital.model.Patient;

class OtelCheckInListener implements PatientListener {

    private final Instrumenter<Patient, Void> instrumenter;

    OtelCheckInListener(Instrumenter<Patient, Void> instrumenter) {
        this.instrumenter = instrumenter;
    }

    @Override
    public void onCheckIn(Patient patient) {
        Context parentContext = Context.current();
        if (instrumenter.shouldStart(parentContext, patient)) {
            Context context = instrumenter.start(parentContext, patient);
            PatientVisitContextHolder.put(patient.name(), context);
        }
    }

    @Override
    public void onCheckOut(Patient patient) {
        Context context = PatientVisitContextHolder.remove(patient.name());
        instrumenter.end(context, patient, null, null);
    }
}
