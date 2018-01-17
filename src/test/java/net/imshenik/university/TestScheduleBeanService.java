package net.imshenik.university;

import net.imshenik.university.entities.*;
import net.imshenik.university.factory.ScheduleFactory;
import net.imshenik.university.factory.StudentFactory;
import net.imshenik.university.service.ScheduleService;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class TestScheduleBeanService {
    ScheduleService scheduleService = new ScheduleService();

    @Test
    public void requestNull() {
        Object expected = new ArrayList<Schedule>();
        List<Schedule> actual = scheduleService.getListPerStudentBetweenPeriod(null, null, null);
        assertEquals(expected, actual);
    }

    @Test
    public void requestNotNull() {
        Object expected = ScheduleFactory.createSchedule();
        List<Schedule> actual = scheduleService.getListPerStudentBetweenPeriod(
                StudentFactory.createStudentBelov(),
                LocalDateTime.of(2018, 1, 1, 9, 0),
                LocalDateTime.of(2018, 1, 2, 0, 0));
        assertEquals(expected, actual);
    }
}
