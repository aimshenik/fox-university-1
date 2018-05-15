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

public class GroupDAO extends AbstractDAO<Group> {
    
    private static final Logger log = Logger.getLogger(GroupDAO.class.getName());
    
    public List<Group> findAll() throws DAOException {
        log.trace("findAll() | call AbstractDAO.findAll() | start");
        String sql = "select * from Groups;";
        List<Group> groups = super.findAll(sql);
        log.trace("findAll() | call AbstractDAO.findAll() | end");
        return groups;
    }
    
    public Group findOne(int id) throws DAOException {
        log.trace("findOne() | call AbstractDAO.findOne() | start");
        String sql = "select * from groups where id=?;";
        Group group = super.findOne(id, sql);
        if (group == null) {
            log.info("findOne() | Group with ID = " + id + " was NOT found!");
        } else {
            log.info("findOne() | Group with ID = " + id + " was found | " + group.toString());
        }
        log.trace("findOne() | call AbstractDAO.findOne() | end");
        return group;
    }
    
    public Group create(String name) throws DAOException {
        log.trace("create() | start");
        String sql = "insert into groups (name) values (?);";
        Group group = null;
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, name);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted == 0) {
                log.error("create() | Group was NOT created!");
            }
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                group = collectOneElementFromResultSet(resultSet);
            } catch (SQLException e) {
                log.error("create() | Unable to create ResultSet", e);
                throw new DAOException("create() | Unable to create ResultSet", e);
            }
        } catch (SQLException e) {
            log.error("create() | Unable to create SQL resourses", e);
            throw new DAOException("create() | Unable to create SQL resourses", e);
        }
        if (group == null) {
            log.error("create() | Group was NOT created!");
        } else {
            log.info("create() | Group was created | " + group.toString());
        }
        log.trace("create() | end");
        return group;
    }
    
    public void update(int id, String name) throws DAOException {
        log.trace("update() | start");
        String sql = "update groups set name=? where id=?;";
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setInt(2, id);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                log.error("update() | Group with ID =  " + id + " was NOT updated!");
            } else {
                log.info("update() | Group with ID =  " + id + " was updated");
            }
        } catch (SQLException e) {
            log.error("update() | Unable to create SQL resourses", e);
            throw new DAOException("update() | Unable to create SQL resourses", e);
        }
        log.trace("update() | end");
    }
    
    public void delete(int id) throws DAOException {
        log.trace("delete() | call AbstractDAO.delete() | start");
        String sql = "delete from groups as g where g.id = ?;";
        int rowsDeleted = super.delete(id, sql);
        if (rowsDeleted == 0) {
            log.error("deleteEntity() | Group with  ID = " + id + " was NOT deleted!");
        } else {
            log.info("deleteEntity() | Group with  ID = " + id + " was deleted");
        }
        log.trace("delete() | call AbstractDAO.delete() | end");
    }
    
    @Override
    protected List<Group> collectManyElementsFromResultSet(ResultSet resultSet) throws DAOException {
        List<Group> groups = new ArrayList<>();
        int id = 0;
        String name = null;
        log.trace("collectManyElementsFromResultSet() | Getting Groups from ResultSet...");
        try {
            while (resultSet.next()) {
                id = resultSet.getInt("id");
                name = resultSet.getString("name");
                groups.add(new Group(id, name));
            }
        } catch (SQLException e) {
            log.error("collectManyElementsFromResultSet() | error while handling ResultSet with Groups", e);
            throw new DAOException("collectManyElementsFromResultSet() | error while handling ResultSet with Groups",
                    e);
        }
        return groups;
    }
    
    @Override
    protected Group collectOneElementFromResultSet(ResultSet resultSet) throws DAOException {
        Group group = null;
        List<Group> groups = collectManyElementsFromResultSet(resultSet);
        if(groups.size() > 0) {
            group = groups.get(0);
        }
        return group;
    }
}
