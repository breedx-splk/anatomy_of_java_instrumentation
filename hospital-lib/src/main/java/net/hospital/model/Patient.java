package net.hospital.model;

import java.util.ArrayList;
import java.util.List;

public record Patient(String firstName, String lastName, List<Ailment> ailments) {
    public String name() {
        return firstName + " " + lastName;
    }

    public Patient removeAilment(Ailment ailment) {
        List<Ailment> newAilments = new ArrayList<>(ailments);
        newAilments.remove(ailment);
        return new Patient(firstName, lastName, newAilments);
    }

    public boolean hasRemainingAilments() {
        return !ailments.isEmpty();
    }
}
