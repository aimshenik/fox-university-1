package net.imshenik.university.dao.postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;
import net.imshenik.university.domain.entities.Group;

public class GroupDAO {
    private static final Logger log   = Logger.getLogger(GroupDAO.class.getName());
    private static final String DRIVER   = DAOSettings.getDriver();
    private static final String URL      = DAOSettings.getUrl();
    private static final String LOGIN    = DAOSettings.getLogin();
    private static final String PASSWORD = DAOSettings.getPassword();
    
    public Set<Group> findAll() throws DAOException {
        log.trace("findAll() | Getting list of all groups");
        Set<Group> groups = null;
        String sql = "select * from groups;";
        try {
            Class.forName(DRIVER);
            log.trace("findAll() | Creating Connection, PreparedStatement and ResultSet...");
            try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
                    PreparedStatement statement = connection.prepareStatement(sql);
                    ResultSet resultSet = statement.executeQuery();) {
                log.trace("findAll() | Iterating by ResultSet...");
                groups = new HashSet<Group>();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    groups.add(new Group(id, name));
                }
                log.info("findAll() | All " + groups.size() + " groups found");
            } catch (SQLException e) {
                log.error("findAll() | Unable to read all groups from database", e);
                throw new DAOException("findAll() | Unable to read all groups from database", e);
            }
        } catch (ClassNotFoundException e) {
            log.fatal("findAll() | Unable to load driver " + DRIVER, e);
            throw new DAOException("findAll() | Unable to load driver " + DRIVER, e);
        }
        return groups;
    }
    
    public Group findOne(int id) throws DAOException {
        log.trace("findOne() | Finding group with ID = " + id);
        String sql = "select * from groups where id=?;";
        Group group = null;
        try {
            Class.forName(DRIVER);
            log.trace("findOne() | Creating Connection and PreparedStatement...");
            try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
                    PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                log.trace("findOne() | Creating ResultSet...");
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String name = resultSet.getString("name");
                        group = new Group(id, name);
                        log.info("findOne() | Found group with ID = " + id + " : " + group.toString());
                    }
                    log.info("findOne() | Unable to find group with ID = " + id);
                }
            } catch (SQLException e) {
                log.error("findOne() | Unable to create Connection", e);
                throw new DAOException("Unable to create Connection", e);
            }
        } catch (ClassNotFoundException e) {
            log.fatal("findOne() | Unable to load driver " + DRIVER, e);
            throw new DAOException("findOne() | Unable to load driver " + DRIVER, e);
        }
        return group;
    }
    
    public Group create(String name) throws DAOException {
        log.trace("create() | Creating new group with name = " + name);
        String sql = "insert into groups (name) values (?);";
        Group group = null;
        try {
            Class.forName(DRIVER);
            try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
                    PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
                statement.setString(1, name);
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted == 0) {
                    log.error("create() | New group with name = " + name + " was NOT created!");
                }
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt(1);
                        group = new Group(id, name);
                        log.info("create() | Created new group : " + group.toString());
                    }
                }
            } catch (SQLException e) {
                log.error("create() | Unable to open connection", e);
                throw new DAOException("create() | Unable to open connection", e);
            }
        } catch (ClassNotFoundException e) {
            log.fatal("create() | Unable to load driver " + DRIVER, e);
            throw new DAOException("create() | Unable to load driver " + DRIVER, e);
        }
        return group;
    }
    
    public void update(int id, String name) throws DAOException {
        log.trace("update() | Updating Group with id = " + id);
        String sql = "update groups set name=? where id=?;";
        try {
            Class.forName(DRIVER);
            try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
                    PreparedStatement statement = connection.prepareStatement(sql);) {
                statement.setString(1, name);
                statement.setInt(2, id);
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated == 0) {
                    log.error("update() | Group with id =  " + id + " was not updated!");
                } else {
                    log.info("update() | After update " + this.findOne(id).toString());
                }
            } catch (SQLException e) {
                log.error("update() | Unable to open connection", e);
                throw new DAOException("update() | Unable to open connection", e);
            }
        } catch (ClassNotFoundException e) {
            log.fatal("update() | Unable to load driver " + DRIVER, e);
            throw new DAOException("update() | Unable to load driver " + DRIVER, e);
        }
    }
    
    public void delete(int id) throws DAOException {
        log.trace("delete() | Deleting group with ID = " + id);
        String sql = "delete from groups as g where g.id = ?;";
        try {
            Class.forName(DRIVER);
            log.trace("delete() | Creating Connection and PreparedStatement...");
            try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
                    PreparedStatement statement = connection.prepareStatement(sql);) {
                statement.setInt(1, id);
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted == 0) {
                    log.error("delete() | Group with ID = " + id + " was NOT deleted!");
                } else {
                    log.info("delete() | Group with ID = " + id + " was deleted");
                }
            } catch (SQLException e) {
                log.error("delete() | Unable to open connection", e);
                throw new DAOException("Unable to open connection", e);
            }
        } catch (ClassNotFoundException e) {
            log.fatal("delete() | Unable to load driver " + DRIVER, e);
            throw new DAOException("delete() | Unable to load driver " + DRIVER, e);
        }
    }
}
