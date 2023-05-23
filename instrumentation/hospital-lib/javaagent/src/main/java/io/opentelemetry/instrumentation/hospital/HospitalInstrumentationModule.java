package io.opentelemetry.instrumentation.hospital;

import com.google.auto.service.AutoService;
import io.opentelemetry.javaagent.extension.instrumentation.InstrumentationModule;
import io.opentelemetry.javaagent.extension.instrumentation.TypeInstrumentation;

import java.util.List;

@AutoService(InstrumentationModule.class)
public class HospitalInstrumentationModule extends InstrumentationModule {

    protected HospitalInstrumentationModule() {
        super("hospital-lib");
    }

    @Override
    public List<TypeInstrumentation> typeInstrumentations() {
        return List.of(new HospitalLibInstrumentation());
    }
}
