package net.imshenik.university;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
        assertNotNull(groups);
    }
    
    @Test
    public void findOneTest() throws DaoException {
        Group group = groupDaoPostgres.findOne(Integer.MAX_VALUE);
        assertNull(group);
        Group group2 = groupDaoPostgres.findOne(101);
        assertNotNull(group2);
        Group group3 = groupDaoPostgres.findOne(Integer.MIN_VALUE);
        assertNull(group3);
    }
    
    @Test
    public void createTest() throws DaoException {
        Group group = groupDaoPostgres.create(new Group(0, "1-K-9000"));
        assertNotNull(group);
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
