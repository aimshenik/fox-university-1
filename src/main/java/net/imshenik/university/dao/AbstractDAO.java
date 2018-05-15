package net.imshenik.university.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;

abstract class AbstractDAO<T> {
    
    private static final Logger log = Logger.getLogger(AbstractDAO.class.getName());
    
    List<T> findAll(String sql) throws DAOException {
        log.trace("findAll() | start");
        List<T> entities = null;
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            entities = collectManyElementsFromResultSet(resultSet);
        } catch (SQLException e) {
            log.error("findAll() | Unable to create SQL resourses", e);
            throw new DAOException("findAll() | Unable to create SQL resourses", e);
        }
        log.trace("findAllEntities() | end");
        return entities;
    }
    
    T findOne(int id, String sql) throws DAOException {
        log.trace("findOne() | start");
        T entity = null;
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                entity = collectOneElementFromResultSet(resultSet);
            } catch (SQLException e) {
                log.error("findOne() | Unable to create ResultSet", e);
                throw new DAOException("findOne() | Unable to create ResultSet", e);
            }
        } catch (SQLException e) {
            log.error("findOne() | Unable to create SQL resourses", e);
            throw new DAOException("findOne() | Unable to create SQL resourses", e);
        }
        log.trace("findOne() | end");
        return entity;
    }
    
    int delete(int id, String sql) throws DAOException {
        log.trace("delete() | start");
        int rowsDeleted = 0;
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            rowsDeleted = statement.executeUpdate();
        } catch (SQLException e) {
            log.error("delete() | Unable to create SQL resourses", e);
            throw new DAOException("delete() | Unable to create SQL resourses", e);
        }
        log.trace("delete() | end");
        return rowsDeleted;
    }
    
    protected abstract List<T> collectManyElementsFromResultSet(ResultSet resultSet) throws DAOException;
    
    protected abstract T collectOneElementFromResultSet(ResultSet resultSet) throws DAOException;
}
