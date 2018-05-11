package net.imshenik.university.dao.postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;
import net.imshenik.university.domain.entities.Group;

public class GroupDAO {
    private static final Logger LOGGER   = Logger.getLogger(GroupDAO.class.getName());
    private static final String DRIVER   = "org.postgresql.Driver";
    private static final String URL      = "jdbc:postgresql://localhost:5432/university";
    private static final String LOGIN    = "andrey";
    private static final String PASSWORD = "1234321";
    
    public Set<Group> findAll() throws DAOException {
        LOGGER.trace("findAll() | Getting list of all groups");
        Set<Group> groups = null;
        String sql = "select * from groups;";
        try {
            Class.forName(DRIVER);
            LOGGER.trace("findAll() | Creating Connection, PreparedStatement and ResultSet...");
            try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
                    PreparedStatement statement = connection.prepareStatement(sql);
                    ResultSet resultSet = statement.executeQuery();) {
                LOGGER.trace("findAll() | Iterating by ResultSet...");
                groups = new HashSet<Group>();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    groups.add(new Group(id, name));
                }
                LOGGER.info("findAll() | All " + groups.size() + " groups found");
            } catch (Exception e) {
                LOGGER.error("findAll() | Unable to read all groups from database", e);
                throw new DAOException("findAll() | Unable to read all groups from database", e);
            }
        } catch (ClassNotFoundException e) {
            LOGGER.fatal("findAll() | Unable to load driver " + DRIVER, e);
            throw new DAOException("findAll() | Unable to load driver " + DRIVER, e);
        }
        return groups;
    }
    
    public Group findOne(int id) throws DAOException {
        LOGGER.trace("findOne() | Finding group with ID = " + id);
        String sql = "select * from groups where id=?;";
        try {
            Class.forName(DRIVER);
            LOGGER.trace("findOne() | Creating Connection and PreparedStatement...");
            try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
                    PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                LOGGER.trace("findOne() | Creating ResultSet...");
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String name = resultSet.getString("name");
                        Group group = new Group(id, name);
                        LOGGER.info("findOne() | Found group with ID = " + id + " : " + group.toString());
                        return group;
                    }
                    LOGGER.warn("findOne() | Unable to find group with ID = " + id);
                    return null;
                }
            } catch (Exception e) {
                LOGGER.error("findOne() | Unable to create Connection", e);
                throw new DAOException("Unable to create Connection", e);
            }
        } catch (ClassNotFoundException e) {
            LOGGER.fatal("findOne() | Unable to load driver " + DRIVER, e);
            throw new DAOException("findOne() | Unable to load driver " + DRIVER, e);
        }
    }
    
    public Group create(String name) throws DAOException {
        LOGGER.trace("create() | Creating new group with name = " + name);
        String sql = "insert into groups (name) values (?);";
        try {
            Class.forName(DRIVER);
            try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
                    PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
                statement.setString(1, name);
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted == 0) {
                    LOGGER.warn("create() | New group with name = " + name + " was NOT created!");
                    return null;
                }
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt(1);
                        Group group = new Group(id, name);
                        LOGGER.info("create() | Created new group : " + group.toString());
                        return group;
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
    
    public void update(int id, String name) throws DAOException {
        LOGGER.trace("update() | Updating Group with id = " + id);
        String sql = "update groups set name=? where id=?;";
        try {
            Class.forName(DRIVER);
            try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
                    PreparedStatement statement = connection.prepareStatement(sql);) {
                statement.setString(1, name);
                statement.setInt(2, id);
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated == 0) {
                    LOGGER.warn("update() | Group with id =  " + id + " was not updated!");
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
        LOGGER.trace("delete() | Deleting group with ID = " + id);
        String sql = "delete from groups as g where g.id = ?;";
        try {
            Class.forName(DRIVER);
            LOGGER.trace("delete() | Creating Connection and PreparedStatement...");
            try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
                    PreparedStatement statement = connection.prepareStatement(sql);) {
                statement.setInt(1, id);
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted == 0) {
                    LOGGER.warn("delete() | Group with ID = " + id + " was NOT deleted!");
                } else {
                    LOGGER.info("delete() | Group with ID = " + id + " was deleted");
                }
            } catch (Exception e) {
                LOGGER.error("delete() | Unable to open connection", e);
                throw new DAOException("Unable to open connection", e);
            }
        } catch (ClassNotFoundException e) {
            LOGGER.fatal("delete() | Unable to load driver " + DRIVER, e);
            throw new DAOException("delete() | Unable to load driver " + DRIVER, e);
        }
    }
}
