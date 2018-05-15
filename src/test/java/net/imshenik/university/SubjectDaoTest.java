package net.imshenik.university;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.util.List;
import org.junit.Test;
import net.imshenik.university.dao.DaoException;
import net.imshenik.university.dao.SubjectDao;
import net.imshenik.university.domain.Subject;

public class SubjectDaoTest {
    SubjectDao subjectDAO = null;
    
    public SubjectDaoTest() throws DaoException {
     subjectDAO =  new SubjectDao();
    }
    
    @Test
    public void findAllTest() throws DaoException {
        List<Subject> subjects = null;
        subjects = subjectDAO.findAll();
        assertNotNull(subjects);
    }
    
    @Test
    public void findOneTest() throws DaoException {
        Subject subject = subjectDAO.findOne(Integer.MAX_VALUE);
        assertNull(subject);
        Subject Subject2 = subjectDAO.findOne(1);
        assertNotNull(Subject2);
        Subject Subject3 = subjectDAO.findOne(Integer.MIN_VALUE);
        assertNull(Subject3);
    }
    
    @Test
    public void createTest() throws DaoException {
        Subject Subject = subjectDAO.create("MATRIALLOVEDENIE");
        assertNotNull(Subject);
    }
    
    @Test
    public void updateTest() throws DaoException {
        Subject Subject = subjectDAO.create("SBJ4UPD");
        subjectDAO.update(Subject.getId(), "NEW" + Subject.getName());
    }
    
    @Test
    public void deleteTest() throws DaoException {
        Subject newSubject = subjectDAO.create("TO DELETE");
        int lastID = newSubject.getId();
        subjectDAO.delete(lastID);
        Subject removedSubject = subjectDAO.findOne(lastID);
        assertNull(removedSubject);
    }
}
