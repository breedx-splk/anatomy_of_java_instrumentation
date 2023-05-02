package net.hospital.impl;

import net.hospital.model.Ailment;
import net.hospital.model.Doctor;

import java.util.Collection;

public class DoctorFinder {

    /**
     * Attempts to find a doctor to treat a given ailment.
     */
    public static Doctor find(Collection<Doctor> availableDoctors, Ailment ailment) {
        return availableDoctors.stream()
                .filter(doctor -> doctor.canTreat(ailment))
                .findFirst()
                .orElse(null);
    }
}
