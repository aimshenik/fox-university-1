package net.imshenik.university;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.Test;
import net.imshenik.university.dao.ClassroomDAO;
import net.imshenik.university.dao.DAOException;
import net.imshenik.university.dao.GroupDAO;
import net.imshenik.university.dao.ScheduleDAO;
import net.imshenik.university.dao.SubjectDAO;
import net.imshenik.university.dao.TeacherDAO;
import net.imshenik.university.domain.Schedule;

public class ScheduleDAOTest {
    ScheduleDAO scheduleDAO = null;
    
    public ScheduleDAOTest() throws DAOException {
        scheduleDAO = new ScheduleDAO();
    }
    
    @Test
    public void findAllTest() throws DAOException {
        List<Schedule> schedules = null;
        schedules = scheduleDAO.findAll();
        assertNotNull(schedules);
    }
    
    @Test
    public void findOneTest() throws DAOException {
        Schedule schedule1 = scheduleDAO.findOne(Integer.MAX_VALUE);
        assertNull(schedule1);
        Schedule schedule2 = scheduleDAO.findOne(1);
        assertNotNull(schedule2);
        Schedule schedule3 = scheduleDAO.findOne(Integer.MIN_VALUE);
        assertNull(schedule3);
    }
    
    @Test
    public void createTest() throws DAOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Schedule schedule = scheduleDAO.create(new TeacherDAO().findOne(1), new GroupDAO().findOne(101),
                new ClassroomDAO().findOne(1), new SubjectDAO().findOne(1),
                LocalDateTime.parse("2018-01-09 10:40:00", dtf), LocalDateTime.parse("2018-01-09T12:00:00"));
        assertNotNull(schedule);
    }
    
    @Test
    public void updateTest() throws DAOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Schedule schedule = scheduleDAO.create(new TeacherDAO().findOne(1), new GroupDAO().findOne(101),
                new ClassroomDAO().findOne(1), new SubjectDAO().findOne(1),
                LocalDateTime.parse("2018-01-09 10:40:00", dtf), LocalDateTime.parse("2018-01-09T12:00:00"));
        scheduleDAO.update(schedule.getId(), new TeacherDAO().findOne(1), new GroupDAO().findOne(101),
                new ClassroomDAO().findOne(1), new SubjectDAO().findOne(1),
                LocalDateTime.parse("2018-01-09 12:40:00", dtf), LocalDateTime.parse("2018-01-09T14:00:00"));
    }
    
    @Test
    public void deleteTest() throws DAOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Schedule newschedule = scheduleDAO.create(new TeacherDAO().findOne(1), new GroupDAO().findOne(101),
                new ClassroomDAO().findOne(1), new SubjectDAO().findOne(1),
                LocalDateTime.parse("2018-01-09 10:40:00", dtf), LocalDateTime.parse("2018-01-09T12:00:00"));
        int lastID = newschedule.getId();
        scheduleDAO.delete(lastID);
        Schedule removedschedule = scheduleDAO.findOne(lastID);
        assertNull(removedschedule);
    }
}
