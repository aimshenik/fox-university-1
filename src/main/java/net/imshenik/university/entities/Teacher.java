package net.imshenik.university.entities;

import java.util.UUID;

public class Teacher {
    private final int uuid;
    private final String firstName;
    private final String lastName;
    private final String passport;

    public Teacher(int uuid, String firstName, String lastName, String passport) {
        this.uuid = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passport = passport;
    }
}
