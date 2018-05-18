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

public class ClassroomDaoPostgres implements ClassroomDao<Classroom> {

    private static final Logger log = Logger.getLogger(ClassroomDaoPostgres.class.getName());

    public List<Classroom> findAll() throws DaoException {
	log.trace("findAll() | start");
	String sql = "select * from classrooms;";
	List<Classroom> classrooms = new ArrayList<>();
	try (Connection connection = ConnectionFactory.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery()) {
	    log.trace("findAll() | Getting Classrooms from ResultSet...");
	    while (resultSet.next()) {
		classrooms.add(new Classroom(resultSet.getInt("id"), resultSet.getString("number"),
			resultSet.getString("building"), resultSet.getInt("capacity")));
	    }
	} catch (SQLException e) {
	    log.error("findAll() | database: interaction failure ", e);
	    throw new DaoException("findAll() | database: interaction failure", e);
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
		if (resultSet.next()) {
		    classroom = new Classroom(id, resultSet.getString("number"), resultSet.getString("building"),
			    resultSet.getInt("capacity"));
		}
	    }
	} catch (SQLException e) {
	    log.error("findOne() | database: interaction failure", e);
	    throw new DaoException("findOne() | database: interaction failure", e);
	}
	log.trace(classroom == null ? "findOne() | Classroom with ID = " + id + " was NOT found!"
		: "findOne() | Classroom with ID = " + id + " was found");
	log.trace("findOne() | end");
	return classroom;
    }

    public Classroom create(Classroom classroom) throws DaoException {
	log.trace("create() | start");
	String sql = "insert into classrooms (number, building, capacity) values (?,?,?);";
	Classroom createdClassroom = null;
	try (Connection connection = ConnectionFactory.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	    statement.setString(1, classroom.getNumber());
	    statement.setString(2, classroom.getBuilding());
	    statement.setInt(3, classroom.getCapacity());
	    statement.executeUpdate();
	    try (ResultSet resultSet = statement.getGeneratedKeys()) {
		if (resultSet.next()) {
		    createdClassroom = new Classroom(resultSet.getInt("id"), resultSet.getString("number"),
			    resultSet.getString("building"), resultSet.getInt("capacity"));
		}
	    }
	} catch (SQLException e) {
	    log.error("create() | database: interaction failure", e);
	    throw new DaoException("create() | database: interaction failure", e);
	}
	if (createdClassroom == null) {
	    log.error("create() | Classroom was NOT created!");
	} else {
	    log.info("create() | Classroom was created | " + createdClassroom.toString());
	}
	log.trace("create() | end");
	return createdClassroom;
    }

    public void update(Classroom classroom) throws DaoException {
	log.trace("update() | start");
	String sql = "update classrooms set number=?,building=?, capacity=? where id=?;";
	try (Connection connection = ConnectionFactory.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql)) {
	    statement.setString(1, classroom.getNumber());
	    statement.setString(2, classroom.getBuilding());
	    statement.setInt(3, classroom.getCapacity());
	    statement.setInt(4, classroom.getId());
	    int rowsUpdated = statement.executeUpdate();
	    if (rowsUpdated == 0) {
		log.error("update() | Classroom with ID =  " + classroom.getId() + " was NOT updated!");
	    } else {
		log.info("update() | Classroom with ID =  " + classroom.getId() + " was updated");
	    }
	} catch (SQLException e) {
	    log.error("update() | database: interaction failure", e);
	    throw new DaoException("update() | database: interaction failure", e);
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
		log.error("delete() | Classroom with  ID = " + id + " was NOT deleted!");
	    } else {
		log.info("delete() | Classroom with  ID = " + id + " was deleted");
	    }
	} catch (SQLException e) {
	    log.error("delete() | database: interaction failure", e);
	    throw new DaoException("delete() | database: interaction failure", e);
	}
	log.trace("delete() | end");
    }
}
