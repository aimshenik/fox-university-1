package net.imshenik.university.entities;

import java.time.LocalDateTime;

public class Schedule {
    private int id;
    private Teacher teacher;
    private Group group;
    private Classroom classroom;
    private Subject subject;
    private LocalDateTime start;
    private LocalDateTime end;

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
}
