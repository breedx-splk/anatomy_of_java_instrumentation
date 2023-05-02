package net.hospital.api;

import net.hospital.model.Doctor;
import net.hospital.model.Patient;

public interface Hospital {

    void doctorAvailable(Doctor doctor);
    void doctorUnavailable(Doctor doctor, String reason);

    void checkIn(Patient patient);

    boolean checkOut(Patient patient);
    void registerPatientListener(PatientListener listener);

}
