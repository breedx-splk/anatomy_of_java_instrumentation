package io.opentelemetry.javaagent.instrumentation.hospital;

import io.opentelemetry.context.Context;
import io.opentelemetry.instrumentation.hospital.HospitalTelemetry;
import io.opentelemetry.instrumentation.hospital.PatientVisitContextHolder;
import io.opentelemetry.javaagent.extension.instrumentation.TypeInstrumentation;
import io.opentelemetry.javaagent.extension.instrumentation.TypeTransformer;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.hospital.api.Hospital;
import net.hospital.model.Ailment;
import net.hospital.model.Doctor;
import net.hospital.model.Patient;

import static io.opentelemetry.javaagent.instrumentation.hospital.HospitalSingletons.treatmentInstrumenter;
import static net.bytebuddy.matcher.ElementMatchers.isMethod;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.takesArguments;

public class HospitalInstrumentation implements TypeInstrumentation {

    @Override
    public ElementMatcher<TypeDescription> typeMatcher() {
        return named("net.hospital.impl.ExampleHospital");
    }

    @Override
    public void transform(TypeTransformer transformer) {
        transformer.applyAdviceToMethod(
                isMethod()
                .and(named("start")),
                HospitalInstrumentation.class.getName() + "$PatientVisitAdvice");
        transformer.applyAdviceToMethod(
                isMethod()
                .and(named("startTreatment"))
                .and(takesArguments(3)),
                HospitalInstrumentation.class.getName() + "$TreatmentStartingAdvice");
        transformer.applyAdviceToMethod(
                isMethod()
                .and(named("finishTreatment"))
                .and(takesArguments(3)),
                HospitalInstrumentation.class.getName() + "$TreatmentFinishingAdvice");
    }

    public static class PatientVisitAdvice {
        @Advice.OnMethodEnter(suppress = Throwable.class)
        public static void onStart(@Advice.This Hospital hospital) {
            HospitalTelemetry.create(HospitalSingletons.otel())
                    .observeHospital(hospital);
        }
    }

    public static class TreatmentStartingAdvice {

        @Advice.OnMethodEnter(suppress = Throwable.class)
        public static void onStartTreatment(
                @Advice.Argument(0) Patient patient,
                @Advice.Argument(1) Ailment ailment,
                @Advice.Argument(2) Doctor doctor) {

            Context parentContext = PatientVisitContextHolder.get(patient.name());
            Treatment treatment = new Treatment(patient, doctor, ailment);
            if (!treatmentInstrumenter().shouldStart(parentContext, treatment)) {
                return;
            }
            Context context = treatmentInstrumenter().start(parentContext, treatment);
            HospitalSingletons.putActive(patient.name(), context);
        }
    }
    public static class TreatmentFinishingAdvice {

        @Advice.OnMethodExit(suppress = Throwable.class, onThrowable = Throwable.class)
        public static void onTreatmentFinish(
                @Advice.Argument(0) Patient patient,
                @Advice.Argument(1) Doctor doctor,
                @Advice.Argument(2) Ailment ailment,
                @Advice.Thrown Throwable exception){

            System.out.println("xxx JEB");
            Context context = HospitalSingletons.popContext(patient.name());
            Treatment treatment = new Treatment(patient, doctor, ailment);
            treatmentInstrumenter().end(context, treatment, null, exception);
        }
    }
}
