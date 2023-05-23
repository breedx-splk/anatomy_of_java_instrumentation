package io.example;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.instrumentation.hospital.HospitalTelemetry;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.autoconfigure.AutoConfiguredOpenTelemetrySdk;
import net.hospital.api.Hospital;
import net.hospital.api.PatientListener;
import net.hospital.impl.ExampleHospital;
import net.hospital.model.Doctor;
import net.hospital.model.Patient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static net.hospital.model.Ailment.BLEEDING;
import static net.hospital.model.Ailment.EAR_ACHE;
import static net.hospital.model.Ailment.FATIGUE;
import static net.hospital.model.Ailment.HEADACHE;
import static net.hospital.model.Ailment.INSOMNIA;
import static net.hospital.model.Ailment.POISONING;
import static net.hospital.model.Ailment.VIRAL_INFECTION;

/**
 * Pretend this is a full-fledged UI
 */
public class WizBangApp implements PatientListener {

    private final Hospital hospital;
    private final BufferedReader in;

    public WizBangApp(Hospital hospital) {
        this.hospital = hospital;
        in = new BufferedReader(new InputStreamReader(System.in));

    }

    public static void main(String[] args) throws Exception {
        Hospital hospital = ExampleHospital.create();
        addInstrumentation(hospital);
        Patient p1 = new Patient("Jeff", "Smith", List.of(BLEEDING, INSOMNIA));
        Patient p2 = new Patient("Jessica", "Andou", List.of(EAR_ACHE, FATIGUE));
        Patient p3 = new Patient("Robert", "Dobbs", List.of(HEADACHE));
        Doctor d1 = new Doctor("P.", "Drummond", List.of(BLEEDING, INSOMNIA, EAR_ACHE, POISONING));
        Doctor d2 = new Doctor("Gina", "Craftson", List.of(BLEEDING, INSOMNIA, HEADACHE));
        Doctor d3 = new Doctor("Matt", "Clarke", List.of(BLEEDING, FATIGUE, VIRAL_INFECTION));
        hospital.checkIn(p1);
        hospital.checkIn(p2);
        hospital.checkIn(p3);
        hospital.doctorAvailable(d1);
        hospital.doctorAvailable(d2);
        hospital.doctorAvailable(d3);
        new WizBangApp(hospital).run();
    }

    private static void addInstrumentation(Hospital hospital) {
        AutoConfiguredOpenTelemetrySdk autoconfigureSdk = AutoConfiguredOpenTelemetrySdk.initialize();
//        OpenTelemetrySdk otel = OpenTelemetrySdk.builder().buildAndRegisterGlobal();
        OpenTelemetrySdk otelSdk = autoconfigureSdk.getOpenTelemetrySdk();
        HospitalTelemetry hospitalTelemetry = HospitalTelemetry.create(otelSdk);
        hospitalTelemetry.observeHospital(hospital);
    }

    private void run() throws IOException {
        hospital.registerPatientListener(this);
        while (true) {
//            mainMenu();
            String entry = read();
            switch (entry) {
                case "1":
                    Patient patient = new PatientReader(in).readPatient();
                    hospital.checkIn(patient);
                    break;
                case "2":
                    Doctor doc = new DoctorReader(in).readDoctor();
                    hospital.doctorAvailable(doc);
            }
        }
    }


    String read() throws IOException {
        return in.readLine();
    }

    private void mainMenu() {
        System.out.println("------------------------------");
        System.out.println("Welcome to the Poke and Patch");
        System.out.println("------------------------------");
        System.out.println("1. Register new patient");
        System.out.println("2. Register a doctor");
        System.out.println();
        System.out.print("> ");
    }

}
