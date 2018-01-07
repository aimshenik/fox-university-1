package biz.imshenik.university.entities;

import java.util.UUID;

public class Student {
    private final UUID uuid;
    private final String firstName;
    private final String lastName;

    public Student(UUID uuid, String firstName, String lastName) {
        this.uuid = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
