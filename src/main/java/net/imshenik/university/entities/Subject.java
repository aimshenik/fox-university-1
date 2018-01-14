package net.imshenik.university.entities;

import java.util.UUID;

public class Subject {
    private final int uuid;
    private final String name;

    public Subject(int uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }
}

