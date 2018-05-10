package net.imshenik.university.dao.postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;
import net.imshenik.university.domain.entities.Subject;

public class SubjectDAO {
    private static final Logger LOGGER   = Logger.getLogger(SubjectDAO.class.getName());
    private static final String DRIVER   = "org.postgresql.Driver";
    private static final String URL      = "jdbc:postgresql://localhost:5432/university";
    private static final String LOGIN    = "andrey";
    private static final String PASSWORD = "1234321";
    
    public Set<Subject> findAll() throws DAOException {
        LOGGER.trace("findAll() | Getting list of all subjects:");
        Set<Subject> subjects = null;
        String sql = "select * from subjects;";
        try {
            Class.forName(DRIVER);
            LOGGER.trace("findAll() | Creating Connection, PreparedStatement and ResultSet...");
            try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
                    PreparedStatement statement = connection.prepareStatement(sql);
                    ResultSet resultSet = statement.executeQuery();) {
                LOGGER.trace("findAll() | Iterating by ResultSet...");
                subjects = new HashSet<Subject>();
                while (resultSet.next()) {
                    Subject subject = new Subject();
                    subject.setId(resultSet.getInt("id"));
                    subject.setName(resultSet.getString("name"));
                    subjects.add(subject);
                }
                LOGGER.info("findAll() | All " + subjects.size() + " subjects found");
            } catch (Exception e) {
                LOGGER.error("findAll() | Unable to read all subjects from database", e);
                throw new DAOException("findAll() | Unable to read all subjects from database", e);
            }
        } catch (ClassNotFoundException e) {
            LOGGER.fatal("findAll() | Unable to load driver " + DRIVER, e);
            throw new DAOException("findAll() | Unable to load driver " + DRIVER, e);
        }
        return subjects;
    }
    
    public Subject findOne(int id) throws DAOException {
        LOGGER.trace("findOne() | Finding subject with ID = " + id);
        String sql = "select * from subjects where id=?;";
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
                        Subject subject = new Subject();
                        subject.setId(resultSet.getInt("id"));
                        subject.setName(resultSet.getString("name"));
                        LOGGER.info("findOne() | Found subject with ID = " + id + " : " + subject.toString());
                        return subject;
                    }
                    LOGGER.warn("findOne() | Unable to find subject with ID = " + id);
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
    
    public Subject create(String name) throws DAOException {
        LOGGER.trace("create() | Creating new subject with name = " + name );
        String sql = "insert into subjects (name) values (?);";
        try {
            Class.forName(DRIVER);
            try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
                    PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
                statement.setString(1, name);
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted == 0) {
                    LOGGER.warn("create() | New subject with number = " + name + " was NOT created!");
                    return null;
                }
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt(1);
                        Subject subject = new Subject(id, name);
                        LOGGER.info("create() | Created new subject : " + subject.toString());
                        return subject;
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
        LOGGER.trace("update() | Updating subject with id = " + id);
        String sql = "update subjects set name=? where id=?;";
        try {
            Class.forName(DRIVER);
            try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
                    PreparedStatement statement = connection.prepareStatement(sql);) {
                statement.setString(1, name);
                statement.setInt(2, id);
                LOGGER.info("update() | Before update : " + this.findOne(id).toString());
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated == 0) {
                    LOGGER.warn("update() | Subject with ID =  " + id + " was NOT updated!");
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
        LOGGER.trace("delete() | Deleting subject with ID = " + id);
        String sql = "delete from subjects as s where s.id = ?;";
        try {
            Class.forName(DRIVER);
            LOGGER.trace("delete() | Creating Connection and PreparedStatement...");
            try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
                    PreparedStatement statement = connection.prepareStatement(sql);) {
                statement.setInt(1, id);
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted == 0) {
                    LOGGER.warn("delete() | Subject with ID =  " + id + " was NOT deleted!");
                } else {
                    LOGGER.info("delete() | Subject with ID =  " + id + " was deleted");
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
