package net.hospital.model;

import java.util.List;

public record Doctor(String firstName, String lastName, List<Ailment> specialties) {
}
