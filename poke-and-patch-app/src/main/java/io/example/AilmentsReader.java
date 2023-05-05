package io.example;

import net.hospital.model.Ailment;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AilmentsReader {
    private final BufferedReader in;

    public AilmentsReader(BufferedReader in) {
        this.in = in;
    }

    List<Ailment> readAilments() throws IOException {
        return readAilments("ailments");
    }

    List<Ailment> readAilments(String prompt) throws IOException {
        List<Ailment> all = Arrays.asList(Ailment.values());
        for (int i = 0; i < all.size(); i++) {
            System.out.printf(" %2d. %s\n", i+1, all.get(i));
        }
        System.out.println();
        System.out.printf("Enter %s: ", prompt);
        String[] parts = in.readLine().split(",");
        return Arrays.stream(parts)
                .map(Integer::valueOf)
                .map(i -> i-1)
                .map(all::get)
                .collect(Collectors.toList());
    }
}
