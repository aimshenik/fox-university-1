package net.imshenik.university;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
        Student student2 = daoStudent.findOne(10);
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
        daoStudent.update(45, "Igor", "Stepanov");
    }
    
    @Test
    public void deleteTest() {
    }
}
