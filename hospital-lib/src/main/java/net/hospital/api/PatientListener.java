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
     * Called when a patient receives treatment.
     */
    default void onTreatment(Doctor doctor, Patient patient, Ailment ailment, String notes){}

    /**
     * Called when a patient checks in to a Hospital
     */
    default void onCheckIn(Patient patient){}

    /**
     * Called when a patient checks out of a Hospital
     */
    default void onCheckOut(Patient patient){}

    // These are examples that _shuold_ exist in the API to help facilitate
    // observability. They are excluded here but left as a reference for how
    // things could/should be improved.
    /**
     * Called when a Doctor first sees a Patient
     */
//    default void onStartDoctorVisit(Patient patient, Doctor doctor){}

    /**
     * Called when a doctor finishes seeing a Patient
     */
//    default void onEndDoctorVisit(Patient patient, Doctor doctor){}
}
