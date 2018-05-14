package net.imshenik.university.dao.postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;
import net.imshenik.university.domain.entities.UniversityEntity;

abstract class AbstractDAO<T extends UniversityEntity> {
    
    private static final Logger log      = Logger.getLogger(AbstractDAO.class.getName());
    private static final String DRIVER   = "org.postgresql.Driver";
    private static final String URL      = "jdbc:postgresql://localhost:5432/university";
    private static final String LOGIN    = "andrey";
    private static final String PASSWORD = "1234321";
    
    AbstractDAO() throws DAOException {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            log.fatal("AbstractDAO() | Unable to load driver " + DRIVER, e);
            throw new DAOException("AbstractDAO() | Unable to load driver " + DRIVER, e);
        }
    }
    
    Connection getConnection() throws DAOException {
        Connection connection = null;
        try {
            log.trace("getConnection() | Opening connection to database");
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        } catch (SQLException e) {
            log.fatal("getConnection() | Unable to create Connection", e);
            throw new DAOException("getConnection() | Unable to create Connection", e);
        }
        return connection;
    }
    
    PreparedStatement getStatement(Connection connection, String sql) throws DAOException {
        PreparedStatement statement = null;
        try {
            log.trace("getConnection() | Opening connection to database");
            statement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            log.fatal("getConnection() | Unable to create Connection", e);
            throw new DAOException("getConnection() | Unable to create Connection", e);
        }
        return statement;
    }
    
    List<T> findAll(String sql) throws DAOException {
        log.trace("findAll() | start");
        List<T> entities = null;
        try (Connection connection = getConnection()) {
            try (PreparedStatement statement = getStatement(connection, sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    entities = retrieveAllEntitiesFromResultSet(resultSet);
                } catch (SQLException e) {
                    log.error("findAll() | Unable to create ResultSet", e);
                    throw new DAOException("findAll() | Unable to create ResultSet", e);
                }
            } catch (SQLException e) {
                log.error("findAll() | Unable to create PreparedStatement", e);
                throw new DAOException("findAll() | Unable to create PreparedStatement", e);
            }
        } catch (SQLException e) {
            log.error("findAll() | Unable to create Connection", e);
            throw new DAOException("findAll() | Unable to create Connection", e);
        }
        log.trace("findAll() | end");
        return entities;
    }
    
    T findOne(int id, String sql) throws DAOException {
        log.trace("findAll() | start");
        T entity = null;
        try (Connection connection = getConnection()) {
            try (PreparedStatement statement = getStatement(connection, sql)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    entity = retrieveOneEntityFromResultSet(resultSet);
                } catch (SQLException e) {
                    log.error("findAll() | Unable to create ResultSet", e);
                    throw new DAOException("findAll() | Unable to create ResultSet", e);
                }
            } catch (SQLException e) {
                log.error("findAll() | Unable to create PreparedStatement", e);
                throw new DAOException("findAll() | Unable to create PreparedStatement", e);
            }
        } catch (SQLException e) {
            log.error("findAll() | Unable to create Connection", e);
            throw new DAOException("findAll() | Unable to create Connection", e);
        }
        log.trace("findAll() | end");
        return entity;
    }
    
    void delete(int id, String sql) throws DAOException {
        log.trace("delete() | start");
        try (Connection connection = getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted == 0) {
                    log.error("delete() | Classroom was NOT deleted!");
                } else {
                    log.info("delete() | Classroom was deleted");
                }
            } catch (SQLException e) {
                log.error("delete() | Unable to create PreparedStatement", e);
                throw new DAOException("delete() | Unable to create PreparedStatement", e);
            }
        } catch (SQLException e) {
            log.error("delete() | Unable to create Connection", e);
            throw new DAOException("delete() | Unable to create Connection", e);
        }
        log.trace("delete() | end");
    }
    
   protected abstract List<T> retrieveAllEntitiesFromResultSet(ResultSet resultSet) throws DAOException;
    
   protected abstract T retrieveOneEntityFromResultSet(ResultSet resultSet) throws DAOException;
}
