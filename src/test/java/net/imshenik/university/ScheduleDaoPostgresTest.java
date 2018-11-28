package net.imshenik.university;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import net.imshenik.university.dao.ScheduleDaoPostgres;
import net.imshenik.university.dao.DaoException;
import net.imshenik.university.domain.Classroom;
import net.imshenik.university.domain.Group;
import net.imshenik.university.domain.Schedule;
import net.imshenik.university.domain.Subject;
import net.imshenik.university.domain.Teacher;

public class ScheduleDaoPostgresTest {
    private ScheduleDaoPostgres scheduleDaoPostgres = new ScheduleDaoPostgres();
    private H2handler h2handler = H2handler.getInstance();
    private static final String TABLENAME = "SCHEDULES";
    private static final String TEACHER_ID = "TEACHER_ID";
    private static final String GROUP_ID = "GROUP_ID";
    private static final String CLASSROOM_ID = "CLASSROOM_ID";
    private static final String SUBJECT_ID = "SUBJECT_ID";
    private static final String START_TIME = "START_TIME";
    private static final String END_TIME = "END_TIME";
    private final static String SQL_CREATE_FILENAME = "H2SchedulesDropCreate.sql";
    private final static String SQL_INSERT_FILENAME = "H2SchedulesInsert.sql";

    @Before
    public void initialize() throws Exception {
        h2handler.createTable(SQL_CREATE_FILENAME);
        h2handler.insertContent(SQL_INSERT_FILENAME);
    }

    @Test
    public void ScheduleCreateTest() throws Exception {
        Schedule expected = new Schedule(new Teacher(1, "1", "1", "1"), new Group(1, "1"),
                new Classroom(1, "1", "1", 1), new Subject(1, "1"), LocalDateTime.now(), LocalDateTime.now());
        int before = H2handler.getNumberOfRows(TABLENAME);
        Schedule actual = scheduleDaoPostgres.create(expected);
        int after = H2handler.getNumberOfRows(TABLENAME);
        assertEquals(after - before, 1);
        assertTrue(equals(expected, actual));
    }

    @Test
    public void scheduleFindOneTest() throws Exception {
        Teacher[] teachers = { new Teacher(1, "ANDREY FEDOROVICH", "SMIKOV", "AA-11111"),
                new Teacher(2, "VIKTOR SERGEEVICH", "MOISEEV", "AA-22222"),
                new Teacher(3, "DMITRY VASILYEVICH", "BEREZHNOY", "AA-33333"),
                new Teacher(4, "BORIS LEONIDOVICH", "BOBRYSHEV", "AA-44444"),
                new Teacher(5, "OLGA VALENTINOVNA", "TELITSYNA", "AA-55555") };
        Subject[] subjects = { new Subject(1, "Heat engineering and heat transfer basics"),
                new Subject(2, "Metrology, standardization and certification"),
                new Subject(3, "Safety of vital functions"), new Subject(4, "Basics of construction"),
                new Subject(5, "Foundry technology") };
        Group[] groups = { new Group(1, "1-D-31"), new Group(2, "1-L-32"), new Group(3, "2-M-43"),
                new Group(4, "3-F-64"), new Group(5, "3-SK-90") };
        Classroom[] classrooms = { new Classroom(1, "106", "23", 30), new Classroom(2, "211", "23", 50),
                new Classroom(3, "321", "23", 30), new Classroom(4, "401", "24", 100),
                new Classroom(5, "305", "24", 30) };
        LocalDateTime[] starts = { LocalDateTime.parse("2018-01-09T09:00:00"),
                LocalDateTime.parse("2018-01-09T10:30:00"), LocalDateTime.parse("2018-01-09T12:00:00"),
                LocalDateTime.parse("2018-01-09T14:00:00"), LocalDateTime.parse("2018-01-09T15:30:00"), };
        LocalDateTime[] ends = { LocalDateTime.parse("2018-01-09T10:20:00"), LocalDateTime.parse("2018-01-09T11:50:00"),
                LocalDateTime.parse("2018-01-09T13:20:00"), LocalDateTime.parse("2018-01-09T15:20:00"),
                LocalDateTime.parse("2018-01-09T16:50:00"), };
        Schedule[] schedules = new Schedule[5];
        for (int i = 0; i < schedules.length; i++) {
            schedules[i] = new Schedule();
            schedules[i].setId(i + 1);
            schedules[i].setClassroom(classrooms[i]);
            schedules[i].setGroup(groups[i]);
            schedules[i].setTeacher(teachers[i]);
            schedules[i].setSubject(subjects[i]);
            schedules[i].setStart(starts[i]);
            schedules[i].setEnd(ends[i]);
        }
        for (int i = 0; i < schedules.length; i++) {
            Schedule expected = schedules[i];
            Schedule actual = scheduleDaoPostgres.findOne(i + 1);
            assertTrue(equals(expected, actual));
        }
    }

