package net.imshenik.university.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import net.imshenik.university.domain.Classroom;

public class ClassroomDaoPostgres implements ClassroomDao {

    private static final Logger log = Logger.getLogger(ClassroomDaoPostgres.class.getName());

    public List<Classroom> findAll() throws DaoException {
	log.trace("findAll() | start");
	String sql = "select * from classrooms;";
	List<Classroom> classrooms = null;
	try (Connection connection = ConnectionFactory.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery()) {
	    classrooms = new ArrayList<>();
	    int id = 0;
	    String building = null;
	    String number = null;
	    int capacity = 0;
	    log.trace("findAll() | Getting Classrooms from ResultSet...");
	    while (resultSet.next()) {
		id = resultSet.getInt("id");
		building = resultSet.getString("building");
		number = resultSet.getString("number");
		capacity = resultSet.getInt("capacity");
		classrooms.add(new Classroom(id, number, building, capacity));
	    }
	} catch (SQLException e) {
	    log.error("findAll() | Database iteraction failure ", e);
	    throw new DaoException("findAll() | Database iteraction failure", e);
	}
	log.trace("findAll() | end");
	return classrooms;
    }

    public Classroom findOne(int id) throws DaoException {
	log.trace("findOne() | start");
	String sql = "select * from classrooms where id=?;";
	Classroom classroom = null;
	try (Connection connection = ConnectionFactory.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql)) {
	    statement.setInt(1, id);
	    try (ResultSet resultSet = statement.executeQuery()) {
		String building = null;
		String number = null;
		int capacity = 0;
		if (resultSet.next()) {
		    building = resultSet.getString("building");
		    number = resultSet.getString("number");
		    capacity = resultSet.getInt("capacity");
		    classroom = new Classroom(id, number, building, capacity);
		}
	    } catch (SQLException e) {
		log.error("findOne() | ResultSet iteraction failure", e);
		throw new DaoException("findOne() | ResultSet iteraction failure", e);
	    }
	} catch (SQLException e) {
	    log.error("findOne() | Database iteraction failure", e);
	    throw new DaoException("findOne() | Database iteraction failure", e);
	}
	log.info(classroom == null ? "findOne() | Classroom with ID = " + id + " was NOT found!"
		: "findOne() | Classroom with ID = " + id + " was found | " + classroom.toString());
	log.trace("findOne() | end");
	return classroom;
    }

    public Classroom create(String number, String building, int capacity) throws DaoException {
	log.trace("create() | start");
	String sql = "insert into classrooms (number, building, capacity) values (?,?,?);";
	Classroom classroom = null;
	try (Connection connection = ConnectionFactory.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	    statement.setString(1, number);
	    statement.setString(2, building);
	    statement.setInt(3, capacity);
	    statement.executeUpdate();
	    try (ResultSet resultSet = statement.getGeneratedKeys()) {
		if (resultSet.next()) {
		    int id = resultSet.getInt("id");
		    classroom = new Classroom(id, number, building, capacity);
		}
	    } catch (SQLException e) {
		log.error("create() | ResultSet iteraction failure", e);
		throw new DaoException("create() | ResultSet iteraction failure", e);
	    }
	} catch (SQLException e) {
	    log.error("create() | Database iteraction failure", e);
	    throw new DaoException("create() | Database iteraction failure", e);
	}
	if (classroom == null) {
	    log.error("create() | Classroom was NOT created!");
	} else {
	    log.info("create() | Classroom was created | " + classroom.toString());
	}
	log.trace("create() | end");
	return classroom;
    }

    public void update(int id, String number, String building, int capacity) throws DaoException {
	log.trace("update() | start");
	String sql = "update classrooms set number=?,building=?, capacity=? where id=?;";
	try (Connection connection = ConnectionFactory.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql)) {
	    statement.setString(1, number);
	    statement.setString(2, building);
	    statement.setInt(3, capacity);
	    statement.setInt(4, id);
	    int rowsUpdated = statement.executeUpdate();
	    if (rowsUpdated == 0) {
		log.error("update() | Classroom with ID =  " + id + " was NOT updated!");
	    } else {
		log.info("update() | Classroom with ID =  " + id + " was updated");
	    }
	} catch (SQLException e) {
	    log.error("update() | Unable to create SQL resourses", e);
	    throw new DaoException("update() | Unable to create SQL resourses", e);
	}
	log.trace("update() | end");
    }

    public void delete(int id) throws DaoException {
	log.trace("delete() | start");
	String sql = "delete from classrooms as c where c.id = ?;";
	try (Connection connection = ConnectionFactory.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql)) {
	    statement.setInt(1, id);
	    int rowsDeleted = statement.executeUpdate();
	    if (rowsDeleted == 0) {
		log.error("deleteEntity() | Classroom with  ID = " + id + " was NOT deleted!");
	    } else {
		log.info("deleteEntity() | Classroom with  ID = " + id + " was deleted");
	    }
	} catch (SQLException e) {
	    log.error("delete() | Unable to create SQL resourses", e);
	    throw new DaoException("delete() | Unable to create SQL resourses", e);
	}
	log.trace("delete() | end");
    }
}
