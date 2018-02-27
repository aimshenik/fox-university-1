package net.imshenik.university.factory;

import net.imshenik.university.entities.Schedule;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ScheduleFactory {
    public static List<Schedule> createSchedule(){
        List<Schedule> schedule = new ArrayList<>();
        //Ivanov
        schedule.add(new Schedule(1,
                TeacherFactory.createTeacherIvanov(),
                GroupFactory.createGroupK25(),
                ClassroomFactory.createClassroom115(),
                SubjectFactory.createSubjDB(),
                LocalDateTime.of(2018,1,2,9,00),
                LocalDateTime.of(2018,1,2,10,30)));
        schedule.add(new Schedule(2,
                TeacherFactory.createTeacherIvanov(),
                GroupFactory.createGroupL32(),
                ClassroomFactory.createClassroom115(),
                SubjectFactory.createSubjDB(),
                LocalDateTime.of(2018,1,2,10,40),
                LocalDateTime.of(2018,1,2,12,10)));
        schedule.add(new Schedule(3,
                TeacherFactory.createTeacherIvanov(),
                GroupFactory.createGroupZ18(),
                ClassroomFactory.createClassroom115(),
                SubjectFactory.createSubjDB(),
                LocalDateTime.of(2018,1,2,12,20),
                LocalDateTime.of(2018,1,2,13,50)));
        //Petrov
        schedule.add(new Schedule(4,
                TeacherFactory.createTeacherPetrov(),
                GroupFactory.createGroupL32(),
                ClassroomFactory.createClassroom304(),
                SubjectFactory.createSubjJava(),
                LocalDateTime.of(2018,1,2,9,00),
                LocalDateTime.of(2018,1,2,10,30)));
        schedule.add(new Schedule(5,
                TeacherFactory.createTeacherPetrov(),
                GroupFactory.createGroupZ18(),
                ClassroomFactory.createClassroom304(),
                SubjectFactory.createSubjJava(),
                LocalDateTime.of(2018,1,2,10,40),
                LocalDateTime.of(2018,1,2,12,10)));
        schedule.add(new Schedule(6,
                TeacherFactory.createTeacherPetrov(),
                GroupFactory.createGroupK25(),
                ClassroomFactory.createClassroom304(),
                SubjectFactory.createSubjJava(),
                LocalDateTime.of(2018,1,2,12,20),
                LocalDateTime.of(2018,1,2,13,50)));
        //Sidorov
        schedule.add(new Schedule(7,
                TeacherFactory.createTeacherSidorov(),
                GroupFactory.createGroupZ18(),
                ClassroomFactory.createClassroom525(),
                SubjectFactory.createSubjC(),
                LocalDateTime.of(2018,1,2,9,00),
                LocalDateTime.of(2018,1,2,10,30)));
        schedule.add(new Schedule(8,
                TeacherFactory.createTeacherSidorov(),
                GroupFactory.createGroupK25(),
                ClassroomFactory.createClassroom525(),
                SubjectFactory.createSubjC(),
                LocalDateTime.of(2018,1,2,10,40),
                LocalDateTime.of(2018,1,2,12,10)));
        schedule.add(new Schedule(9,
                TeacherFactory.createTeacherSidorov(),
                GroupFactory.createGroupL32(),
                ClassroomFactory.createClassroom525(),
                SubjectFactory.createSubjC(),
                LocalDateTime.of(2018,1,2,12,20),
                LocalDateTime.of(2018,1,2,13,50)));
        return schedule;
    }
}
