package net.imshenik.university;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import net.imshenik.university.dao.ClassroomDaoPostgres;
import net.imshenik.university.dao.DaoException;
import net.imshenik.university.domain.Classroom;

public class ClassroomDaoTest {
	ClassroomDaoPostgres classroomDaoPostgres = new ClassroomDaoPostgres();

	@Test
	public void classroomCrudTest() throws DaoException {
		H2handler.createTable("H2ClasstoomsCreation.sql");
		Classroom classroom = new Classroom("22", "d", 50);
		// create
		assertTrue(classroomDaoPostgres.create(classroom).equals(classroom));
		// findOne
		assertTrue(classroomDaoPostgres.findOne(classroom.getId()).equals(classroom));
		// findAll
		List<Classroom> classrooms = classroomDaoPostgres.findAll();
		assertTrue(classrooms.size() > 0);
		// update
		classroom.setCapacity(800);
		classroom.setNumber("23z");
		classroomDaoPostgres.update(classroom);
		assertTrue(classroomDaoPostgres.findOne(classroom.getId()).equals(classroom));
		// delete
		classroomDaoPostgres.delete(classroom.getId());
		assertNull(classroomDaoPostgres.findOne(classroom.getId()));
	}
}
