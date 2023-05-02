package net.hospital.api;

import net.hospital.model.Ailment;
import net.hospital.model.Doctor;
import net.hospital.model.Patient;

/**
 * A callback interface for listening to various Patient
 * related processes.
 */
public interface PatientListener {

    /**
     * Called every time a new diagnosis is made.
     */
    default void onDiagnosis(Doctor doctor, Patient patient, Ailment ailment){}

    /**
     * Called when a patient checks in to a Hospital
     */
    default void onCheckIn(Patient patient){}

    /**
     * Called when a patient checks out of a Hospital
     */
    default void onCheckOut(Patient patient){}
}
