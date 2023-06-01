package io.opentelemetry.javaagent.instrumentation.hospital;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.context.Context;
import io.opentelemetry.instrumentation.api.instrumenter.Instrumenter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HospitalSingletons {

    private final static Map<String, Context> activeContexts = new ConcurrentHashMap<>();
    private final static Instrumenter<Treatment, Void> treatmentInstrumenter =
            TreatmentInstrumenter.create(GlobalOpenTelemetry.get());

    public static OpenTelemetry otel(){
        return GlobalOpenTelemetry.get();
    }

    public static Instrumenter<Treatment,Void> treatmentInstrumenter(){
        return treatmentInstrumenter;
    }

    public static void putActive(String patientName, Context context){
        activeContexts.put(patientName, context);
    }

    public static Context popContext(String patientName){
        return activeContexts.remove(patientName);
    }
}
