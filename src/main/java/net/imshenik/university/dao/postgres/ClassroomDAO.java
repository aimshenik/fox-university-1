package net.imshenik.university.dao.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;
import net.imshenik.university.domain.entities.Classroom;

public class ClassroomDAO extends AbstractDAO {
    private static final Logger log = Logger.getLogger(ClassroomDAO.class.getName());
    
    public ClassroomDAO() throws DAOException {super();
    }
    
    public Set<Classroom> findAll() throws DAOException {
        log.trace("findAll() | Getting list of all classrooms:");
        Set<Classroom> classrooms = null;
        String sql = "select * from classrooms;";
        log.trace("findAll() | Creating Connection, PreparedStatement and ResultSet...");
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();) {
            log.trace("findAll() | Iterating by ResultSet...");
            classrooms = new HashSet<Classroom>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String building = resultSet.getString("building");
                String number = resultSet.getString("number");
                int capacity = resultSet.getInt("capacity");
                classrooms.add(new Classroom(id, number, building, capacity));
            }
            log.info("findAll() | All " + classrooms.size() + " classrooms found");
        } catch (SQLException e) {
            log.error("findAll() | Unable to read all classrooms from database", e);
            throw new DAOException("findAll() | Unable to read all classrooms from database", e);
        }
        return classrooms;
    }
    
    public Classroom findOne(int id) throws DAOException {
        log.trace("findOne() | Finding classroom with ID = " + id);
        String sql = "select * from classrooms where id=?;";
        Classroom classroom = null;
        log.trace("findOne() | Creating Connection and PreparedStatement...");
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            log.trace("findOne() | Creating ResultSet...");
            try (ResultSet resultSet = statement.executeQuery()) {
                log.trace("findOne() | Iterating by ResultSet...");
                while (resultSet.next()) {
                    String building = resultSet.getString("building");
                    String number = resultSet.getString("number");
                    int capacity = resultSet.getInt("capacity");
                    classroom = new Classroom(id, number, building, capacity);
                    log.info("findOne() | Found classroom with ID = " + id + " : " + classroom.toString());
                }
                log.info("findOne() | Unable to find classroom with ID = " + id);
            }
        } catch (SQLException e) {
            log.error("findOne() | Unable to create Connection", e);
            throw new DAOException("findOne() | Unable to create Connection", e);
        }
        return classroom;
    }
    
    public Classroom create(String number, String building, int capacity) throws DAOException {
        log.trace("create() | Creating new classroom with number = " + number + " and building = " + building
                + " and  capacity = " + capacity);
        String sql = "insert into classrooms (number, building, capacity) values (?,?,?);";
        Classroom classroom = null;
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            statement.setString(1, number);
            statement.setString(2, building);
            statement.setInt(3, capacity);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted == 0) {
                log.error("create() | New classroom with number = " + number + " and building = " + building
                        + " and  capacity = " + capacity + " was NOT created!");
            }
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    classroom = new Classroom(id, number, building, capacity);
                    log.info("create() | Created new classroom : " + classroom.toString());
                }
            }
        } catch (SQLException e) {
            log.error("create() | Unable to open connection", e);
            throw new DAOException("create() | Unable to open connection", e);
        }
        return classroom;
    }
    
    public void update(int id, String number, String building, int capacity) throws DAOException {
        log.trace("update() | Updating classroom with id = " + id);
        String sql = "update classrooms set number=?,building=?, capacity=? where id=?;";
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, number);
            statement.setString(2, building);
            statement.setInt(3, capacity);
            statement.setInt(4, id);
            log.info("update() | Before update : " + this.findOne(id).toString());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                log.error("update() | Classroom with ID =  " + id + " was NOT updated!");
            } else {
                log.info("update() | After update " + this.findOne(id).toString());
            }
        } catch (SQLException e) {
            log.error("update() | Unable to open connection", e);
            throw new DAOException("update() | Unable to open connection", e);
        }
    }
    
    public void delete(int id) throws DAOException {
        log.trace("delete() | Deleting classroom with ID = " + id);
        String sql = "delete from classrooms as c where c.id = ?;";
        log.trace("delete() | Creating Connection and PreparedStatement...");
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted == 0) {
                log.error("delete() | Classroom with ID =  " + id + " was NOT deleted!");
            } else {
                log.info("delete() | Classroom with ID =  " + id + " was deleted");
            }
        } catch (SQLException e) {
            log.error("delete() | Unable to open connection", e);
            throw new DAOException("delete() | Unable to open connection", e);
        }
    }
}
