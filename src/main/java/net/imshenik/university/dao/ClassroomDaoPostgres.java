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
        log.trace("Getting all classrooms");
        String sql = "SELECT * FROM CLASSROOMS";
        List<Classroom> classrooms = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                classrooms.add(new Classroom(resultSet.getInt(ID), resultSet.getString(NUMBER), resultSet.getString(BUILDING),
                        resultSet.getInt(CAPACITY)));
            }
        } catch (SQLException e) {
            log.error("Database: interaction failure ", e);
            throw new DaoException("Database: interaction failure", e);
        }
        log.debug(String.format("Returned list of %d classrooms", classrooms.size()));
        return classrooms;
    }
    
    public Classroom findOne(Integer id) throws DaoException {
        log.trace(String.format("Getting classroom with ID = %d", id));
        String sql = "SELECT * FROM CLASSROOMS WHERE ID=?";
        Classroom classroom = null;
        try (Connection connection = ConnectionFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
            try (ResultSet resultSet = statement.getResultSet()) {
                if (resultSet.next()) {
                    classroom = new Classroom(resultSet.getInt(ID), resultSet.getString(NUMBER), resultSet.getString(BUILDING),
                            resultSet.getInt(CAPACITY));
                }
            }
        } catch (SQLException e) {
            log.error("Database: interaction failure", e);
            throw new DaoException("Database: interaction failure", e);
        }
        log.trace(classroom == null ? "classroom was NOT found, returning 'null' " : "classroom was found");
        return classroom;
    }
    
    public Classroom create(Classroom classroom) throws DaoException {
        log.trace(String.format("Inserting %s into database", classroom.toString()));
        String sql = "INSERT INTO CLASSROOMS (NUMBER, BUILDING, CAPACITY) VALUES (?,?,?)";
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, classroom.getNumber());
            statement.setString(2, classroom.getBuilding());
            statement.setInt(3, classroom.getCapacity());
            if (statement.executeUpdate() == 1) {
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        classroom.setId(resultSet.getInt(ID));
                        log.info(String.format("%s was created", classroom.toString()));
                    }
                }
            }
        } catch (SQLException e) {
            log.error("Database: interaction failure", e);
            throw new DaoException("Database: interaction failure", e);
        }
        return classroom;
    }
    
    public void update(Classroom classroom) throws DaoException {
        log.trace(String.format("Updating classrom %s", classroom.toString()));
        if (doesNotExist(classroom.getId())) {
            throw new DaoException("Classroom with ID =  " + classroom.getId() + " does NOT exist!");
        }
        String sql = "UPDATE CLASSROOMS SET NUMBER=?,BUILDING=?, CAPACITY=? WHERE ID=?";
        try (Connection connection = ConnectionFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, classroom.getNumber());
            statement.setString(2, classroom.getBuilding());
            statement.setInt(3, classroom.getCapacity());
            statement.setInt(4, classroom.getId());
            statement.executeUpdate();
            log.info("Classroom with ID =  " + classroom.getId() + " was updated");
        } catch (SQLException e) {
            log.error("database: interaction failure", e);
            throw new DaoException("database: interaction failure", e);
        }
    }
    
    public void delete(Integer id) throws DaoException {
        if (doesNotExist(id)) {
            throw new DaoException("Classroom with  ID = " + id + " does NOT exist!");
        }
        String sql = "DELETE FROM CLASSROOMS AS C WHERE C.ID = ?";
        log.debug(String.format("Deleting classroom with ID=%d", id));
        try (Connection connection = ConnectionFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            log.info("Classroom with  ID = " + id + " was deleted");
        } catch (SQLException e) {
            log.error("database: interaction failure", e);
            throw new DaoException("database: interaction failure", e);
        }
    }
    
    private boolean doesNotExist(Integer id) throws DaoException {
        if (id == null) {
            return true;
        }
        log.debug(String.format("Checking existance of classroom with ID=%d", id));
        boolean notFound = true;
        String sql = "SELECT EXISTS(SELECT 1 FROM CLASSROOMS WHERE ID=?)";
        try (Connection connection = ConnectionFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                if (resultSet.getBoolean(1) == true) {
                    notFound = false;
                }
            }
        } catch (SQLException e) {
            log.error("database: interaction failure", e);
            throw new DaoException("database: interaction failure", e);
        }
        log.trace(notFound ? "classroom not found" : "classroom found");
        return notFound;
    }
}
