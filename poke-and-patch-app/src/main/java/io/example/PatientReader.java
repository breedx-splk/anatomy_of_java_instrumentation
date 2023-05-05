package io.example;

import net.hospital.model.Ailment;
import net.hospital.model.Patient;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class PatientReader {

    private final BufferedReader in;

    public PatientReader(BufferedReader in) {
        this.in = in;
    }

    Patient readPatient() throws IOException {
        System.out.print("Enter name: ");
        String name = in.readLine();
        String[] parts = name.split(" ");
        List<Ailment> ailments = new AilmentsReader(in).readAilments();
        return new Patient(parts[0], parts[1], ailments);
    }

}
