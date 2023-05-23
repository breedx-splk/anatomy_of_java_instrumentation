package io.opentelemetry.instrumentation.hospital;

import io.opentelemetry.javaagent.extension.instrumentation.TypeInstrumentation;
import io.opentelemetry.javaagent.extension.instrumentation.TypeTransformer;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

import static io.opentelemetry.javaagent.extension.matcher.AgentElementMatchers.extendsClass;
import static net.bytebuddy.matcher.ElementMatchers.isMethod;
import static net.bytebuddy.matcher.ElementMatchers.nameStartsWith;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.not;
import static net.bytebuddy.matcher.ElementMatchers.takesArguments;

public class HospitalLibInstrumentation implements TypeInstrumentation {

    @Override
    public ElementMatcher<TypeDescription> typeMatcher() {
        return nameStartsWith("java.net.")
                .or(nameStartsWith("jdk.internal."))
                .and(not(named("jdk.internal.net.http.HttpClientFacade")))
                .and(extendsClass(named("java.net.http.HttpClient")));
    }

    @Override
    public void transform(TypeTransformer transformer) {
        transformer.applyAdviceToMethod(
                isMethod().and(named("start")).and(takesArguments(0)),
                HospitalInstrumentationModule.class.getName() + "$SomethingAdvice");
    }

    public static class SomethingAdvice {

    }
}
