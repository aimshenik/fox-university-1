package net.imshenik.university;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.*;
import java.util.Set;
import org.junit.Test;
import net.imshenik.university.dao.postgres.DAOException;
import net.imshenik.university.dao.postgres.StudentDAO;
import net.imshenik.university.domain.entities.Student;

public class StudentDAOTest {
	StudentDAO daoStudent = new StudentDAO();

	@Test
	public void findAllTest() throws DAOException {
		Set<Student> students = null;
		students = daoStudent.findAll();
		assertNotNull(students);
	}

	@Test
	public void findOneTest() throws DAOException {
		Student student1 = daoStudent.findOne(Integer.MAX_VALUE);
		assertNull(student1);
		Student student2 = daoStudent.findOne(1);
		assertNotNull(student2);
		Student student3 = daoStudent.findOne(Integer.MIN_VALUE);
		assertNull(student3);
	}

	@Test
	public void createTest() throws DAOException {
		Student student = daoStudent.create("SERGEY", "IVANOV");
		assertNotNull(student);
	}

	@Test
	public void updateTest() throws DAOException {
		Student student = daoStudent.findOne(2);
		String oldName = student.getFirstName() + " " + student.getLastName();
		daoStudent.update(student.getId(), "NEW" + student.getFirstName(), "NEW" + student.getLastName());
		student = daoStudent.findOne(2);
		String newName = student.getFirstName() + " " + student.getLastName();
		assertNotEquals(oldName, newName);
	}

	@Test
	public void deleteTest() throws DAOException {
		Student newStudent = daoStudent.create("TODELETE:", "TODELETE");
		int lastID = newStudent.getId();
		daoStudent.delete(lastID);
		Student removedStudent = daoStudent.findOne(lastID);
		assertNull(removedStudent);
	}
}
