package net.imshenik.university;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.Test;
import net.imshenik.university.dao.ClassroomDao;
import net.imshenik.university.dao.DaoException;
import net.imshenik.university.dao.GroupDao;
import net.imshenik.university.dao.ScheduleDao;
import net.imshenik.university.dao.SubjectDao;
import net.imshenik.university.dao.TeacherDao;
import net.imshenik.university.domain.Schedule;

public class ScheduleDaoTest {
    ScheduleDao scheduleDAO = null;
    
    public ScheduleDaoTest() throws DaoException {
        scheduleDAO = new ScheduleDao();
    }
    
    @Test
    public void findAllTest() throws DaoException {
        List<Schedule> schedules = null;
        schedules = scheduleDAO.findAll();
        assertNotNull(schedules);
    }
    
    @Test
    public void findOneTest() throws DaoException {
        Schedule schedule1 = scheduleDAO.findOne(Integer.MAX_VALUE);
        assertNull(schedule1);
        Schedule schedule2 = scheduleDAO.findOne(1);
        assertNotNull(schedule2);
        Schedule schedule3 = scheduleDAO.findOne(Integer.MIN_VALUE);
        assertNull(schedule3);
    }
    
    @Test
    public void createTest() throws DaoException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Schedule schedule = scheduleDAO.create(new TeacherDao().findOne(1), new GroupDao().findOne(101),
                new ClassroomDao().findOne(1), new SubjectDao().findOne(1),
                LocalDateTime.parse("2018-01-09 10:40:00", dtf), LocalDateTime.parse("2018-01-09T12:00:00"));
        assertNotNull(schedule);
    }
    
    @Test
    public void updateTest() throws DaoException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Schedule schedule = scheduleDAO.create(new TeacherDao().findOne(1), new GroupDao().findOne(101),
                new ClassroomDao().findOne(1), new SubjectDao().findOne(1),
                LocalDateTime.parse("2018-01-09 10:40:00", dtf), LocalDateTime.parse("2018-01-09T12:00:00"));
        scheduleDAO.update(schedule.getId(), new TeacherDao().findOne(1), new GroupDao().findOne(101),
                new ClassroomDao().findOne(1), new SubjectDao().findOne(1),
                LocalDateTime.parse("2018-01-09 12:40:00", dtf), LocalDateTime.parse("2018-01-09T14:00:00"));
    }
    
    @Test
    public void deleteTest() throws DaoException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Schedule newschedule = scheduleDAO.create(new TeacherDao().findOne(1), new GroupDao().findOne(101),
                new ClassroomDao().findOne(1), new SubjectDao().findOne(1),
                LocalDateTime.parse("2018-01-09 10:40:00", dtf), LocalDateTime.parse("2018-01-09T12:00:00"));
        int lastID = newschedule.getId();
        scheduleDAO.delete(lastID);
        Schedule removedschedule = scheduleDAO.findOne(lastID);
        assertNull(removedschedule);
    }
}
