package net.imshenik.university;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import net.imshenik.university.dao.StudentDaoPostgres;
import net.imshenik.university.dao.DaoException;
import net.imshenik.university.domain.Student;

public class StudentDaoPostgresTest {
    private StudentDaoPostgres studentDaoPostgres = new StudentDaoPostgres();
    private H2handler h2handler = H2handler.getInstance();
    private static final String TABLENAME = "STUDENTS";
    private static final String FIRSTNAME = "FIRSTNAME";
    private static final String LASTNAME = "LASTNAME";
    private static final String GROUP_ID = "GROUP_ID";
    private final static String SQL_CREATE_FILENAME = "H2StudentsDropCreate.sql";
    private final static String SQL_INSERT_FILENAME = "H2StudentsInsert.sql";

    @Before
    public void initialize() throws Exception {
        h2handler.createTable(SQL_CREATE_FILENAME);
        h2handler.insertContent(SQL_INSERT_FILENAME);
    }

    @Test
    public void studentCreateTest() throws Exception {
        Student expected = new Student("VENIAMIN", "LEVASHOV", 5);
        int before = H2handler.getNumberOfRows(TABLENAME);
        Student actual = studentDaoPostgres.create(expected);
        int after = H2handler.getNumberOfRows(TABLENAME);
        assertEquals(after - before, 1);
        assertTrue(equals(expected, actual));
    }

    @Test
    public void studentFindOneTest() throws Exception {
        Student[] students = { new Student(1, "ANDREY", "IMSHENIK", 1), new Student(2, "IVAN", "STEPANOV", 2),
                new Student(3, "GEORGY", "TEPLOV", 3), new Student(4, "MARY", "LE", 4),
                new Student(5, "ELENA", "YAKOVLEVA", 5) };
        for (int i = 0; i < students.length; i++) {
            Student expected = students[i];
            Student actual = studentDaoPostgres.findOne(i + 1);
            assertTrue(equals(expected, actual));
        }
    }

    @Test
    public void studentFindAllTest() throws Exception {
        List<Student> students = studentDaoPostgres.findAll();
        assertTrue(students.size() == 5);
        for (Student student : students) {
            assertTrue(exist(student.getId()));
        }
    }

    @Test
    public void studentUpdateTest() throws Exception {
        Student[] students = { new Student(1, "ANDREY", "IMSHENIK", 2), new Student(2, "IVAN", "STEPANKIN", 2),
                new Student(3, "GEORG", "TEPLOV", 3), new Student(4, "MARY", "LE", 100),
                new Student(5, "ELENA", "IVANOVA", 5) };
        for (int i = 0; i < students.length; i++) {
            studentDaoPostgres.update(students[i]);
        }
        assertEquals((int) H2handler.getField(TABLENAME, GROUP_ID, 1), 2);
        assertEquals((String) H2handler.getField(TABLENAME, LASTNAME, 2), "STEPANKIN");
        assertEquals((String) H2handler.getField(TABLENAME, FIRSTNAME, 3), "GEORG");
        assertEquals((int) H2handler.getField(TABLENAME, GROUP_ID, 4), 100);
        assertEquals((String) H2handler.getField(TABLENAME, LASTNAME, 5), "IVANOVA");
    }

    @Test(expected = DaoException.class)
    public void studentUpdateExceptionTest() throws Exception {
        int notExistID = 1_000;
        Student student = new Student();
        student.setId(notExistID);
        studentDaoPostgres.update(student);
    }

    @Test
    public void studentDeleteTest() throws Exception {
        for (int i = 1; i < 6; i++) {
            studentDaoPostgres.delete(i);
            assertFalse(exist(i));
        }
    }

    private boolean exist(Integer id) throws DaoException {
        return H2handler.exist(TABLENAME, id);
    }

    private boolean equals(Student expected, Student actual) {
        if (expected.getId() == actual.getId() && expected.getFirstName().equals(actual.getFirstName())
                && expected.getLastName().equals(actual.getLastName())
                && expected.getGroupId() == actual.getGroupId()) {
            return true;
        }
        return false;
    }
}
