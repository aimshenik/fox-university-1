package net.imshenik.university;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import org.apache.log4j.Logger;
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
		classroomDaoPostgres.create(classroom);
		List<Classroom> classrooms = classroomDaoPostgres.findAll();
		classroomDaoPostgres.findOne(classroom.getId());
		classroomDaoPostgres.delete(classroom.getId());
	}
}
