package net.hospital.impl;

import net.hospital.api.Hospital;
import net.hospital.api.PatientListener;
import net.hospital.model.Ailment;
import net.hospital.model.Doctor;
import net.hospital.model.Patient;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This serves as a very basic reference implementation of the API
 * offered in the Hospital interface.
 */
public final class ExampleHospital implements Hospital {

    private final Queue<Doctor> doctors = new ConcurrentLinkedQueue<>();
    private final Queue<Patient> waitingRoom = new ConcurrentLinkedQueue<>();
    private final Map<Patient, Doctor> activeVisits = new ConcurrentHashMap<>();
    private final List<PatientListener> listeners = new CopyOnWriteArrayList<>();
    private final ScheduledExecutorService loop = Executors.newSingleThreadScheduledExecutor();

    public static Hospital create() {
        ExampleHospital hospital = new ExampleHospital();
        hospital.start();
        return hospital;
    }

    private void start() {
        // Just an old-fashioned inefficient operation loop here.
        loop.scheduleAtFixedRate(this::loop, 0, 1, TimeUnit.SECONDS);
    }

    private ExampleHospital() {
    }

    private void loop() {
//        System.out.println("ExampleHospital.loop()");
        if(!waitingRoom.isEmpty()){
            if(findAPatientADoctor()){
                return;
            }
        }
        if(!activeVisits.isEmpty()){
            performATreatment();
            return;
        }
    }

    private boolean findAPatientADoctor() {
        Patient patient = waitingRoom.remove();
        Ailment ailment = patient.ailments().get(0);
        Doctor doctor = DoctorFinder.find(doctors, ailment);
        //TODO: not thread-safe
        if (doctor == null) {
            System.out.println("Warning: Could not find " + patient + " a doctor.");
            waitingRoom.add(patient);
            return false;
        } else {
            startTreatment(patient, ailment, doctor);
            return true;
        }
    }

    private void startTreatment(Patient patient, Ailment ailment, Doctor doctor) {
        doctors.remove(doctor);
        doctorUnavailable(doctor, "Starting treatment of " + patient.name() + " for " + ailment);
        activeVisits.put(patient, doctor);
        // Ideally this would exist to facilitate library instrumentation, but we pretend this hasn't been built yet
        // listeners.forEach(listener -> listener.onStartDoctorVisit(patient, doctor));
    }

    private void performATreatment() {
        Map.Entry<Patient, Doctor> entry = activeVisits.entrySet().iterator().next();
        Patient patient = entry.getKey();
        Doctor doctor = entry.getValue();
        if(activeVisits.remove(patient) != null){
            Ailment ailment = patient.ailments().get(0);
            finishTreatment(patient, doctor, ailment);
        }
    }

    private void finishTreatment(Patient patient, Doctor doctor, Ailment ailment) {
        // Ideally this would exist to facilitate library instrumentation, but we pretend this hasn't been built yet
        // listeners.forEach(listener -> listener.onEndDoctorVisit(patient, doctor));
        System.out.println(patient.name() + " has been treated by " + doctor.name() + " for " + ailment);
        listeners.forEach(listener -> listener.onTreatment(doctor, patient, ailment, "Given excellent medical treatment!"));

        doctorAvailable(doctor);
        Patient healedPatient = patient.removeAilment(ailment);
        waitingRoom.add(healedPatient);
        if(!healedPatient.hasRemainingAilments()){
            // All healed up!
            checkOut(healedPatient);
        }
    }

    @Override
    public void doctorAvailable(Doctor doctor) {
        System.out.println(doctor.name() + " is now available in the hospital.");
        doctors.add(doctor);
    }

    @Override
    public void doctorUnavailable(Doctor doctor, String reason) {
        //TODO: Make sure doctor isn't with a patient and exists at this hospital
        System.out.println(doctor.name() + " is unavailable: " + reason);
        doctors.remove(doctor);
    }

    @Override
    public void checkIn(Patient patient) {
        System.out.println("Patient " + patient + " checking in.");
        waitingRoom.add(patient);
        listeners.forEach(listener -> listener.onCheckIn(patient));
    }

    @Override
    public boolean checkOut(Patient patient) {
        //TODO: Make sure patient is actually here and not in with a doctor
        if(waitingRoom.remove(patient)){
            System.out.println("Warning: patient " + patient + " was checked out.");
            listeners.forEach(listener -> listener.onCheckOut(patient));
            return true;
        }
        System.out.println("Warning: patient " + patient + " could not be checked out (not in waiting room)");
        return false;

    }

    @Override
    public void registerPatientListener(PatientListener listener) {
        listeners.add(listener);
    }
}
