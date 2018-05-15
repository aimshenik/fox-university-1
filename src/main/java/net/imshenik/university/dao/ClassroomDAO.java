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

public class ClassroomDAO extends AbstractDAO<Classroom> {
    
    private static final Logger log = Logger.getLogger(ClassroomDAO.class.getName());
    
    public List<Classroom> findAll() throws DAOException {
        log.trace("findAll() | call AbstractDAO.findAll() | start");
        String sql = "select * from classrooms;";
        List<Classroom> classrooms = super.findAll(sql);
        log.trace("findAll() | call AbstractDAO.findAll() | end");
        return classrooms;
    }
    
    public Classroom findOne(int id) throws DAOException {
        log.trace("findOne() | call AbstractDAO.findOne() | start");
        String sql = "select * from classrooms where id=?;";
        Classroom classroom = super.findOne(id, sql);
        if (classroom == null) {
            log.info("findOne() | Classroom with ID = " + id + " was NOT found!");
        } else {
            log.info("findOne() | Classroom with ID = " + id + " was found | " + classroom.toString());
        }
        log.trace("findOne() | call AbstractDAO.findOne() | end");
        return classroom;
    }
    
    public Classroom create(String number, String building, int capacity) throws DAOException {
        log.trace("create() | start");
        String sql = "insert into classrooms (number, building, capacity) values (?,?,?);";
        Classroom classroom = null;
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, number);
            statement.setString(2, building);
            statement.setInt(3, capacity);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted == 0) {
                log.error("create() | Classroom was NOT created!");
            }
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                classroom = collectOneElementFromResultSet(resultSet);
            } catch (SQLException e) {
                log.error("create() | Unable to create ResultSet", e);
                throw new DAOException("create() | Unable to create ResultSet", e);
            }
        } catch (SQLException e) {
            log.error("create() | Unable to create SQL resourses", e);
            throw new DAOException("create() | Unable to create SQL resourses", e);
        }
        if (classroom == null) {
            log.error("create() | Classroom was NOT created!");
        } else {
            log.info("create() | Classroom was created | " + classroom.toString());
        }
        log.trace("create() | end");
        return classroom;
    }
    
    public void update(int id, String number, String building, int capacity) throws DAOException {
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
            throw new DAOException("update() | Unable to create SQL resourses", e);
        }
        log.trace("update() | end");
    }
    
    public void delete(int id) throws DAOException {
        log.trace("delete() | call AbstractDAO.delete() | start");
        String sql = "delete from classrooms as c where c.id = ?;";
        int rowsDeleted = super.delete(id, sql);
        if (rowsDeleted == 0) {
            log.error("deleteEntity() | Classroom with  ID = " + id + " was NOT deleted!");
        } else {
            log.info("deleteEntity() | Classroom with  ID = " + id + " was deleted");
        }
        log.trace("delete() | call AbstractDAO.delete() | end");
    }
    
    @Override
    protected List<Classroom> collectManyElementsFromResultSet(ResultSet resultSet) throws DAOException {
        List<Classroom> classrooms = new ArrayList<>();
        int id = 0;
        String building = null;
        String number = null;
        int capacity = 0;
        log.trace("collectManyElementsFromResultSet() | Getting Classrooms from ResultSet...");
        try {
            while (resultSet.next()) {
                id = resultSet.getInt("id");
                building = resultSet.getString("building");
                number = resultSet.getString("number");
                capacity = resultSet.getInt("capacity");
                classrooms.add(new Classroom(id, number, building, capacity));
            }
        } catch (SQLException e) {
            log.error("collectManyElementsFromResultSet() | error while handling ResultSet with Classrooms", e);
            throw new DAOException(
                    "collectManyElementsFromResultSet() | error while handling ResultSet with Classrooms", e);
        }
        return classrooms;
    }
    
    @Override
    protected Classroom collectOneElementFromResultSet(ResultSet resultSet) throws DAOException {
        Classroom classroom = null;
        List<Classroom> classrooms = collectManyElementsFromResultSet(resultSet);
        if (classrooms.size() > 0) {
            classroom = classrooms.get(0);
        }
        return classroom;
    }
}
