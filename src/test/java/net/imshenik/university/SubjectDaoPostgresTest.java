package net.imshenik.university;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import net.imshenik.university.dao.SubjectDaoPostgres;
import net.imshenik.university.dao.DaoException;
import net.imshenik.university.domain.Subject;

public class SubjectDaoPostgresTest {
    private SubjectDaoPostgres subjectDaoPostgres = new SubjectDaoPostgres();
    private H2handler h2handler = H2handler.getInstance();
    private final static String TABLENAME = "SUBJECTS";
    private final static String NAME = "NAME";
    private final static String SQL_CREATE_FILENAME = "H2SubjectsDropCreate.sql";
    private final static String SQL_INSERT_FILENAME = "H2SubjectsInsert.sql";

    @Before
    public void initialize() throws Exception {
        h2handler.createTable(SQL_CREATE_FILENAME);
        h2handler.insertContent(SQL_INSERT_FILENAME);
    }

    @Test
    public void subjectCreateTest() throws Exception {
        Subject expected = new Subject("H2Database ДЛЯ ЧАЙНИКОВ");
        int before = H2handler.getNumberOfRows(TABLENAME);
        Subject actual = subjectDaoPostgres.create(expected);
        int after = H2handler.getNumberOfRows(TABLENAME);
        assertEquals(after - before, 1);
        assertTrue(equals(expected, actual));
    }

    @Test
    public void subjectFindOneTest() throws Exception {
        Subject[] subjects = { new Subject(1, "Heat engineering and heat transfer basics"),
                new Subject(2, "Metrology, standardization and certification"),
                new Subject(3, "Safety of vital functions"), new Subject(4, "Basics of construction"),
                new Subject(5, "Foundry technology") };
        for (int i = 0; i < subjects.length; i++) {
            Subject expected = subjects[i];
            Subject actual = subjectDaoPostgres.findOne(i + 1);
            assertTrue(equals(expected, actual));
        }
    }

    @Test
    public void subjectFindAllTest() throws Exception {
        List<Subject> subjects = subjectDaoPostgres.findAll();
        assertTrue(subjects.size() == 5);
        for (Subject subject : subjects) {
            assertTrue(exist(subject.getId()));
        }
    }

    @Test
    public void subjectUpdateTest() throws Exception {
        Subject[] subjects = { new Subject(1, "Engineering"),
                new Subject(2, "Metrology"),
                new Subject(3, "Safety"), new Subject(4, "Construction"),
                new Subject(5, "Technology") };
        for (int i = 0; i < subjects.length; i++) {
            subjectDaoPostgres.update(subjects[i]);
        }
        assertEquals((String) H2handler.getField(TABLENAME, NAME, 1), "Engineering");
        assertEquals((String) H2handler.getField(TABLENAME, NAME, 2), "Metrology");
        assertEquals((String) H2handler.getField(TABLENAME, NAME, 3), "Safety");
        assertEquals((String) H2handler.getField(TABLENAME, NAME, 4), "Construction");
        assertEquals((String) H2handler.getField(TABLENAME, NAME, 5), "Technology");
    }

    @Test(expected = DaoException.class)
    public void subjectUpdateExceptionTest() throws Exception {
        int notExistID = 1_000;
        Subject subject = new Subject();
        subject.setId(notExistID);
        subjectDaoPostgres.update(subject);
    }

    @Test
    public void subjectDeleteTest() throws Exception {
        for (int i = 1; i < 6; i++) {
            subjectDaoPostgres.delete(i);
            assertFalse(exist(i));
        }
    }

    private boolean exist(Integer id) throws DaoException {
        return H2handler.exist(TABLENAME, id);
    }

    private boolean equals(Subject expected, Subject actual) {
        if (expected.getId() == actual.getId() && expected.getName().equals(actual.getName())) {
            return true;
        }
        return false;
    }
}
