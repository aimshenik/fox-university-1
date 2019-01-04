package net.imshenik.university;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import net.imshenik.university.dao.TeacherDaoPostgres;
import net.imshenik.university.dao.DaoException;
import net.imshenik.university.domain.Teacher;

public class TeacherDaoPostgresTest {
    private TeacherDaoPostgres teacherDaoPostgres = new TeacherDaoPostgres();
    private H2handler h2handler = H2handler.getInstance();
    private static final String TABLENAME = "TEACHERS";
    private static final String FIRSTNAME = "FIRSTNAME";
    private static final String LASTNAME = "LASTNAME";
    private static final String PASSPORT = "PASSPORT";
    private final static String SQL_CREATE_FILENAME = "H2TeachersDropCreate.sql";
    private final static String SQL_INSERT_FILENAME = "H2TeachersInsert.sql";

    @Before
    public void initialize() throws Exception {
        h2handler.createTable(SQL_CREATE_FILENAME);
        h2handler.insertContent(SQL_INSERT_FILENAME);
    }

    @Test
    public void teacherCreateTest() throws Exception {
        Teacher expected = new Teacher("ANDREY", "FEDOTOV", "4564-9982801");
        int before = H2handler.getNumberOfRows(TABLENAME);
        Teacher actual = teacherDaoPostgres.create(expected);
        int after = H2handler.getNumberOfRows(TABLENAME);
        assertEquals(after - before, 1);
        assertTrue(equals(expected, actual));
    }

    @Test
    public void teacherFindOneTest() throws Exception {
        Teacher[] teachers = { new Teacher(1,"ANDREY FEDOROVICH", "SMIKOV", "AA-11111"),
                new Teacher(2,"VIKTOR SERGEEVICH", "MOISEEV", "AA-22222"),
                new Teacher(3,"DMITRY VASILYEVICH", "BEREZHNOY", "AA-33333"),
                new Teacher(4,"BORIS LEONIDOVICH", "BOBRYSHEV", "AA-44444"),
                new Teacher(5,"OLGA VALENTINOVNA", "TELITSYNA", "AA-55555") };
        for (int i = 0; i < teachers.length; i++) {
            Teacher expected = teachers[i];
            Teacher actual = teacherDaoPostgres.findOne(i + 1);
            assertTrue(equals(expected, actual));
        }
    }

    @Test
    public void teacherFindAllTest() throws Exception {
        List<Teacher> teachers = teacherDaoPostgres.findAll();
        assertTrue(teachers.size() == 5);
        for (Teacher teacher : teachers) {
            assertTrue(exist(teacher.getId()));
        }
    }

    @Test
    public void TeacherUpdateTest() throws Exception {
        Teacher[] teachers = { new Teacher(1,"ANDREY FEDOROVICH", "SMYKOV", "AA-11111"),
                new Teacher(2,"VIKTOR SERGEEVICH", "MOISEEV", "BB-22222"),
                new Teacher(3,"DMITRIY VASILYEVICH", "BEREZHNOY", "AA-33333"),
                new Teacher(4,"BORIS LEONIDOVICH", "BOBRYSHEV", "CC-44444"),
                new Teacher(5,"OLGA VALENTINOVNA", "TELITSINA", "AA-55555") };
        for (int i = 0; i < teachers.length; i++) {
            teacherDaoPostgres.update(teachers[i]);
        }
        assertEquals((String) H2handler.getField(TABLENAME, LASTNAME, 1), "SMYKOV");
        assertEquals((String) H2handler.getField(TABLENAME, PASSPORT, 2), "BB-22222");
        assertEquals((String) H2handler.getField(TABLENAME, FIRSTNAME, 3), "DMITRIY VASILYEVICH");
        assertEquals((String) H2handler.getField(TABLENAME, PASSPORT, 4), "CC-44444");
        assertEquals((String) H2handler.getField(TABLENAME, LASTNAME, 5), "TELITSINA");
    }

    @Test(expected = DaoException.class)
    public void teacherUpdateExceptionTest() throws Exception {
        int notExistID = 1_000;
        Teacher teacher = new Teacher();
        teacher.setId(notExistID);
        teacherDaoPostgres.update(teacher);
    }

    @Test
    public void teacherDeleteTest() throws Exception {
        for (int i = 1; i < 6; i++) {
            teacherDaoPostgres.delete(i);
            assertFalse(exist(i));
        }
    }

    private boolean exist(Integer id) throws DaoException {
        return H2handler.exist(TABLENAME, id);
    }

    private boolean equals(Teacher expected, Teacher actual) {
        if (expected.getId() == actual.getId() && expected.getFirstName().equals(actual.getFirstName())
                && expected.getLastName().equals(actual.getLastName())
                && expected.getPassport().equals(actual.getPassport())) {
            return true;
        }
        return false;
    }
}
