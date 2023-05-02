package net.hospital.api;

import net.hospital.model.Doctor;
import net.hospital.model.Patient;

import java.util.UUID;

public interface Hospital {

    void doctorAvailable(Doctor doctor);
    void doctorUnavailable(Doctor doctor);

    void checkIn(Patient patient);

    void checkOut(Patient patient);
    void registerPatientListener(PatientListener listener);

}
