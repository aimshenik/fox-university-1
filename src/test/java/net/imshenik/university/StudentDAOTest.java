package net.imshenik.university;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.util.Set;
import org.junit.Test;
import net.imshenik.university.dao.postgres.DAOException;
import net.imshenik.university.dao.postgres.StudentDAO;
import net.imshenik.university.domain.entities.Student;

public class StudentDAOTest {
    StudentDAO studentDAO = null;
    
    public StudentDAOTest() throws DAOException {
        studentDAO = new StudentDAO();
    }
    
    @Test
    public void findAllTest() throws DAOException {
        Set<Student> students = null;
        students = studentDAO.findAll();
        assertNotNull(students);
    }
    
    @Test
    public void findOneTest() throws DAOException {
        Student student1 = studentDAO.findOne(Integer.MAX_VALUE);
        assertNull(student1);
        Student student2 = studentDAO.findOne(22);
        assertNotNull(student2);
        Student student3 = studentDAO.findOne(Integer.MIN_VALUE);
        assertNull(student3);
    }
    
    @Test
    public void createTest() throws DAOException {
        Student student = studentDAO.create("SERGEY", "IVANOV");
        assertNotNull(student);
    }
    
    @Test
    public void updateTest() throws DAOException {
        Student student = studentDAO.create("FORUPDATE", "FORUPDATE");
        studentDAO.update(student.getId(), "NEW" + student.getFirstName(), "NEW" + student.getLastName(), 101);
    }
    
    @Test
    public void deleteTest() throws DAOException {
        Student newStudent = studentDAO.create("TODELETE:", "TODELETE");
        int lastID = newStudent.getId();
        studentDAO.delete(lastID);
        Student removedStudent = studentDAO.findOne(lastID);
        assertNull(removedStudent);
    }
}
