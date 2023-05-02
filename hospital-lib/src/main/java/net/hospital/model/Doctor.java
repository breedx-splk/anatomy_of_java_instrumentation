package net.hospital.model;

import java.util.List;

public record Doctor(String firstName, String lastName, List<Ailment> specialties) {
    public boolean canTreat(Ailment ailment) {
        return specialties.contains(ailment);
    }

    public String name() {
        return firstName + " " + lastName;
    }
}
