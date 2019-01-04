package net.imshenik.university;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import net.imshenik.university.dao.GroupDaoPostgres;
import net.imshenik.university.dao.DaoException;
import net.imshenik.university.domain.Group;

public class GroupDaoPostgresTest {
    private GroupDaoPostgres groupDaoPostgres = new GroupDaoPostgres();
    private H2handler h2handler = H2handler.getInstance();
    private final static String TABLENAME = "GROUPS";
    private final static String NAME = "NAME";
    private final static String SQL_CREATE_FILENAME = "H2GroupsDropCreate.sql";
    private final static String SQL_INSERT_FILENAME = "H2GroupsInsert.sql";

    @Before
    public void initialize() throws Exception {
        h2handler.createTable(SQL_CREATE_FILENAME);
        h2handler.insertContent(SQL_INSERT_FILENAME);
    }

    @Test
    public void groupCreateTest() throws Exception {
        Group expected = new Group("3-VD-45");
        int before = H2handler.getNumberOfRows(TABLENAME);
        Group actual = groupDaoPostgres.create(expected);
        int after = H2handler.getNumberOfRows(TABLENAME);
        assertEquals(after - before, 1);
        assertTrue(equals(expected, actual));
    }

    @Test
    public void groupFindOneTest() throws Exception {
        Group[] groups = { new Group(1, "1-D-31"), new Group(2, "1-L-32"), new Group(3, "2-M-43"),
                new Group(4, "3-F-64"), new Group(5, "3-SK-90") };
        for (int i = 0; i < groups.length; i++) {
            Group expected = groups[i];
            Group actual = groupDaoPostgres.findOne(i + 1);
            assertTrue(equals(expected, actual));
        }
    }

    @Test
    public void groupFindAllTest() throws Exception {
        List<Group> groups = groupDaoPostgres.findAll();
        assertTrue(groups.size() == 5);
        for (Group group : groups) {
            assertTrue(exist(group.getId()));
        }
    }

    @Test
    public void groupUpdateTest() throws Exception {
        Group[] groups = { new Group(1, "Z1-D-31"), new Group(2, "X1-L-32"), new Group(3, "C2-M-43"),
                new Group(4, "V3-F-64"), new Group(5, "B3-SK-90") };
        for (int i = 0; i < groups.length; i++) {
            groupDaoPostgres.update(groups[i]);
        }
        assertEquals((String) H2handler.getField(TABLENAME, NAME, 1), "Z1-D-31");
        assertEquals((String) H2handler.getField(TABLENAME, NAME, 2), "X1-L-32");
        assertEquals((String) H2handler.getField(TABLENAME, NAME, 3), "C2-M-43");
        assertEquals((String) H2handler.getField(TABLENAME, NAME, 4), "V3-F-64");
        assertEquals((String) H2handler.getField(TABLENAME, NAME, 5), "B3-SK-90");
    }

    @Test(expected = DaoException.class)
    public void groupUpdateExceptionTest() throws Exception {
        int notExistID = 1_000;
        Group Group = new Group();
        Group.setId(notExistID);
        groupDaoPostgres.update(Group);
    }

    @Test
    public void groupDeleteTest() throws Exception {
        for (int i = 1; i < 6; i++) {
            groupDaoPostgres.delete(i);
            assertFalse(exist(i));
        }
    }

    private boolean exist(Integer id) throws DaoException {
        return H2handler.exist(TABLENAME, id);
    }

    private boolean equals(Group expected, Group actual) {
        if (expected.getId() == actual.getId() && expected.getName().equals(actual.getName())) {
            return true;
        }
        return false;
    }
}
