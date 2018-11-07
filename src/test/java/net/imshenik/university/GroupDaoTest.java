package net.imshenik.university;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import net.imshenik.university.dao.GroupDaoPostgres;
import net.imshenik.university.dao.DaoException;
import net.imshenik.university.domain.Group;

public class GroupDaoTest {
	GroupDaoPostgres GroupDaoPostgres = new GroupDaoPostgres();

	@Test
	public void GroupCrudTest() throws DaoException {
		H2handler.createTable("H2GroupsCreation.sql");
		Group Group = new Group("1-D-11");
		// create
		assertTrue(GroupDaoPostgres.create(Group).equals(Group));
		// findOne
		assertTrue(GroupDaoPostgres.findOne(Group.getId()).equals(Group));
		// findAll
		List<Group> Groups = GroupDaoPostgres.findAll();
		assertTrue(Groups.size() > 0);
		// update
		Group.setName("1-2-3-4-5");
		GroupDaoPostgres.update(Group);
		assertTrue(GroupDaoPostgres.findOne(Group.getId()).equals(Group));
		// delete
		GroupDaoPostgres.delete(Group.getId());
		assertNull(GroupDaoPostgres.findOne(Group.getId()));
	}
}
