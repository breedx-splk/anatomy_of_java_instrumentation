package net.hospital.impl;

import net.hospital.api.Hospital;
import net.hospital.api.PatientListener;
import net.hospital.model.Doctor;
import net.hospital.model.Patient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class ExampleHospital implements Hospital {

    private final Queue<Doctor> doctors = new ConcurrentLinkedQueue<>();
    private final Queue<Patient> waitingRoom = new ConcurrentLinkedQueue<>();
    private final Map<Patient,Doctor> activeVisits = new ConcurrentHashMap<>();
    private final ScheduledExecutorService loop = Executors.newSingleThreadScheduledExecutor();

    public static Hospital create(){
        ExampleHospital hospital = new ExampleHospital();
        hospital.start();
        return hospital;
    }

    private void start() {
        // Just an old-fashioned operation loop here.
        loop.scheduleAtFixedRate(this::loop, 0, 1000, TimeUnit.SECONDS);
    }

    private ExampleHospital(){ }

    private void loop(){

    }

    @Override
    public void doctorAvailable(Doctor doctor) {

    }

    @Override
    public void doctorUnavailable(Doctor doctor) {

    }

    @Override
    public void checkIn(Patient patient) {

    }

    @Override
    public void checkOut(Patient patient) {

    }

    @Override
    public void registerPatientListener(PatientListener listener) {

    }
}
