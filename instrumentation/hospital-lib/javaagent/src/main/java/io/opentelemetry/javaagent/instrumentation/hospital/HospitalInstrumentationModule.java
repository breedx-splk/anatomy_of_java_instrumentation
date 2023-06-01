package io.opentelemetry.javaagent.instrumentation.hospital;

import com.google.auto.service.AutoService;
import io.opentelemetry.javaagent.extension.instrumentation.InstrumentationModule;
import io.opentelemetry.javaagent.extension.instrumentation.TypeInstrumentation;

import java.util.List;

@AutoService(InstrumentationModule.class)
public class HospitalInstrumentationModule extends InstrumentationModule {

    public HospitalInstrumentationModule() {
        super("hospital-lib");
    }

    @Override
    public List<String> getAdditionalHelperClassNames() {
        return List.of(HospitalSingletons.class.getName(),
                Treatment.class.getName(),
                TreatmentAttributeExtractor.class.getName(),
                TreatmentInstrumenter.class.getName(),
                "io.opentelemetry.javaagent.shaded.instrumentation.hospital.HospitalVisitInstrumenter",
                "io.opentelemetry.javaagent.shaded.instrumentation.hospital.PatientAttributeExtractor",
                "io.opentelemetry.javaagent.shaded.instrumentation.hospital.HospitalTelemetry",
                "io.opentelemetry.javaagent.shaded.instrumentation.hospital.PatientVisitContextHolder",
                "io.opentelemetry.javaagent.shaded.instrumentation.hospital.OtelCheckInListener"
        );
    }

    @Override
    public List<TypeInstrumentation> typeInstrumentations() {
        return List.of(new HospitalInstrumentation());
    }
}
