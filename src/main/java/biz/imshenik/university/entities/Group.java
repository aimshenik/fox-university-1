package biz.imshenik.university.entities;

import java.util.List;
import java.util.UUID;

public class Group {
    private final UUID uuid;
    private final String name;
    private List<Student> students;

    public Group(UUID uuid, String name, List<Student> students) {
        this.uuid = uuid;
        this.name = name;
        this.students = students;
    }
}
