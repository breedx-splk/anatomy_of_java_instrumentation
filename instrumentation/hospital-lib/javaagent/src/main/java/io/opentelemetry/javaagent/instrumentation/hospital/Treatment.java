package io.opentelemetry.javaagent.instrumentation.hospital;

import net.hospital.model.Ailment;
import net.hospital.model.Doctor;
import net.hospital.model.Patient;

public record Treatment(Patient patient, Doctor doctor, Ailment ailment) {

}
