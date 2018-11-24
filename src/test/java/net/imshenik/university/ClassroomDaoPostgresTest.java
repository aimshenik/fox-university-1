package net.imshenik.university;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import net.imshenik.university.dao.ClassroomDaoPostgres;
import net.imshenik.university.dao.DaoException;
import net.imshenik.university.domain.Classroom;

public class ClassroomDaoPostgresTest {
    private ClassroomDaoPostgres classroomDaoPostgres = new ClassroomDaoPostgres();
    private H2handler h2handler = H2handler.getInstance();
    private final static String TABLENAME = "CLASSROOMS";
    private final static String NUMBER = "NUMBER";
    private final static String BUILDING = "BUILDING";
    private final static String CAPACITY = "CAPACITY";
    private final static String SQL_CREATE_FILENAME = "H2ClassroomsDropCreate.sql";
    private final static String SQL_INSERT_FILENAME = "H2ClassroomsInsert.sql";

    @Test
    public void classroomCreateTest() throws Exception {
        Classroom expected = new Classroom("22", "d", 50);
        int before = H2handler.getNumberOfRows(TABLENAME);
        Classroom actual = classroomDaoPostgres.create(expected);
        int after = H2handler.getNumberOfRows(TABLENAME);
        assertEquals(after - before, 1);
        assertTrue(equals(expected, actual));
    }

    @Test
    public void classroomFindOneTest() throws Exception {
        Classroom[] classrooms = { new Classroom(1, "106", "23", 30), new Classroom(2, "211", "23", 50),
                new Classroom(3, "321", "23", 30), new Classroom(4, "401", "24", 100),
                new Classroom(5, "305", "24", 30) };
        for (int i = 0; i < classrooms.length; i++) {
            Classroom expected = classrooms[i];
            Classroom actual = classroomDaoPostgres.findOne(i + 1);
            assertTrue(equals(expected, actual));
        }
    }

    @Test
    public void classroomFindAllTest() throws Exception {
        List<Classroom> classrooms = classroomDaoPostgres.findAll();
        assertTrue(classrooms.size() == 5);
        for (Classroom room : classrooms) {
            assertTrue(exist(room.getId()));
        }
    }

    @Test
    public void classroomUpdateTest() throws Exception {
        Classroom[] classrooms = { new Classroom(1, "106", "13", 30), new Classroom(2, "989", "23", 150),
                new Classroom(3, "321", "23a", 320), new Classroom(4, "405", "24zxc", 100),
                new Classroom(5, "305", "24", 35) };
        for (int i = 0; i < classrooms.length; i++) {
            classroomDaoPostgres.update(classrooms[i]);
        }
        assertEquals((String) H2handler.getField(TABLENAME, BUILDING, 1), "13");
        assertEquals((int) H2handler.getField(TABLENAME, CAPACITY, 2), 150);
        assertEquals((String) H2handler.getField(TABLENAME, BUILDING, 3), "23a");
        assertEquals((String) H2handler.getField(TABLENAME, NUMBER, 4), "405");
        assertEquals((int) H2handler.getField(TABLENAME, CAPACITY, 5), 35);
    }

    @Test(expected = DaoException.class)
    public void classroomUpdateExceptionTest() throws Exception {
        int notExistID = 1_000;
        Classroom classroom = new Classroom();
        classroom.setId(notExistID);
        classroomDaoPostgres.update(classroom);
    }

    @Test
    public void classroomDeleteTest() throws Exception {
        for (int i = 1; i < 6; i++) {
            classroomDaoPostgres.delete(i);
            assertFalse(exist(i));
        }
    }

    @Before
    public void initialize() throws Exception {
        h2handler.createTable(SQL_CREATE_FILENAME);
        h2handler.insertContent(SQL_INSERT_FILENAME);
    }

    private boolean exist(Integer id) throws DaoException {
        return H2handler.exist(TABLENAME, id);
    }

    private boolean equals(Classroom expected, Classroom actual) {
        if (expected.getId() == actual.getId() && expected.getNumber().equals(actual.getNumber())
                && expected.getBuilding().equals(actual.getBuilding())
                && expected.getCapacity() == actual.getCapacity()) {
            return true;
        }
        return false;
    }
}
