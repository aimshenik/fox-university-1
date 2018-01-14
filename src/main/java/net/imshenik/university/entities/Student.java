package net.imshenik.university.entities;

import java.util.UUID;

public class Student {
    private final int uuid;
    private final String firstName;
    private final String lastName;

    public Student(int uuid, String firstName, String lastName) {
        this.uuid = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
