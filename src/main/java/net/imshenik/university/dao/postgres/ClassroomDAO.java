package net.imshenik.university.dao.postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;
import net.imshenik.university.domain.entities.Classroom;

public class ClassroomDAO {
    private static final Logger LOGGER   = Logger.getLogger(ClassroomDAO.class.getName());
    private static final String DRIVER   = "org.postgresql.Driver";
    private static final String URL      = "jdbc:postgresql://localhost:5432/university";
    private static final String LOGIN    = "andrey";
    private static final String PASSWORD = "1234321";
    
    public Set<Classroom> findAll() throws DAOException {
        LOGGER.trace("findAll() | Getting list of all classrooms:");
        Set<Classroom> classrooms = null;
        String sql = "select * from classrooms;";
        try {
            Class.forName(DRIVER);
            LOGGER.trace("findAll() | Creating Connection, PreparedStatement and ResultSet...");
            try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
                    PreparedStatement statement = connection.prepareStatement(sql);
                    ResultSet resultSet = statement.executeQuery();) {
                LOGGER.trace("findAll() | Iterating by ResultSet...");
                classrooms = new HashSet<Classroom>();
                while (resultSet.next()) {
                    Classroom classroom = new Classroom();
                    classroom.setId(resultSet.getInt("id"));
                    classroom.setBuilding(resultSet.getString("building"));
                    classroom.setNumber(resultSet.getString("number"));
                    classroom.setCapacity(resultSet.getInt("capacity"));
                    classrooms.add(classroom);
                }
                LOGGER.info("findAll() | All " + classrooms.size() + " classrooms found");
            } catch (Exception e) {
                LOGGER.error("findAll() | Unable to read all classrooms from database", e);
                throw new DAOException("findAll() | Unable to read all classrooms from database", e);
            }
        } catch (ClassNotFoundException e) {
            LOGGER.fatal("findAll() | Unable to load driver " + DRIVER, e);
            throw new DAOException("findAll() | Unable to load driver " + DRIVER, e);
        }
        return classrooms;
    }
    
    public Classroom findOne(int id) throws DAOException {
        LOGGER.trace("findOne() | Finding classroom with ID = " + id);
        String sql = "select * from classrooms where id=?;";
        try {
            Class.forName(DRIVER);
            LOGGER.trace("findOne() | Creating Connection and PreparedStatement...");
            try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
                    PreparedStatement statement = connection.prepareStatement(sql);) {
                statement.setInt(1, id);
                LOGGER.trace("findOne() | Creating ResultSet...");
                try (ResultSet resultSet = statement.executeQuery()) {
                    LOGGER.trace("findOne() | Iterating by ResultSet...");
                    while (resultSet.next()) {
                        Classroom classroom = new Classroom();
                        classroom.setId(resultSet.getInt("id"));
                        classroom.setBuilding(resultSet.getString("building"));
                        classroom.setNumber(resultSet.getString("number"));
                        classroom.setCapacity(resultSet.getInt("capacity"));
                        LOGGER.info("findOne() | Found classroom with ID = " + id + " : " + classroom.toString());
                        return classroom;
                    }
                    LOGGER.warn("findOne() | Unable to find classroom with ID = " + id);
                }
            } catch (Exception e) {
                LOGGER.error("findOne() | Unable to create Connection", e);
                throw new DAOException("findOne() | Unable to create Connection", e);
            }
        } catch (ClassNotFoundException e) {
            LOGGER.fatal("findOne() | Unable to load driver " + DRIVER, e);
            throw new DAOException("findOne() | Unable to load driver " + DRIVER, e);
        }
        return null;
    }
    
    public Classroom create(String number, String building, int capacity) throws DAOException {
        LOGGER.trace("create() | Creating new classroom with number = " + number + " and building = " + building + " and  capacity = " + capacity);
        String sql = "insert into classrooms (number, building, capacity) values (?,?,?);";
        try {
            Class.forName(DRIVER);
            try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
                    PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
                statement.setString(1, number);
                statement.setString(2, building);
                statement.setInt(3, capacity);
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted == 0) {
                    LOGGER.warn("create() | New classroom with number = " + number + " and building = " + building + " and  capacity = " + capacity
                            + " was NOT created!");
                    return null;
                }
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt(1);
                        Classroom classroom = new Classroom(id, number, building, capacity);
                        LOGGER.info("create() | Created new classroom : " + classroom.toString());
                        return classroom;
                    }
                }
            } catch (Exception e) {
                LOGGER.error("create() | Unable to open connection", e);
                throw new DAOException("create() | Unable to open connection", e);
            }
        } catch (ClassNotFoundException e) {
            LOGGER.fatal("create() | Unable to load driver " + DRIVER, e);
            throw new DAOException("create() | Unable to load driver " + DRIVER, e);
        }
        return null;
    }
    
    public void update(int id, String number, String building, int capacity) throws DAOException {
        LOGGER.trace("update() | Updating classroom with id = " + id);
        String sql = "update classrooms set number=?,building=?, capacity=? where id=?;";
        try {
            Class.forName(DRIVER);
            try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
                    PreparedStatement statement = connection.prepareStatement(sql);) {
                statement.setString(1, number);
                statement.setString(2, building);
                statement.setInt(3, capacity);
                statement.setInt(4, id);
                LOGGER.info("update() | Before update : " + this.findOne(id).toString());
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated == 0) {
                    LOGGER.warn("update() | Classroom with ID =  " + id + " was NOT updated!");
                } else {
                    LOGGER.info("update() | After update " + this.findOne(id).toString());
                }
            } catch (Exception e) {
                LOGGER.error("update() | Unable to open connection", e);
                throw new DAOException("update() | Unable to open connection", e);
            }
        } catch (ClassNotFoundException e) {
            LOGGER.fatal("update() | Unable to load driver " + DRIVER, e);
            throw new DAOException("update() | Unable to load driver " + DRIVER, e);
        }
    }
    
    public void delete(int id) throws DAOException {
        LOGGER.trace("delete() | Deleting classroom with ID = " + id);
        String sql = "delete from classrooms as c where c.id = ?;";
        try {
            Class.forName(DRIVER);
            LOGGER.trace("delete() | Creating Connection and PreparedStatement...");
            try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
                    PreparedStatement statement = connection.prepareStatement(sql);) {
                statement.setInt(1, id);
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted == 0) {
                    LOGGER.warn("delete() | Classroom with ID =  " + id + " was NOT deleted!");
                } else {
                    LOGGER.info("delete() | Classroom with ID =  " + id + " was deleted");
                }
            } catch (Exception e) {
                LOGGER.error("delete() | Unable to open connection", e);
                throw new DAOException("delete() | Unable to open connection", e);
            }
        } catch (ClassNotFoundException e) {
            LOGGER.fatal("delete() | Unable to load driver " + DRIVER, e);
            throw new DAOException("delete() | Unable to load driver " + DRIVER, e);
        }
    }
}
