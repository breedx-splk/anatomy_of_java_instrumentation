package io.opentelemetry.javaagent.instrumentation.hospital;

import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import io.opentelemetry.javaagent.bootstrap.Java8BytecodeBridge;
import io.opentelemetry.javaagent.extension.instrumentation.TypeInstrumentation;
import io.opentelemetry.javaagent.extension.instrumentation.TypeTransformer;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.hospital.model.Ailment;
import net.hospital.model.Doctor;
import net.hospital.model.Patient;

import static io.opentelemetry.javaagent.instrumentation.hospital.HospitalSingletons.instrumenter;
import static net.bytebuddy.matcher.ElementMatchers.isMethod;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.takesArguments;

public class HospitalLibInstrumentation implements TypeInstrumentation {

    @Override
    public ElementMatcher<TypeDescription> typeMatcher() {
        return named("net.hospital.impl.ExampleHospital");
    }

    @Override
    public void transform(TypeTransformer transformer) {
        transformer.applyAdviceToMethod(
                isMethod()
                .and(named("startTreatment"))
                .and(takesArguments(3)),
                HospitalLibInstrumentation.class.getName() + "$TreatmentStartingAdvice");
        transformer.applyAdviceToMethod(
                isMethod()
                .and(named("finishTreatment"))
                .and(takesArguments(2)),
                HospitalLibInstrumentation.class.getName() + "$TreatmentFinishingAdvice");
    }

    public static class TreatmentStartingAdvice {

        @Advice.OnMethodEnter(suppress = Throwable.class)
        public static void onStartTreatment(
                @Advice.Argument(0) Patient patient,
                @Advice.Argument(1) Ailment ailment,
                @Advice.Argument(2) Doctor doctor,
                @Advice.Local("otelContext") Context context,
                @Advice.Local("otelScope") Scope scope) {
            Context parentContext = Java8BytecodeBridge.currentContext();
            System.out.println("MOTHER");
            System.out.println("MOTHER");
            System.out.println("MOTHER");
            System.out.println("MOTHER");
            System.out.println("MOTHER");
            System.out.println("MOTHER");

            if (!instrumenter().shouldStart(parentContext, patient)) {
                return;
            }
            System.out.println("DAD");
            System.out.println("DAD");
            System.out.println("DAD");
            System.out.println("DAD");
            System.out.println("DAD");
            System.out.println("DAD");
            System.out.println("DAD");
            System.out.println("DAD");
            System.out.println("DAD");
            context = instrumenter().start(parentContext, patient);
            scope = context.makeCurrent();
        }
    }
    public static class TreatmentFinishingAdvice {

        @Advice.OnMethodExit(suppress = Throwable.class, onThrowable = Throwable.class)
        public static void onTreatmentFinish(
                @Advice.Argument(0) Patient patient,
                @Advice.Argument(1) Doctor doctor,
                @Advice.Thrown Throwable exception,
                @Advice.Local("otelContext") Context context,
                @Advice.Local("otelScope") Scope scope){

            if (scope == null) {
                return;
            }
            scope.close();
            instrumenter().end(context, patient, null, exception);
        }
    }
}
