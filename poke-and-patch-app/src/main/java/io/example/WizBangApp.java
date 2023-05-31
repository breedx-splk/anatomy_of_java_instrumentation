package io.example;

import io.opentelemetry.instrumentation.hospital.HospitalTelemetry;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.autoconfigure.AutoConfiguredOpenTelemetrySdk;
import net.hospital.api.Hospital;
import net.hospital.impl.ExampleHospital;
import net.hospital.model.Doctor;
import net.hospital.model.Patient;

import java.io.IOException;
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
public class WizBangApp {
    private final Hospital hospital;

    public WizBangApp(Hospital hospital) {
        this.hospital = hospital;
    }

    public static void main(String[] args) throws Exception {
        Hospital hospital = ExampleHospital.create();
        new WizBangApp(hospital).run();
    }

    public void run() throws Exception {
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
        System.in.readNBytes(1000);
    }
}
