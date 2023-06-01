package io.opentelemetry.javaagent.instrumentation.hospital;

import com.google.auto.service.AutoService;
import io.opentelemetry.instrumentation.hospital.HospitalTelemetry;
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
                "io.opentelemetry.javaagent.shaded.instrumentation.hospital.HospitalVisitInstrumenter",
                "io.opentelemetry.javaagent.shaded.instrumentation.hospital.PatientAttributeExtractor"
        );
    }

    @Override
    public boolean isHelperClass(String className) {
        System.out.println("SHITBALL: " + className);
//        return className.startsWith("io.opentelemetry.javaagent.instrumentation.hospital");
        return className.startsWith(HospitalTelemetry.class.getPackageName());
    }

    @Override
    public List<TypeInstrumentation> typeInstrumentations() {
        return List.of(new HospitalLibInstrumentation());
    }
}
