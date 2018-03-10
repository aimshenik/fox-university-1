package net.imshenik.university.entities;

import java.io.Serializable;
import java.util.List;

public class Group implements Serializable {
    private int id;
    private String name;
    private List<Student> students;

    public Group() {
    }

    public Group(int id, String name, List<Student> students) {
        this.id = id;
        this.name = name;
        this.students = students;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        return id == group.id;
    }

    @Override
    public int hashCode() {
        return id * 31;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", students= List of " + students.size() + " students" +
                '}';
    }
}
