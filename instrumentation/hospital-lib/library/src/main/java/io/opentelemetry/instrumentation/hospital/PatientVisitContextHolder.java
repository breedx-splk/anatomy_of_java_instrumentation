package io.opentelemetry.instrumentation.hospital;

import io.opentelemetry.context.Context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PatientVisitContextHolder {

    private final static Map<String, Context> patientContext = new ConcurrentHashMap<>();

    public static void put(String name, Context context) {
        patientContext.put(name, context);
    }

    public static Context remove(String name) {
        return patientContext.remove(name);
    }

    public static Context get(String name){
        return patientContext.get(name);
    }
}
