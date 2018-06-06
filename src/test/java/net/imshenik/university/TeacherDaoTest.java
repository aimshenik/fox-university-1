package net.imshenik.university;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.Test;
import net.imshenik.university.dao.DaoException;
import net.imshenik.university.dao.TeacherDaoPostgres;
import net.imshenik.university.domain.Teacher;

public class TeacherDaoTest {
    TeacherDaoPostgres teacherDAO = null;
    
    public TeacherDaoTest() throws DaoException {
     teacherDAO =  new TeacherDaoPostgres();
    }
    
    @Test
    public void findAllTest() throws DaoException {
        List<Teacher> teachers = null;
        teachers = teacherDAO.findAll();
        assertTrue(teachers.size() > 0);
    }
    
    @Test
    public void findOneTest() throws DaoException {
        Teacher teacher1 = teacherDAO.findOne(Integer.MAX_VALUE);
        assertNull(teacher1);
        Teacher teacher2 = teacherDAO.findOne(2);
        assertNotNull(teacher2);
    }
    
    @Test
    public void createTest() throws DaoException {
        Teacher teacher = teacherDAO.create(new Teacher(0, "SERGEY IVANOVICH", "KRIVONOS" , "123"));
        assertTrue(teacher.getId() != 0);
    }
    
    @Test
    public void updateTest() throws DaoException {
        Teacher teacher = teacherDAO.create(new Teacher(0, "TO UPDATE", "TO UPDATE" , "TO UPDATE"));
        teacher.setFirstName("UPDATED");
        teacherDAO.update(teacher);
    }
    
    @Test
    public void deleteTest() throws DaoException {
        Teacher newTeacher = teacherDAO.create(new Teacher(0, "TO DELETE", "TO DELETE" , "TO DELETE"));
        int lastID = newTeacher.getId();
        teacherDAO.delete(lastID);
        Teacher removedTeacher = teacherDAO.findOne(lastID);
        assertNull(removedTeacher);
    }
}
