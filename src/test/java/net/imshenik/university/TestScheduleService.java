package net.imshenik.university;

import net.imshenik.university.entities.*;
import net.imshenik.university.service.ScheduleService;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static junit.framework.TestCase.assertEquals;

public class TestScheduleService {
    ScheduleService scheduleService = new ScheduleService();
    Teacher teacher1 = new Teacher(1, "Sergey", "Ivanov", "A-111111111");
    Teacher teacher2 = new Teacher(2, "Peter", "Razin", "A-222222222");
    Student student1 = new Student(10, "Igor", "Newmann");
    Student student2 = new Student(20, "Victor", "Orlov");
    Group group = new Group(100, "1-B-56", new ArrayList<>(Arrays.asList(student1,student2)));
    Classroom classroom1 = new Classroom(1000, "5A", "D", 4, "A", 50);
    Classroom classroom2 = new Classroom(2000, "9B", "A", 1, "C", 150);
    Subject subject1 = new Subject(10000, "Math");
    Subject subject2 = new Subject(20000, "Programming");
    Schedule schedule1 = new Schedule(100000,
            teacher1,
            group,
            classroom1,
            subject1,
            LocalDateTime.of(2018, 1, 1, 9, 00),
            LocalDateTime.of(2018, 1, 1, 10, 30));
    Schedule schedule2 = new Schedule(100000,
            teacher2,
            group,
            classroom2,
            subject2,
            LocalDateTime.of(2018, 1, 1, 10, 40),
            LocalDateTime.of(2018, 1, 1, 12, 10));

    @Test
    public void requestNull() {
        Object expected  = null;
        List<Schedule> actual = scheduleService.getListPerStudentBetweenPeriod(null,null,null);
        assertEquals(expected, actual);
    }
}
