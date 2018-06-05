package net.imshenik.university;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.util.List;
import org.junit.Test;
import net.imshenik.university.dao.DaoException;
import net.imshenik.university.dao.SubjectDaoPostgres;
import net.imshenik.university.domain.Subject;

public class SubjectDaoTest {
    SubjectDaoPostgres subjectDaoPostgres = null;
    
    public SubjectDaoTest() throws DaoException {
        subjectDaoPostgres = new SubjectDaoPostgres();
    }
    
    @Test
    public void findAllTest() throws DaoException {
        List<Subject> subjects = null;
        subjects = subjectDaoPostgres.findAll();
        assertNotNull(subjects);
    }
    
    @Test
    public void findOneTest() throws DaoException {
        Subject subject = subjectDaoPostgres.findOne(Integer.MAX_VALUE);
        assertNull(subject);
        Subject Subject2 = subjectDaoPostgres.findOne(1);
        assertNotNull(Subject2);
        Subject Subject3 = subjectDaoPostgres.findOne(Integer.MIN_VALUE);
        assertNull(Subject3);
    }
    
    @Test
    public void createTest() throws DaoException {
        Subject Subject = subjectDaoPostgres.create(new Subject(0, "High Math"));
        assertNotNull(Subject);
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
