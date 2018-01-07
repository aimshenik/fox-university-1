package biz.imshenik.university.entities;

import java.util.Date;
import java.util.UUID;

public class Schedule {
    private final UUID uuid;
    private final Teacher teacher;
    private final Group group;
    private final Classroom classroom;
    private final Subject subject;
    private final Date start;
    private final Date end;

    public Schedule(UUID uuid, Teacher teacher, Group group, Classroom classroom, Subject subject, Date start, Date end) {
        this.uuid = uuid;
        this.teacher = teacher;
        this.group = group;
        this.classroom = classroom;
        this.subject = subject;
        this.start = start;
        this.end = end;
    }
}
