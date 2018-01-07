package biz.imshenik.university.entities;

import java.util.UUID;

public class Subject {
    private final UUID uuid;
    private final String name;

    public Subject(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }
}

