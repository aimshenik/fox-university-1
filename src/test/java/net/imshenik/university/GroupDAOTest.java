package net.imshenik.university;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.util.List;
import org.junit.Test;
import net.imshenik.university.dao.DAOException;
import net.imshenik.university.dao.GroupDAO;
import net.imshenik.university.domain.Group;

public class GroupDAOTest {
    GroupDAO groupDAO = null;
    
    public GroupDAOTest() throws DAOException {
        groupDAO = new GroupDAO();
    }
    
    @Test
    public void findAllTest() throws DAOException {
        List<Group> groups = null;
        groups = groupDAO.findAll();
        assertNotNull(groups);
    }
    
    @Test
    public void findOneTest() throws DAOException {
        Group group = groupDAO.findOne(Integer.MAX_VALUE);
        assertNull(group);
        Group group2 = groupDAO.findOne(101);
        assertNotNull(group2);
        Group group3 = groupDAO.findOne(Integer.MIN_VALUE);
        assertNull(group3);
    }
    
    @Test
    public void createTest() throws DAOException {
        Group group = groupDAO.create("1-K-9000");
        assertNotNull(group);
    }
    
    @Test
    public void updateTest() throws DAOException {
        Group group = groupDAO.create("3-DDD-200");
        groupDAO.update(group.getId(), "NEW" + group.getName());
    }
    
    @Test
    public void deleteTest() throws DAOException {
        Group newGroup = groupDAO.create("TO_DELETE");
        int lastID = newGroup.getId();
        groupDAO.delete(lastID);
        Group removedGroup = groupDAO.findOne(lastID);
        assertNull(removedGroup);
    }
}
