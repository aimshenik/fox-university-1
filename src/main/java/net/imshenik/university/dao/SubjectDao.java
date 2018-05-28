package net.imshenik.university.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import net.imshenik.university.domain.Subject;

public class SubjectDao extends AbstractDao<Subject> {
    private static final Logger log = Logger.getLogger(SubjectDao.class.getName());
    
    public List<Subject> findAll() throws DaoException {
        log.trace("findAll() | call AbstractDAO.findAll() | start");
        String sql = "select * from subjects;";
        List<Subject> subjects = super.findAll(sql);
        log.trace("findAll() | call AbstractDAO.findAll() | end");
        return subjects;
    }
    
    public Subject findOne(int id) throws DaoException {
        log.trace("findOne() | call AbstractDAO.findOne() | start");
        String sql = "select * from subjects where id=?;";
        Subject subject = super.findOne(id, sql);
        if (subject == null) {
            log.info("findOne() | Subject with ID = " + id + " was NOT found!");
        } else {
            log.info("findOne() | Subject with ID = " + id + " was found | " + subject.toString());
        }
        log.trace("findOne() | call AbstractDAO.findOne() | end");
        return subject;
    }
    
    public Subject create(String name) throws DaoException {
        log.trace("create() | Creating new subject with name = " + name);
        String sql = "insert into subjects (name) values (?);";
        Subject subject = null;
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, name);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted == 0) {
                log.error("create() | Subject was NOT created!");
            }
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                subject = collectOneElementFromResultSet(resultSet);
            } catch (SQLException e) {
                log.error("create() | Unable to create ResultSet", e);
                throw new DaoException("create() | Unable to create ResultSet", e);
            }
        } catch (SQLException e) {
            log.error("create() | Unable to create SQL resourses", e);
            throw new DaoException("create() | Unable to create SQL resourses", e);
        }
        return subject;
    }
    
    public void update(int id, String name) throws DaoException {
        log.trace("update() | start");
        String sql = "update subjects set name=? where id=?;";
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setInt(2, id);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                log.error("update() | Subject with ID =  " + id + " was NOT updated!");
            } else {
                log.info("update() | Subject with ID =  " + id + " was updated");
            }
        } catch (SQLException e) {
            log.error("update() | Unable to create SQL resourses", e);
            throw new DaoException("update() | Unable to create SQL resourses", e);
        }
    }
    
    public void delete(int id) throws DaoException {
        log.trace("delete() | call AbstractDAO.delete() | start");
        String sql = "delete from subjects as s where s.id = ?;";
        int rowsDeleted = super.delete(id, sql);
        if (rowsDeleted == 0) {
            log.error("deleteEntity() | Subjects with ID = " + id + " was NOT deleted!");
        } else {
            log.info("deleteEntity() | Subjects with ID = " + id + " was deleted");
        }
        log.trace("delete() | call AbstractDAO.delete() | end");
    }
    
    @Override
    protected List<Subject> collectManyElementsFromResultSet(ResultSet resultSet) throws DaoException {
        List<Subject> subjects = new ArrayList<>();
        int id = 0;
        String name = null;
        log.trace("collectManyElementsFromResultSet() | Getting Subjects from ResultSet...");
        try {
            while (resultSet.next()) {
                id = resultSet.getInt("id");
                name = resultSet.getString("name");
                subjects.add(new Subject(id, name));
            }
        } catch (SQLException e) {
            log.error("collectManyElementsFromResultSet() | error while handling ResultSet with Subjects", e);
            throw new DaoException("collectManyElementsFromResultSet() | error while handling ResultSet with Subjects",
                    e);
        }
        return subjects;
    }
    
    @Override
    protected Subject collectOneElementFromResultSet(ResultSet resultSet) throws DaoException {
        Subject subject = null;
        List<Subject> subjects = collectManyElementsFromResultSet(resultSet);
        if (subjects.size() > 0) {
            subject = subjects.get(0);
        }
        return subject;
    }
}
