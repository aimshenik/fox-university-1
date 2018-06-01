package net.imshenik.university;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.util.List;
import org.junit.Test;
import net.imshenik.university.dao.DaoException;
import net.imshenik.university.dao.StudentDaoPostgres;
import net.imshenik.university.domain.Student;

public class StudentDaoTest {
    StudentDaoPostgres studentDAO = null;
    
    public StudentDaoTest() throws DaoException {
        studentDAO = new StudentDaoPostgres();
    }
    
    @Test
    public void findAllTest() throws DaoException {
        List<Student> students = null;
        students = studentDAO.findAll();
        assertNotNull(students);
    }
    
    @Test
    public void findOneTest() throws DaoException {
        Student student1 = studentDAO.findOne(Integer.MAX_VALUE);
        assertNull(student1);
        Student student2 = studentDAO.findOne(22);
        assertNotNull(student2);
        Student student3 = studentDAO.findOne(Integer.MIN_VALUE);
        assertNull(student3);
    }
    
    @Test
    public void createTest() throws DaoException {
        Student student = studentDAO.create(new Student(0, "SERGEY", "IVANOV", 0));
        assertNotNull(student);
    }
    
    @Test
    public void updateTest() throws DaoException {
        Student student = studentDAO.create(new Student(0,"FORUPDATE", "FORUPDATE", 0));
        student.setFirstName("CHANGED");
        studentDAO.update(student);
    }
    
    @Test
    public void deleteTest() throws DaoException {
        Student newStudent = studentDAO.create(new Student(0,"TODELETE", "TODELETE", 0));
        int lastID = newStudent.getId();
        studentDAO.delete(lastID);
        Student removedStudent = studentDAO.findOne(lastID);
        assertNull(removedStudent);
    }
}
