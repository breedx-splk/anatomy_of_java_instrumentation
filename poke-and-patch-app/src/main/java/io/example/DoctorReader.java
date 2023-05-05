package io.example;

import net.hospital.model.Ailment;
import net.hospital.model.Doctor;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class DoctorReader {
    private final BufferedReader in;

    public DoctorReader(BufferedReader in) {
        this.in = in;
    }

    public Doctor readDoctor() throws IOException {
        System.out.print("Enter name: ");
        String name = in.readLine();
        String[] parts = name.split(" ");
        List<Ailment> ailments = new AilmentsReader(in).readAilments("specialties");
        return new Doctor(parts[0], parts[1], ailments);
    }
}
