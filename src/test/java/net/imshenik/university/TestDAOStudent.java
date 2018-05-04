package net.imshenik.university;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
        Student student1 = daoStudent.findOne(Integer.MAX_VALUE);
        assertNull(student1);
        Student student2 = daoStudent.findOne(10);
        assertNotNull(student2);
        Student student3 = daoStudent.findOne(Integer.MIN_VALUE);
        assertNull(student3);
    }
    
    @Test
    public void createNormalTest() throws DAOException {
        Student student = daoStudent.create("SERGEY", "IVANOV");
        assertNotNull(student);
    }
    
    @Test
    public void createWithSpacesTest() throws DAOException {
        Student student = daoStudent.create("   Leonid   ", "   Gavrilov   ");
        String fName = "Leonid";
        String lName = "Gavrilov";
        assertNotNull(student);
        assertEquals(fName, student.getFirstName());
        assertEquals(lName, student.getLastName());
    }
    
    @Test(expected = DAOException.class)
    public void createNullFirstNameTest() throws DAOException {
        Student student = daoStudent.create(null, "IVANOV");
        assertNotNull(student);
    }
    
    @Test(expected = DAOException.class)
    public void createNullLastNameTest() throws DAOException {
        Student student = daoStudent.create("Gregory", null);
        assertNotNull(student);
    }
    
    @Test(expected = DAOException.class)
    public void createNullBothTest() throws DAOException {
        Student student = daoStudent.create(null, null);
        assertNotNull(student);
    }
    
    @Test(expected = DAOException.class)
    public void createSpacesOnlyFirstNameTest() throws DAOException {
        Student student = daoStudent.create("           ", "IVANOV");
        assertNotNull(student);
    }
    
    @Test(expected = DAOException.class)
    public void createSpacesOnlyLastNameTest() throws DAOException {
        Student student = daoStudent.create("Gregory", "           ");
        assertNotNull(student);
    }
    
    @Test(expected = DAOException.class)
    public void createSpacesOnlyBothTest() throws DAOException {
        Student student = daoStudent.create("           ", "              ");
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
