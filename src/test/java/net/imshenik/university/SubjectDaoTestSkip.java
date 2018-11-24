package net.imshenik.university;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.Test;
import net.imshenik.university.dao.DaoException;
import net.imshenik.university.dao.SubjectDaoPostgres;
import net.imshenik.university.domain.Subject;

public class SubjectDaoTestSkip {
    SubjectDaoPostgres subjectDaoPostgres = null;
    
    public SubjectDaoTestSkip() throws DaoException {
        subjectDaoPostgres = new SubjectDaoPostgres();
    }
    
    @Test
    public void findAllTest() throws DaoException {
        List<Subject> subjects = null;
        subjects = subjectDaoPostgres.findAll();
        assertTrue(subjects.size() > 0);
    }
    
    @Test
    public void findOneTest() throws DaoException {
        Subject subject = subjectDaoPostgres.findOne(Integer.MAX_VALUE);
        assertNull(subject);
        Subject subject2 = subjectDaoPostgres.findOne(1);
        assertNotNull(subject2);
    }
    
    @Test
    public void createTest() throws DaoException {
        Subject subject = subjectDaoPostgres.create(new Subject(0, "High Math"));
        assertTrue(subject.getId() != 0);
    }
    
    @Test
    public void updateTest() throws DaoException {
        Subject subject = subjectDaoPostgres.create(new Subject(0, "FRffdfa"));
        subject.setName("Franch");
        subjectDaoPostgres.update(subject);
    }
    
    @Test
    public void deleteTest() throws DaoException {
        Subject newSubject = subjectDaoPostgres.create(new Subject(0, "TO DELETE"));
        int lastID = newSubject.getId();
        subjectDaoPostgres.delete(lastID);
        Subject removedSubject = subjectDaoPostgres.findOne(lastID);
        assertNull(removedSubject);
    }
}
