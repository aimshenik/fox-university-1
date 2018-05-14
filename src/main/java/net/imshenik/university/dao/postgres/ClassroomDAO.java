package net.imshenik.university.dao.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import net.imshenik.university.domain.entities.Classroom;

public class ClassroomDAO extends AbstractDAO<Classroom> {
    
    private static final Logger log = Logger.getLogger(ClassroomDAO.class.getName());
    
    public ClassroomDAO() throws DAOException {
    }
    
    public List<Classroom> findAll() throws DAOException {
        String sql = "select * from classrooms;";
        log.trace("findAll() | call AbstractDAO.findAll()");
        return super.findAll(sql);
    }
    
    public Classroom findOne(int id) throws DAOException {
        String sql = "select * from classrooms where id=?;";
        log.trace("findOne() | call AbstractDAO.findOne()");
        return super.findOne(id, sql);
    }
    
    public Classroom create(String number, String building, int capacity) throws DAOException {
        log.trace("create() | start");
        String sql = "insert into classrooms (number, building, capacity) values (?,?,?);";
        Classroom classroom = null;
        try (Connection connection = getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, number);
                statement.setString(2, building);
                statement.setInt(3, capacity);
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted == 0) {
                    log.error("create() | Classroom was NOT created!");
                }
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    classroom = retrieveOneEntityFromResultSet(resultSet);
                } catch (SQLException e) {
                    log.error("create() | Unable to create ResultSet", e);
                    throw new DAOException("create() | Unable to create ResultSet", e);
                }
            } catch (Exception e) {
                log.error("create() | Unable to create PreparedStatement", e);
                throw new DAOException("create() | Unable to create PreparedStatement", e);
            }
        } catch (SQLException e) {
            log.error("create() | Unable to create Connection", e);
            throw new DAOException("create() | Unable to create Connection", e);
        }
        log.trace("create() | end");
        return classroom;
    }
    
    public void update(int id, String number, String building, int capacity) throws DAOException {
        log.trace("update() | start");
        String sql = "update classrooms set number=?,building=?, capacity=? where id=?;";
        try (Connection connection = getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
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
            } catch (Exception e) {
                log.error("create() | Unable to create PreparedStatement", e);
                throw new DAOException("create() | Unable to create PreparedStatement", e);
            }
        } catch (SQLException e) {
            log.error("update() | Unable to create Connection", e);
            throw new DAOException("update() | Unable to create Connection", e);
        }
        log.trace("update() | end");
    }
    
    public void delete(int id) throws DAOException {
        String sql = "delete from classrooms as c where c.id = ?;";
        log.trace("delete() | call AbstractDAO.delete()");
        super.delete(id, sql);
    }
    
    @Override
    protected List<Classroom> retrieveAllEntitiesFromResultSet(ResultSet resultSet) throws DAOException {
        List<Classroom> classrooms = new ArrayList<>();
        int id = 0;
        String building = null;
        String number = null;
        int capacity = 0;
        try {
            while (resultSet.next()) {
                id = resultSet.getInt("id");
                building = resultSet.getString("building");
                number = resultSet.getString("number");
                capacity = resultSet.getInt("capacity");
                classrooms.add(new Classroom(id, number, building, capacity));
            }
        } catch (SQLException e) {
            log.error("findAll() | error while handling ResultSet with Classrooms", e);
            throw new DAOException("findAll() | error while handling ResultSet with Classrooms", e);
        }
        return classrooms;
    }
    
    @Override
    protected Classroom retrieveOneEntityFromResultSet(ResultSet resultSet) throws DAOException {
        Classroom classroom = null;
        int id = 0;
        String building = null;
        String number = null;
        int capacity = 0;
        log.trace("findOne() | Getting Classroom from ResultSet...");
        try {
            while (resultSet.next()) {
                id = resultSet.getInt("id");
                building = resultSet.getString("building");
                number = resultSet.getString("number");
                capacity = resultSet.getInt("capacity");
                classroom = new Classroom(id, number, building, capacity);
            }
        } catch (SQLException e) {
            log.error("findOne() | error while handling ResultSet with Classroom", e);
            throw new DAOException("findOne() | error while handling ResultSet with Classroom", e);
        }
        if (classroom == null) {
            log.info("findOne() | Classroom with ID =  " + id + " was NOT found!");
        } else {
            log.info("findOne() | Classroom with ID =  " + id + " was found");
        }
        return classroom;
    }
    
}
