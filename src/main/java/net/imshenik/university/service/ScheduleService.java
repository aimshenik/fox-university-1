package net.imshenik.university.service;

import net.imshenik.university.entities.Schedule;
import net.imshenik.university.entities.Student;
import net.imshenik.university.entities.Teacher;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import net.imshenik.university.factory.ScheduleFactory;
import org.apache.log4j.Logger;

public class ScheduleService {
    final static Logger logger = Logger.getLogger("ScheduleService");

    public List<Schedule> getListPerStudentBetweenPeriod(Student student, LocalDateTime start, LocalDateTime end) {
        if (student == null || start == null || end == null) {
            logger.error("getListPerStudentBetweenPeriod(...) - NULL in parameters");
            return new ArrayList<Schedule>();
        }
        return ScheduleFactory.createSchedule();
    }

    public List<Schedule> getListPerTeacherBetweenPeriod(Teacher teacher, LocalDateTime start, LocalDateTime end) {
        if (teacher == null || start == null || end == null) {
            logger.error("getListPerStudentBetweenPeriod(...) - NULL in parameters");
            return new ArrayList<Schedule>();
        }
        return ScheduleFactory.createSchedule();
    }
}
