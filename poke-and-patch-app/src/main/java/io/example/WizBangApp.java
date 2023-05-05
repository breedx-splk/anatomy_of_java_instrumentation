package io.example;

import net.hospital.api.Hospital;
import net.hospital.api.PatientListener;
import net.hospital.impl.ExampleHospital;
import net.hospital.model.Patient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Scanner;

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
        new WizBangApp(hospital).run();
    }

    private void run() throws IOException {
        hospital.registerPatientListener(this);
        while(true){
            mainMenu();
            String entry = read();
            if(Objects.equals(entry, "1")){
                Patient patient = new PatientReader(in).readPatient();
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
