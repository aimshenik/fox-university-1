package net.imshenik.university.entities;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public class Schedule {
    private final int uuid;
    private final Teacher teacher;
    private final Group group;
    private final Classroom classroom;
    private final Subject subject;
    private final LocalDateTime start;
    private final LocalDateTime end;

    public Schedule(int uuid, Teacher teacher, Group group, Classroom classroom, Subject subject, LocalDateTime start, LocalDateTime end) {
        this.uuid = uuid;
        this.teacher = teacher;
        this.group = group;
        this.classroom = classroom;
        this.subject = subject;
        this.start = start;
        this.end = end;
    }
}