    @Test
    public void scheduleFindAllTest() throws Exception {
        List<Schedule> schedules = scheduleDaoPostgres.findAll();
        assertTrue(schedules.size() == 5);
        for (Schedule schedule : schedules) {
            assertTrue(exist(schedule.getId()));
        }
    }

    @Test
    public void scheduleUpdateTest() throws Exception {
        Teacher[] teachers = { new Teacher(1, "ANDREY FEDOROVICH", "SMIKOV", "AA-11111"),
                new Teacher(2, "VIKTOR SERGEEVICH", "MOISEEV", "AA-22222"),
                new Teacher(3, "DMITRY VASILYEVICH", "BEREZHNOY", "AA-33333"),
                new Teacher(4, "BORIS LEONIDOVICH", "BOBRYSHEV", "AA-44444"),
                new Teacher(5, "OLGA VALENTINOVNA", "TELITSYNA", "AA-55555") };
        Subject[] subjects = { new Subject(1, "Heat engineering and heat transfer basics"),
                new Subject(2, "Metrology, standardization and certification"),
                new Subject(3, "Safety of vital functions"), new Subject(4, "Basics of construction"),
                new Subject(5, "Foundry technology") };
        Group[] groups = { new Group(1, "1-D-31"), new Group(2, "1-L-32"), new Group(3, "2-M-43"),
                new Group(4, "3-F-64"), new Group(5, "3-SK-90") };
        Classroom[] classrooms = { new Classroom(1, "106", "23", 30), new Classroom(2, "211", "23", 50),
                new Classroom(3, "321", "23", 30), new Classroom(4, "401", "24", 100),
                new Classroom(5, "305", "24", 30) };
        LocalDateTime[] starts = { LocalDateTime.parse("2018-01-09T09:00:00"),
                LocalDateTime.parse("2018-01-09T10:30:00"), LocalDateTime.parse("2018-01-09T12:00:00"),
                LocalDateTime.parse("2018-01-09T14:00:00"), LocalDateTime.parse("2021-01-09T15:30:00") };
        LocalDateTime[] ends = { LocalDateTime.parse("2018-01-09T10:20:00"), LocalDateTime.parse("2018-01-09T11:50:00"),
                LocalDateTime.parse("2018-01-09T13:20:00"), LocalDateTime.parse("2018-01-09T15:20:00"),
                LocalDateTime.parse("2021-01-09T16:50:00") };
        Schedule[] schedules = new Schedule[5];
        for (int i = 0; i < schedules.length; i++) {
            schedules[i] = new Schedule();
            schedules[i].setId(i + 1);
            schedules[i].setClassroom(classrooms[i == 4 ? 0 : i + 1]);
            schedules[i].setGroup(groups[i == 4 ? 0 : i + 1]);
            schedules[i].setTeacher(teachers[i == 4 ? 0 : i + 1]);
            schedules[i].setSubject(subjects[i == 4 ? 0 : i + 1]);
            schedules[i].setStart(starts[i]);
            schedules[i].setEnd(ends[i]);
        }
        for (int i = 0; i < schedules.length; i++) {
            scheduleDaoPostgres.update(schedules[i]);
        }
        assertEquals((int) H2handler.getField(TABLENAME, TEACHER_ID, 1), 2);
        assertEquals((int) H2handler.getField(TABLENAME, SUBJECT_ID, 2), 3);
        assertEquals((int) H2handler.getField(TABLENAME, GROUP_ID, 3), 4);
        assertEquals((int) H2handler.getField(TABLENAME, CLASSROOM_ID, 4), 5);
        assertTrue(((Timestamp) H2handler.getField(TABLENAME, START_TIME, 5)).toLocalDateTime()
                .equals(LocalDateTime.parse("2021-01-09T15:30:00")));
        assertTrue(((Timestamp) H2handler.getField(TABLENAME, END_TIME, 5)).toLocalDateTime()
                .equals(LocalDateTime.parse("2021-01-09T16:50:00")));
    }

    @Test(expected = DaoException.class)
    public void scheduleUpdateExceptionTest() throws Exception {
        int notExistID = 1_000;
        Schedule schedule = new Schedule();
        schedule.setId(notExistID);
        scheduleDaoPostgres.update(schedule);
    }

    @Test
    public void scheduleDeleteTest() throws Exception {
        for (int i = 1; i < 6; i++) {
            scheduleDaoPostgres.delete(i);
            assertFalse(exist(i));
        }
    }

    private boolean exist(Integer id) throws DaoException {
        return H2handler.exist(TABLENAME, id);
    }

    private boolean equals(Schedule expected, Schedule actual) {
        if (expected.getId() == actual.getId() && expected.getTeacher().equals(actual.getTeacher())
                && expected.getClassroom().equals(actual.getClassroom())
                && expected.getGroup().equals(actual.getGroup()) && expected.getSubject().equals(actual.getSubject())
                && expected.getStart().equals(actual.getStart()) && expected.getEnd().equals(actual.getEnd())) {
            return true;
        }
        return false;
    }
}
