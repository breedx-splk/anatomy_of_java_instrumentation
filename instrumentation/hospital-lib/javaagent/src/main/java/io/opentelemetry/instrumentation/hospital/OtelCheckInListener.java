package io.opentelemetry.instrumentation.hospital;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import net.hospital.api.PatientListener;
import net.hospital.model.Patient;

public class OtelCheckInListener implements PatientListener {

    @Override
    public void onCheckIn(Patient patient) {
        Context context = producerInstrumenter.start(parentContext, request);
        try (Scope ignored = context.makeCurrent()) {
            propagator().inject(context, record.headers(), SETTER);
            callback = new ProducerCallback(callback, parentContext, context, request);
            return sendFn.apply(record, callback);
        }
    }

    @Override
    public void onCheckOut(Patient patient) {
        Span currentSpan = Span.current();
        if (!currentSpan.getSpanContext().isValid()) {
            return;
        }

    }
}
