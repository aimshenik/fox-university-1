package net.imshenik.university;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.Set;
import org.junit.Test;
import net.imshenik.university.dao.postgres.DAOException;
import net.imshenik.university.dao.postgres.DAOStudent;
import net.imshenik.university.domain.entities.Student;

public class TestDAOStudent {
    DAOStudent daoStudent = new DAOStudent();
    
    @Test
    public void findAllTest() throws DAOException {
        Set<Student> students = null;
        students = daoStudent.findAll();
        assertNotNull(students);
    }
    
    @Test
    public void findOneTest() throws DAOException {
        Student expected = daoStudent.findOne(1);
        Student actual = new Student();
        actual.setId(1);
        actual.setFirstName("Andrey");
        actual.setLastName("Imshenik");
        assertEquals(expected, actual);
    }
    
    @Test
    public void createTest() throws DAOException {
        boolean expected = true; 
        boolean actual = daoStudent.create("Nickota", "Ivanov");
        assertEquals(expected, actual);
    }
    
    @Test
    public void updateTest() {
    }
    
    @Test
    public void deleteTest() {
    }
}
