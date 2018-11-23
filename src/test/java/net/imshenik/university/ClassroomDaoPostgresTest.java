package net.imshenik.university;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import net.imshenik.university.dao.ClassroomDaoPostgres;
import net.imshenik.university.dao.ConnectionFactory;
import net.imshenik.university.dao.DaoException;
import net.imshenik.university.domain.Classroom;

public class ClassroomDaoPostgresTest {
	private ClassroomDaoPostgres classroomDaoPostgres = new ClassroomDaoPostgres();
	private H2handler h2handler = H2handler.getInstance();
	private final static String tablename = "CLASSROOMS";

	@Test
	public void classroomCreateTest() throws Exception {
		Classroom expected = new Classroom("22", "d", 50);
		int before = H2handler.getNumberOfRows(tablename);
		Classroom actual = classroomDaoPostgres.create(expected);
		int after = H2handler.getNumberOfRows(tablename);
		assertEquals(after - before, 1);
		assertTrue(compare(expected, actual));
	}

	@Test
	public void classroomFindOneTest() throws Exception {
		Classroom[] etalon = { new Classroom(1, "106", "23", 30), new Classroom(2, "211", "23", 50),
				new Classroom(3, "321", "23", 30), new Classroom(4, "401", "24", 100),
				new Classroom(5, "305", "24", 30) };
		for (int i = 0; i < etalon.length; i++) {
			Classroom expected = etalon[i];
			Classroom actual = classroomDaoPostgres.findOne(i + 1);
			assertTrue(compare(expected, actual));
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
		Classroom[] updates = { new Classroom(1, "106", "13", 30), new Classroom(2, "989", "23", 150),
				new Classroom(3, "321", "23a", 320), new Classroom(4, "401", "24zxc", 100),
				new Classroom(5, "3a05", "24", 30) };
		for (int i = 0; i < updates.length; i++) {
			classroomDaoPostgres.update(updates[i]);
		}
		String sql = "SELECT * FROM CLASSROOMS ORDER BY ID ASC;";
		Classroom expected, actual;
		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.execute();
			try (ResultSet resultSet = statement.getResultSet()) {
				for (int i = 0; i < updates.length; i++) {
					resultSet.next();
					expected = updates[i];
					actual = new Classroom(resultSet.getInt("ID"), resultSet.getString("NUMBER"),
							resultSet.getString("BUILDING"), resultSet.getInt("CAPACITY"));
					assertTrue(compare(expected, actual));
				}
			}
		} catch (SQLException e) {
		}
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
		h2handler.initialize();
	}

	private boolean exist(Integer id) throws DaoException {
		return H2handler.exist(tablename, id);
	}

	private boolean compare(Classroom expected, Classroom actual) {
		if (expected.getId() == actual.getId() && expected.getNumber().equals(actual.getNumber())
				&& expected.getBuilding().equals(actual.getBuilding())
				&& expected.getCapacity() == actual.getCapacity()) {
			return true;
		} else {
			return false;
		}
	}

}
