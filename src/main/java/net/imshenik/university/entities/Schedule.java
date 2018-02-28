package net.imshenik.university.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;

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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Schedule schedule = (Schedule) o;

        if (teacher != null ? !teacher.equals(schedule.teacher) : schedule.teacher != null) return false;
        if (group != null ? !group.equals(schedule.group) : schedule.group != null) return false;
        if (classroom != null ? !classroom.equals(schedule.classroom) : schedule.classroom != null) return false;
        if (subject != null ? !subject.equals(schedule.subject) : schedule.subject != null) return false;
        if (start != null ? !start.equals(schedule.start) : schedule.start != null) return false;
        return end != null ? end.equals(schedule.end) : schedule.end == null;
    }

    @Override
    public int hashCode() {
        int result = teacher != null ? teacher.hashCode() : 0;
        result = 31 * result + (group != null ? group.hashCode() : 0);
        result = 31 * result + (classroom != null ? classroom.hashCode() : 0);
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        result = 31 * result + (start != null ? start.hashCode() : 0);
        result = 31 * result + (end != null ? end.hashCode() : 0);
        return result;
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
