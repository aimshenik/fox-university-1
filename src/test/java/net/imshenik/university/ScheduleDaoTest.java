package net.imshenik.university;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.Test;
import net.imshenik.university.dao.ClassroomDaoPostgres;
import net.imshenik.university.dao.DaoException;
import net.imshenik.university.dao.GroupDaoPostgres;
import net.imshenik.university.dao.ScheduleDaoPostgres;
import net.imshenik.university.dao.SubjectDaoPostgres;
import net.imshenik.university.dao.TeacherDaoPostgres;
import net.imshenik.university.domain.Schedule;

public class ScheduleDaoTest {
    ScheduleDaoPostgres scheduleDaoPostgres = null;
    
    public ScheduleDaoTest() {
        this.scheduleDaoPostgres = new ScheduleDaoPostgres();
    }
    
    @Test
    public void findAllTest() throws DaoException {
        List<Schedule> schedules = null;
        schedules = scheduleDaoPostgres.findAll();
        assertTrue(schedules.size() > 0);
    }
    
    @Test
    public void findOneTest() throws DaoException {
        Schedule schedule1 = scheduleDaoPostgres.findOne(Integer.MAX_VALUE);
        assertNull(schedule1);
        Schedule schedule2 = scheduleDaoPostgres.findOne(1);
        assertNotNull(schedule2);
    }
    
    @Test
    public void createTest() throws DaoException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Schedule schedule = scheduleDaoPostgres
                .create(new Schedule(0, new TeacherDaoPostgres().findOne(1), new GroupDaoPostgres().findOne(101),
                        new ClassroomDaoPostgres().findOne(1), new SubjectDaoPostgres().findOne(1),
                        LocalDateTime.parse("2018-01-09 10:40:00", dtf), LocalDateTime.parse("2018-01-09T12:00:00")));
        assertTrue(schedule.getId() != 0);
    }
    
    @Test
    public void updateTest() throws DaoException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Schedule schedule = new Schedule(0, new TeacherDaoPostgres().findOne(1), new GroupDaoPostgres().findOne(101),
                new ClassroomDaoPostgres().findOne(1), new SubjectDaoPostgres().findOne(1),
                LocalDateTime.parse("2018-01-09 10:40:00", dtf), LocalDateTime.parse("2018-01-09T12:00:00"));
        Schedule scheduleNew = scheduleDaoPostgres.create(schedule);
        scheduleNew.setClassroom(new ClassroomDaoPostgres().findOne(2));
        scheduleDaoPostgres.update(scheduleNew);
    }
    
    @Test
    public void deleteTest() throws DaoException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Schedule schedule = new Schedule(0, new TeacherDaoPostgres().findOne(1), new GroupDaoPostgres().findOne(101),
                new ClassroomDaoPostgres().findOne(1), new SubjectDaoPostgres().findOne(1),
                LocalDateTime.parse("2018-01-09 10:40:00", dtf), LocalDateTime.parse("2018-01-09T12:00:00"));
        Schedule scheduleNew = scheduleDaoPostgres.create(schedule);
        int lastID = scheduleNew.getId();
        scheduleDaoPostgres.delete(lastID);
        Schedule removedSchedule = scheduleDaoPostgres.findOne(lastID);
        assertNull(removedSchedule);
    }
}
