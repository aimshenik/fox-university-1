package net.imshenik.university.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Schedule implements Serializable {
    private int id;
    private Teacher teacher;
    private Group group;
    private Classroom classroom;
    private Subject subject;
    private LocalDateTime start;
    private LocalDateTime end;

    public Schedule() {
    }

    public Schedule(int id, Teacher teacher, Group group, Classroom classroom, Subject subject, LocalDateTime start, LocalDateTime end) {
        this.id = id;
        this.teacher = teacher;
        this.group = group;
        this.classroom = classroom;
        this.subject = subject;
        this.start = start;
        this.end = end;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Schedule schedule = (Schedule) o;
        if (id != schedule.id) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return 31 * id;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", teacher=" + teacher +
                ", group=" + group +
                ", classroom=" + classroom +
                ", subject=" + subject +
                ", start='" + start + "'" +
                ", end='" + end + "'" +
                '}';
    }
}
