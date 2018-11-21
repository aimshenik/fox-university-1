package net.imshenik.university;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import net.imshenik.university.dao.ClassroomDaoPostgres;
import net.imshenik.university.dao.ConnectionFactory;
import net.imshenik.university.dao.DaoException;
import net.imshenik.university.domain.Classroom;

public class ClassroomDaoPostgresTest {
	private static final Logger log = Logger.getLogger(ClassroomDaoPostgresTest.class.getName());
	private ClassroomDaoPostgres classroomDaoPostgres = new ClassroomDaoPostgres();
	private H2handler h2handler = H2handler.getInstance();

	@Test
	public void classroomCreateTest() throws DaoException {
		h2handler.initializeIfRequired();
		Classroom initialClassroom = new Classroom("22", "d", 50);
		String sql = "SELECT COUNT(*) FROM CLASSROOMS;";
		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			int before, after = 0;
			try (ResultSet resultSet = statement.executeQuery()) {
				resultSet.next();
				before = resultSet.getInt(1);
			}
			Classroom createdClassroom = classroomDaoPostgres.create(initialClassroom);
			try (ResultSet resultSet = statement.executeQuery()) {
				resultSet.next();
				after = resultSet.getInt(1);
			}
			assertTrue(after - before == 1);
			assertNotNull(createdClassroom);
			assertEquals(initialClassroom, createdClassroom);
			assertEquals(initialClassroom.getId(), createdClassroom.getId());
			assertEquals(initialClassroom.getNumber(), createdClassroom.getNumber());
			assertEquals(initialClassroom.getBuilding(), createdClassroom.getBuilding());
			assertEquals(initialClassroom.getCapacity(), createdClassroom.getCapacity());
		} catch (SQLException e) {
			log.error("classroomCreateTest() | database: interaction failure", e);
			throw new DaoException("classroomCreateTest() | database: interaction failure", e);
		}
		log.info("classroomCreateTest() | PASSED");
	}

	@Test
	public void classroomFindOneTest() throws DaoException {
		h2handler.initializeIfRequired();
		for (int i = 1; i < 6; i++) {
			Classroom classroom = classroomDaoPostgres.findOne(i);
			assertTrue(classroom.getId() == i);
		}
		log.info("classroomFindOneTest() | PASSED");
		log.trace("classroomFindOneTest() | end");
	}

	@Test
	public void classroomFindAllTest() throws DaoException {
		log.trace("classroomFindAllTest() | start");
		h2handler.initializeIfRequired();
		List<Classroom> classrooms = classroomDaoPostgres.findAll();
		String sql = "SELECT COUNT(*) FROM CLASSROOMS;";
		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			try (ResultSet resultSet = statement.executeQuery()) {
				resultSet.next();
				int count = resultSet.getInt(1);
				assertTrue(classrooms.size() == count);
			}

		} catch (SQLException e) {
			log.error("classroomFindAllTest() | database: interaction failure", e);
			throw new DaoException("classroomFindAllTest() | database: interaction failure", e);
		}
		for (Classroom room : classrooms) {
			assertTrue(exist(room.getId()));
		}
		log.info("classroomFindAllTest() | PASSED");
	}

	@Test
	public void classroomUpdateTest() throws DaoException {
		h2handler.initializeIfRequired();
		Classroom classroom = new Classroom("555", "5a", 30);
		String sql = "INSERT INTO CLASSROOMS (NUMBER, BUILDING, CAPACITY) VALUES (?,?,?);";
		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, classroom.getNumber());
			statement.setString(2, classroom.getBuilding());
			statement.setInt(3, classroom.getCapacity());
			if (statement.executeUpdate() == 1) {
				try (ResultSet resultSet = statement.getGeneratedKeys()) {
					resultSet.next();
					classroom.setId(resultSet.getInt("ID"));
				}
			}

		} catch (SQLException e) {
			log.error("classroomUpdateTest() | database: interaction failure", e);
			throw new DaoException("classroomUpdateTest() | database: interaction failure", e);
		}
		classroom.setCapacity(500);
		classroom.setBuilding("666");
		classroom.setBuilding("6b");
		classroomDaoPostgres.update(classroom);
		sql = "SELECT * FROM CLASSROOMS WHERE ID = ?;";
		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, classroom.getId());
			statement.execute();
			try (ResultSet resultSet = statement.getResultSet()) {
				resultSet.next();
				assertEquals(classroom.getNumber(), resultSet.getString("NUMBER"));
				assertEquals(classroom.getBuilding(), resultSet.getString("BUILDING"));
				assertEquals(classroom.getCapacity(), resultSet.getInt("CAPACITY"));
			}
		} catch (SQLException e) {
			log.error("classroomUpdateTest() | database: interaction failure", e);
			throw new DaoException("classroomUpdateTest() | database: interaction failure", e);
		}
		classroom.setId(Integer.MIN_VALUE);
		log.info("classroomUpdateTest() | PASSED");
	}

	@Test(expected = DaoException.class)
	public void classroomUpdateExceptionTest() throws DaoException {
		h2handler.initializeIfRequired();
		Classroom classroom = new Classroom();
		classroom.setId(Integer.MIN_VALUE);
		log.info("classroomUpdateExceptionTest() | PASSED");
		classroomDaoPostgres.update(classroom);
	}

	@Test
	public void classroomDeleteTest() throws DaoException {
		h2handler.initializeIfRequired();
		for (int i = 1; i < 6; i++) {
			classroomDaoPostgres.delete(i);
			assertFalse(exist(i));
		}
		log.info("classroomDeleteTest() | PASSED");
	}

	private boolean exist(Integer id) throws DaoException {
		if (id == null) {
			return false;
		}
		boolean found = false;
		String sql = "SELECT EXISTS(SELECT 1 FROM CLASSROOMS WHERE ID=?);";
		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				resultSet.next();
				if (resultSet.getBoolean(1) == true) {
					found = true;
				}
			}
		} catch (SQLException e) {
			throw new DaoException("exist() | database: interaction failure", e);
		}
		return found;
	}
}
