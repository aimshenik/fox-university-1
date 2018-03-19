package net.imshenik.university;

import net.imshenik.university.domain.entities.Schedule;
import net.imshenik.university.domain.factory.ScheduleFactory;
import net.imshenik.university.domain.factory.StudentFactory;
import net.imshenik.university.domain.service.ScheduleService;

import org.apache.log4j.Logger;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class TestScheduleBeanService {
    ScheduleService scheduleService = new ScheduleService();
    private static Logger logger = Logger.getLogger("TestScheduleBeanService");

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
