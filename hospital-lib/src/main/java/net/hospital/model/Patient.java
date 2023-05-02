package net.hospital.model;

import java.util.List;

public record Patient(String firstName, String lastName, List<Ailment> ailments) {
}
