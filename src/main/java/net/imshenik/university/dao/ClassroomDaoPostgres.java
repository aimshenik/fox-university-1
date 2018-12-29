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
    private static final String ID = "ID";
    private static final String NUMBER = "NUMBER";
    private static final String BUILDING = "BUILDING";
    private static final String CAPACITY = "CAPACITY";
    
    public List<Classroom> findAll() throws DaoException {
        log.trace("findAll() | start");
        String sql = "SELECT * FROM CLASSROOMS";
        List<Classroom> classrooms = new ArrayList<>();
        log.debug("findAll() | Getting all existing CLASSROOMS");
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                classrooms.add(new Classroom(resultSet.getInt(ID), resultSet.getString(NUMBER),
                        resultSet.getString(BUILDING), resultSet.getInt(CAPACITY)));
            }
        } catch (SQLException e) {
            log.warn("findAll() | database: interaction failure ", e);
            throw new DaoException("findAll() | database: interaction failure", e);
        }
        log.trace("findAll() | end");
        return classrooms;
    }
    
    public Classroom findOne(Integer id) throws DaoException {
        log.trace("findOne() | start");
        String sql = "SELECT * FROM CLASSROOMS WHERE ID=?";
        Classroom classroom = null;
        log.debug(String.format("findOne() | Getting CLASSROOM with ID = %d", id));
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
            try (ResultSet resultSet = statement.getResultSet()) {
                if (resultSet.next()) {
                    classroom = new Classroom(resultSet.getInt(ID), resultSet.getString(NUMBER),
                            resultSet.getString(BUILDING), resultSet.getInt(CAPACITY));
                }
            }
        } catch (SQLException e) {
            log.warn("findOne() | database: interaction failure", e);
            throw new DaoException("findOne() | database: interaction failure", e);
        }
        log.debug(classroom == null ? "findOne() | Classroom was NOT found!" : "findOne() | Classroom was found");
        log.trace("findOne() | end");
        return classroom;
    }
    
    public Classroom create(Classroom classroom) throws DaoException {
        log.trace("create() | start");
        String sql = "INSERT INTO CLASSROOMS (NUMBER, BUILDING, CAPACITY) VALUES (?,?,?)";
        log.debug(String.format("create() | Inserting CLASSROM %s into database", classroom.toString()));
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, classroom.getNumber());
            statement.setString(2, classroom.getBuilding());
            statement.setInt(3, classroom.getCapacity());
            if (statement.executeUpdate() == 1) {
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        classroom.setId(resultSet.getInt(ID));
                        log.info("create() | Classroom was created | " + classroom.toString());
                    }
                }
            }
        } catch (SQLException e) {
            log.warn("create() | database: interaction failure", e);
            throw new DaoException("create() | database: interaction failure", e);
        }
        log.trace("create() | end");
        return classroom;
    }
    
    public void update(Classroom classroom) throws DaoException {
        log.trace("update() | start");
        if (doesNotExist(classroom.getId())) {
            throw new DaoException("update() | Classroom with ID =  " + classroom.getId() + " does NOT exist!");
        }
        String sql = "UPDATE CLASSROOMS SET NUMBER=?,BUILDING=?, CAPACITY=? WHERE ID=?";
        log.debug(String.format("update() | Updating CLASSROM %s", classroom.toString()));
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, classroom.getNumber());
            statement.setString(2, classroom.getBuilding());
            statement.setInt(3, classroom.getCapacity());
            statement.setInt(4, classroom.getId());
            statement.executeUpdate();
            log.info("update() | Classroom with ID =  " + classroom.getId() + " was updated");
        } catch (SQLException e) {
            log.warn("update() | database: interaction failure", e);
            throw new DaoException("update() | database: interaction failure", e);
        }
        log.trace("update() | end");
    }
    
    public void delete(Integer id) throws DaoException {
        log.trace("delete() | start");
        if (doesNotExist(id)) {
            throw new DaoException("delete() | Classroom with  ID = " + id + " does NOT exist!");
        }
        String sql = "DELETE FROM CLASSROOMS AS C WHERE C.ID = ?";
        log.debug(String.format("delete() | Deletion CLASSROOM with ID=%d", id));
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            log.info("delete() | Classroom with  ID = " + id + " was deleted");
        } catch (SQLException e) {
            log.warn("delete() | database: interaction failure", e);
            throw new DaoException("delete() | database: interaction failure", e);
        }
        log.trace("delete() | end");
    }
    
    private boolean doesNotExist(Integer id) throws DaoException {
        log.trace("doesNotExist() | start");
        if (id == null) {
            return true;
        }
        boolean notFound = true;
        String sql = "SELECT EXISTS(SELECT 1 FROM CLASSROOMS WHERE ID=?)";
        log.debug(String.format("doesNotExist() | Checking existance of CLASSROOM with ID=%d", id));
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
            log.warn("doesNotExist() | database: interaction failure", e);
            throw new DaoException("doesNotExist() | database: interaction failure", e);
        }
        log.trace("doesNotExist() | end");
        return notFound;
    }
}
