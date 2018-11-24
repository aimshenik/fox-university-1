package net.imshenik.university.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import net.imshenik.university.domain.Teacher;

public class TeacherDaoPostgres implements TeacherDao {
    private static final Logger log = Logger.getLogger(TeacherDaoPostgres.class.getName());

    public List<Teacher> findAll() throws DaoException {
	log.trace("findAll() | start");
	String sql = "select * from teachers";
	List<Teacher> teachers = new ArrayList<>();
	try (Connection connection = ConnectionFactory.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery()) {
	    log.trace("findAll() | Getting Teachers from ResultSet...");
	    while (resultSet.next()) {
		teachers.add(new Teacher(resultSet.getInt("id"), resultSet.getString("firstname"),
			resultSet.getString("lastname"), resultSet.getString("passport")));
	    }
	} catch (SQLException e) {
	    log.error("findAll() | database: interaction failure ", e);
	    throw new DaoException("findAll() | database: interaction failure", e);
	}
	log.trace("findAll() | end");
	return teachers;
    }

    public Teacher findOne(Integer id) throws DaoException {
	log.trace("findOne() | start");
	String sql = "select * from teachers where id=?";
	Teacher teacher = null;
	try (Connection connection = ConnectionFactory.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql)) {
	    statement.setInt(1, id);
	    statement.execute();
	    try (ResultSet resultSet = statement.getResultSet()) {
		if (resultSet.next()) {
		    teacher = new Teacher(resultSet.getInt("id"), resultSet.getString("firstname"),
			    resultSet.getString("lastname"), resultSet.getString("passport"));
		}
	    }
	} catch (SQLException e) {
	    log.error("findOne() | database: interaction failure", e);
	    throw new DaoException("findOne() | database: interaction failure", e);
	}
	log.trace(teacher == null ? "findOne() | Teacher was NOT found!" : "findOne() | Teacher was found");
	log.trace("findOne() | end");
	return teacher;
    }

    public Teacher create(Teacher teacher) throws DaoException {
	log.trace("create() | start");
	String sql = "insert into teachers (firstname, lastname, passport) values (?,?,?)";
	try (Connection connection = ConnectionFactory.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	    statement.setString(1, teacher.getFirstName());
	    statement.setString(2, teacher.getLastName());
	    statement.setString(3, teacher.getPassport());
	    if (statement.executeUpdate() == 1) {
		try (ResultSet resultSet = statement.getGeneratedKeys()) {
		    if (resultSet.next()) {
			teacher.setId(resultSet.getInt("Id"));
			log.info("create() | Teacher was created | " + teacher.toString());
		    }
		}
	    }
	} catch (SQLException e) {
	    log.error("create() | database: interaction failure", e);
	    throw new DaoException("create() | database: interaction failure", e);
	}
	log.trace("create() | end");
	return teacher;
    }

    public void update(Teacher teacher) throws DaoException {
	log.trace("update() | start");
	if (doesNotExist(teacher.getId())) {
	    throw new DaoException("update() | Teacher with ID =  " + teacher.getId() + " does NOT exist!");
	}
	String sql = "update teachers set firstname=?,lastname=?, passport=? where id=?";
	try (Connection connection = ConnectionFactory.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql)) {
	    statement.setString(1, teacher.getFirstName());
	    statement.setString(2, teacher.getLastName());
	    statement.setString(3, teacher.getPassport());
	    statement.setInt(4, teacher.getId());
	    statement.executeUpdate();
	    log.info("update() | Teacher with ID =  " + teacher.getId() + " was updated");
	} catch (SQLException e) {
	    log.error("update() | database: interaction failure", e);
	    throw new DaoException("update() | database: interaction failure", e);
	}
	log.trace("update() | end");
    }

    public void delete(Integer id) throws DaoException {
	log.trace("delete() | start");
	if (doesNotExist(id)) {
	    throw new DaoException("delete() | Teacher with  ID = " + id + " does NOT exist!");
	}
	String sql = "delete from teachers as t where t.id = ?";
	try (Connection connection = ConnectionFactory.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql)) {
	    statement.setInt(1, id);
	    statement.executeUpdate();
	    log.info("delete() | Teacher with  ID = " + id + " was deleted");
	} catch (SQLException e) {
	    log.error("delete() | database: interaction failure", e);
	    throw new DaoException("delete() | database: interaction failure", e);
	}
	log.trace("delete() | end");
    }

    private boolean doesNotExist(Integer id) throws DaoException {
	boolean notFound = true;
	String sql = "select exists(select 1 from teachers where id=?)";
	try (Connection connection = ConnectionFactory.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql)) {
	    statement.setInt(1, id);
	    try (ResultSet resultSet = statement.executeQuery()) {
		resultSet.next();
		if (resultSet.getBoolean(1) == true) {
		    notFound = false;
		}
	    }
	} catch (SQLException e) {
	    throw new DaoException("exist() | database: interaction failure", e);
	}
	return notFound;
    }
}
