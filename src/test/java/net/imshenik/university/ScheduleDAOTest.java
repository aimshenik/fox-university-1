package net.imshenik.university;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Set;
import org.junit.Test;

import net.imshenik.university.dao.postgres.ClassroomDAO;
import net.imshenik.university.dao.postgres.DAOException;
import net.imshenik.university.dao.postgres.GroupDAO;
import net.imshenik.university.dao.postgres.ScheduleDAO;
import net.imshenik.university.dao.postgres.SubjectDAO;
import net.imshenik.university.dao.postgres.TeacherDAO;
import net.imshenik.university.domain.entities.Schedule;
import net.imshenik.university.domain.entities.Teacher;

public class ScheduleDAOTest {
	ScheduleDAO scheduleDAO = new ScheduleDAO();

	@Test
	public void findAllTest() throws DAOException {
		Set<Schedule> schedules = null;
		schedules = scheduleDAO.findAll();
		assertNotNull(schedules);
		for (Iterator iterator = schedules.iterator(); iterator.hasNext();) {
			Schedule schedule = (Schedule) iterator.next();
			System.out.println(schedule.toString());

		}
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
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		Schedule schedule = scheduleDAO.create(new TeacherDAO().findOne(1), new GroupDAO().findOne(101),
				new ClassroomDAO().findOne(1), new SubjectDAO().findOne(1), LocalDateTime.parse("2018-01-09 10:40:00", formatter),
				LocalDateTime.parse("2018-01-09 12:00:00", formatter));
		assertNotNull(schedule);
	}

	// @Test
	// public void updateTest() throws DAOException {
	// schedule schedule = scheduleDAO.create("FORUPDATE", "FORUPDATE");
	// scheduleDAO.update(schedule.getId(), "NEW" + schedule.getFirstName(),
	// "NEW" + schedule.getLastName(), 101);
	// }
	//
	// @Test
	// public void deleteTest() throws DAOException {
	// schedule newschedule = scheduleDAO.create("TODELETE:", "TODELETE");
	// int lastID = newschedule.getId();
	// scheduleDAO.delete(lastID);
	// schedule removedschedule = scheduleDAO.findOne(lastID);
	// assertNull(removedschedule);
	// }
}
