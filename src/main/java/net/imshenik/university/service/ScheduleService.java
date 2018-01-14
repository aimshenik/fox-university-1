package net.imshenik.university.service;

import net.imshenik.university.entities.Schedule;
import net.imshenik.university.entities.Student;
import net.imshenik.university.entities.Teacher;

import java.time.LocalDateTime;
import java.util.List;

public class ScheduleService {

    public List<Schedule> getListPerStudentBetweenPeriod(Student student, LocalDateTime start, LocalDateTime end){
        if (student == null || start == null || end == null){
            System.out.println("Please verify your request");
            return null;
        }
        return null;


    }

    public List<Schedule> getListPerTeacherBetweenPeriod(Teacher teacher, LocalDateTime start, LocalDateTime end){
        if (teacher == null || start == null || end == null){
            System.out.println("Please verify your request");
            return null;
        }
        return null;
    }
}
