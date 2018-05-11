package net.imshenik.university;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.util.Set;
import org.junit.Test;
import net.imshenik.university.dao.postgres.SubjectDAO;
import net.imshenik.university.dao.postgres.DAOException;
import net.imshenik.university.domain.entities.Subject;

public class SubjectDAOTest {
    SubjectDAO subjectDAO = null;
    
    public SubjectDAOTest() throws DAOException {
     subjectDAO =  new SubjectDAO();
    }
    
    @Test
    public void findAllTest() throws DAOException {
        Set<Subject> subjects = null;
        subjects = subjectDAO.findAll();
        assertNotNull(subjects);
    }
    
    @Test
    public void findOneTest() throws DAOException {
        Subject subject = subjectDAO.findOne(Integer.MAX_VALUE);
        assertNull(subject);
        Subject Subject2 = subjectDAO.findOne(1);
        assertNotNull(Subject2);
        Subject Subject3 = subjectDAO.findOne(Integer.MIN_VALUE);
        assertNull(Subject3);
    }
    
    @Test
    public void createTest() throws DAOException {
        Subject Subject = subjectDAO.create("MATRIALLOVEDENIE");
        assertNotNull(Subject);
    }
    
    @Test
    public void updateTest() throws DAOException {
        Subject Subject = subjectDAO.create("SBJ4UPD");
        subjectDAO.update(Subject.getId(), "NEW" + Subject.getName());
    }
    
    @Test
    public void deleteTest() throws DAOException {
        Subject newSubject = subjectDAO.create("TO DELETE");
        int lastID = newSubject.getId();
        subjectDAO.delete(lastID);
        Subject removedSubject = subjectDAO.findOne(lastID);
        assertNull(removedSubject);
    }
}
