package net.imshenik.university.entities;

import java.util.List;
import java.util.UUID;

public class Group {
    private final int uuid;
    private final String name;
    private List<Student> students;

    public Group(int uuid, String name, List<Student> students) {
        this.uuid = uuid;
        this.name = name;
        this.students = students;
    }

    public void addStudent(Student student){
        students.add(student);
    }
    public void removeStudent(Student student){
        students.remove(student);
    }
}
