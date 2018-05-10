package net.imshenik.university;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.util.Set;
import org.junit.Test;
import net.imshenik.university.dao.postgres.DAOException;
import net.imshenik.university.dao.postgres.TeacherDAO;
import net.imshenik.university.domain.entities.Teacher;

public class TeacherDAOTest {
    TeacherDAO teacherDAO = new TeacherDAO();
    
    @Test
    public void findAllTest() throws DAOException {
        Set<Teacher> teachers = null;
        teachers = teacherDAO.findAll();
        assertNotNull(teachers);
    }
    
    @Test
    public void findOneTest() throws DAOException {
        Teacher teacher1 = teacherDAO.findOne(Integer.MAX_VALUE);
        assertNull(teacher1);
        Teacher teacher2 = teacherDAO.findOne(2);
        assertNotNull(teacher2);
        Teacher teacher3 = teacherDAO.findOne(Integer.MIN_VALUE);
        assertNull(teacher3);
    }
    
    @Test
    public void createTest() throws DAOException {
        Teacher teacher = teacherDAO.create("SERGEY IVANOVICH", "KRIVONOS" , "123");
        assertNotNull(teacher);
    }
    
    @Test
    public void updateTest() throws DAOException {
        Teacher teacher = teacherDAO.create("FORUPDATE", "FORUPDATE", "000000");
        teacherDAO.update(teacher.getId(), "NEW" + teacher.getFirstName(), "NEW" + teacher.getLastName(), "NEW" + teacher.getPassport());
    }
    
    @Test
    public void deleteTest() throws DAOException {
        Teacher newTeacher = teacherDAO.create("TODELETE:", "TODELETE","29361293");
        int lastID = newTeacher.getId();
        teacherDAO.delete(lastID);
        Teacher removedTeacher = teacherDAO.findOne(lastID);
        assertNull(removedTeacher);
    }
}
