package net.imshenik.university;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.Test;
import net.imshenik.university.dao.DaoException;
import net.imshenik.university.dao.GroupDaoPostgres;
import net.imshenik.university.domain.Group;

public class GroupDaoTest {
    GroupDaoPostgres groupDaoPostgres = null;
    
    public GroupDaoTest() throws DaoException {
        groupDaoPostgres = new GroupDaoPostgres();
    }
    
    @Test
    public void findAllTest() throws DaoException {
        List<Group> groups = null;
        groups = groupDaoPostgres.findAll();
        assertTrue(groups.size() > 0);
    }
    
    @Test
    public void findOneTest() throws DaoException {
        Group group = groupDaoPostgres.findOne(Integer.MAX_VALUE);
        assertNull(group);
        Group group2 = groupDaoPostgres.findOne(101);
        assertNotNull(group2);
    }
    
    @Test
    public void createTest() throws DaoException {
        Group group = groupDaoPostgres.create(new Group(0, "1-K-9000"));
        assertTrue(group.getId() != 0);
    }
    
    @Test
    public void updateTest() throws DaoException {
        Group group = groupDaoPostgres.create(new Group(0, "1-K-eq0"));
        group.setName("UPDATED");
        groupDaoPostgres.update(group);
    }
    
    @Test
    public void deleteTest() throws DaoException {
        Group newGroup = groupDaoPostgres.create(new Group(0, "1-"));
        int lastID = newGroup.getId();
        groupDaoPostgres.delete(lastID);
        Group removedGroup = groupDaoPostgres.findOne(lastID);
        assertNull(removedGroup);
    }
}
