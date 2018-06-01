package net.imshenik.university.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import net.imshenik.university.domain.Group;

public class GroupDaoPostgres implements GroupDao {
    private static final Logger log = Logger.getLogger(GroupDaoPostgres.class.getName());
    
    public List<Group> findAll() throws DaoException {
        log.trace("findAll() | start");
        String sql = "select * from groups";
        List<Group> groups = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            log.trace("findAll() | Getting Groups from ResultSet...");
            while (resultSet.next()) {
                groups.add(new Group(resultSet.getInt("id"), resultSet.getString("name")));
            }
        } catch (SQLException e) {
            log.error("findAll() | database: interaction failure ", e);
            throw new DaoException("findAll() | database: interaction failure", e);
        }
        log.trace("findAll() | end");
        return groups;
    }
    
    public Group findOne(Integer id) throws DaoException {
        log.trace("findOne() | start");
        String sql = "select * from groups where id=?";
        Group group = null;
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
            try (ResultSet resultSet = statement.getResultSet()) {
                if (resultSet.next()) {
                    group = new Group(resultSet.getInt("id"), resultSet.getString("name"));
                }
            }
        } catch (SQLException e) {
            log.error("findOne() | database: interaction failure", e);
            throw new DaoException("findOne() | database: interaction failure", e);
        }
        log.trace(group == null ? "findOne() | Group was NOT found!" : "findOne() | Group was found");
        log.trace("findOne() | end");
        return group;
    }
    
    public Group create(Group group) throws DaoException {
        log.trace("create() | start");
        String sql = "insert into groups (name) values (?)";
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, group.getName());
            if (statement.executeUpdate() == 1) {
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        group.setId(resultSet.getInt("Id"));
                        log.info("create() | Group was created | " + group.toString());
                    }
                }
            }
        } catch (SQLException e) {
            log.error("create() | database: interaction failure", e);
            throw new DaoException("create() | database: interaction failure", e);
        }
        log.trace("create() | end");
        return group;
    }
    
    public void update(Group group) throws DaoException {
        log.trace("update() | start");
        if (doesNotExist(group.getId())) {
            throw new DaoException("update() | Group with ID =  " + group.getId() + " does NOT exist!");
        }
        String sql = "update groups set name=? where id=?";
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, group.getName());
            statement.setInt(2, group.getId());
            statement.executeUpdate();
            log.info("update() | Group with ID =  " + group.getId() + " was updated");
        } catch (SQLException e) {
            log.error("update() | database: interaction failure", e);
            throw new DaoException("update() | database: interaction failure", e);
        }
        log.trace("update() | end");
    }
    
    public void delete(Integer id) throws DaoException {
        log.trace("delete() | start");
        if (doesNotExist(id)) {
            throw new DaoException("delete() | Group with  ID = " + id + " does NOT exist!");
        }
        String sql = "delete from groups as g where g.id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            log.info("delete() | Group with  ID = " + id + " was deleted");
        } catch (SQLException e) {
            log.error("delete() | database: interaction failure", e);
            throw new DaoException("delete() | database: interaction failure", e);
        }
        log.trace("delete() | end");
    }
    
    private boolean doesNotExist(Integer id) throws DaoException {
        boolean notFound = true;
        String sql = "select exists(select 1 from groups where id=?)";
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                if (resultSet.getBoolean("exists") == true) {
                    notFound = false;
                }
            }
        } catch (SQLException e) {
            throw new DaoException("exist() | database: interaction failure", e);
        }
        return notFound;
    }
}
