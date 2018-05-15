package net.imshenik.university;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.util.List;
import org.junit.Test;
import net.imshenik.university.dao.DaoException;
import net.imshenik.university.dao.GroupDao;
import net.imshenik.university.domain.Group;

public class GroupDaoTest {
    GroupDao groupDAO = null;
    
    public GroupDaoTest() throws DaoException {
        groupDAO = new GroupDao();
    }
    
    @Test
    public void findAllTest() throws DaoException {
        List<Group> groups = null;
        groups = groupDAO.findAll();
        assertNotNull(groups);
    }
    
    @Test
    public void findOneTest() throws DaoException {
        Group group = groupDAO.findOne(Integer.MAX_VALUE);
        assertNull(group);
        Group group2 = groupDAO.findOne(101);
        assertNotNull(group2);
        Group group3 = groupDAO.findOne(Integer.MIN_VALUE);
        assertNull(group3);
    }
    
    @Test
    public void createTest() throws DaoException {
        Group group = groupDAO.create("1-K-9000");
        assertNotNull(group);
    }
    
    @Test
    public void updateTest() throws DaoException {
        Group group = groupDAO.create("3-DDD-200");
        groupDAO.update(group.getId(), "NEW" + group.getName());
    }
    
    @Test
    public void deleteTest() throws DaoException {
        Group newGroup = groupDAO.create("TO_DELETE");
        int lastID = newGroup.getId();
        groupDAO.delete(lastID);
        Group removedGroup = groupDAO.findOne(lastID);
        assertNull(removedGroup);
    }
}
