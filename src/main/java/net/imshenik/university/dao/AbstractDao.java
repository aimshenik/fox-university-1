package net.imshenik.university.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;

abstract class AbstractDao<T> {
    
    private static final Logger log = Logger.getLogger(AbstractDao.class.getName());
    
    List<T> findAll(String sql) throws DaoException {
        log.trace("findAll() | start");
        List<T> entities = null;
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            entities = collectManyElementsFromResultSet(resultSet);
        } catch (SQLException e) {
            log.error("findAll() | Unable to create SQL resourses", e);
            throw new DaoException("findAll() | Unable to create SQL resourses", e);
        }
        log.trace("findAll() | end");
        return entities;
    }
    
    T findOne(int id, String sql) throws DaoException {
        log.trace("findOne() | start");
        T entity = null;
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                entity = collectOneElementFromResultSet(resultSet);
            } catch (SQLException e) {
                log.error("findOne() | Unable to create ResultSet", e);
                throw new DaoException("findOne() | Unable to create ResultSet", e);
            }
        } catch (SQLException e) {
            log.error("findOne() | Unable to create SQL resourses", e);
            throw new DaoException("findOne() | Unable to create SQL resourses", e);
        }
        log.trace("findOne() | end");
        return entity;
    }
    
    int delete(int id, String sql) throws DaoException {
        log.trace("delete() | start");
        int rowsDeleted = 0;
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            rowsDeleted = statement.executeUpdate();
        } catch (SQLException e) {
            log.error("delete() | Unable to create SQL resourses", e);
            throw new DaoException("delete() | Unable to create SQL resourses", e);
        }
        log.trace("delete() | end");
        return rowsDeleted;
    }
    
    protected abstract List<T> collectManyElementsFromResultSet(ResultSet resultSet) throws DaoException;
    
    protected abstract T collectOneElementFromResultSet(ResultSet resultSet) throws DaoException;
}
