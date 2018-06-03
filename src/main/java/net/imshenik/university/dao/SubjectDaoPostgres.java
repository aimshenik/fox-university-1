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

public class SubjectDaoPostgres implements SubjectDao {
    private static final Logger log = Logger.getLogger(SubjectDaoPostgres.class.getName());

    public List<Subject> findAll() throws DaoException {
        log.trace("findAll() | start");
        String sql = "select * from subjects";
        List<Subject> subjects = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            log.trace("findAll() | Getting Subjects from ResultSet...");
            while (resultSet.next()) {
                subjects.add(new Subject(resultSet.getInt("id"), resultSet.getString("name")));
            }
        } catch (SQLException e) {
            log.error("findAll() | database: interaction failure ", e);
            throw new DaoException("findAll() | database: interaction failure", e);
        }
        log.trace("findAll() | end");
        return subjects;
    }
    
    public Subject findOne(Integer id) throws DaoException {
        log.trace("findOne() | start");
        String sql = "select * from subjects where id=?";
        Subject subject = null;
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
            try (ResultSet resultSet = statement.getResultSet()) {
                if (resultSet.next()) {
                    subject = new Subject(resultSet.getInt("id"), resultSet.getString("name"));
                }
            }
        } catch (SQLException e) {
            log.error("findOne() | database: interaction failure", e);
            throw new DaoException("findOne() | database: interaction failure", e);
        }
        log.trace(subject == null ? "findOne() | Subject was NOT found!" : "findOne() | Subject was found");
        log.trace("findOne() | end");
        return subject;
    }
    
    public Subject create(Subject subject) throws DaoException {
        log.trace("create() | start");
        String sql = "insert into subjects (name) values (?)";
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, subject.getName());
            if (statement.executeUpdate() == 1) {
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        subject.setId(resultSet.getInt("Id"));
                        log.info("create() | Subject was created | " + subject.toString());
                    }
                }
            }
        } catch (SQLException e) {
            log.error("create() | database: interaction failure", e);
            throw new DaoException("create() | database: interaction failure", e);
        }
        log.trace("create() | end");
        return subject;
    }
    
    public void update(Subject subject) throws DaoException {
        log.trace("update() | start");
        if (doesNotExist(subject.getId())) {
            throw new DaoException("update() | Subject with ID =  " + subject.getId() + " does NOT exist!");
        }
        String sql = "update subjects set name=? where id=?";
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, subject.getName());
            statement.setInt(2, subject.getId());
            statement.executeUpdate();
            log.info("update() | Subject with ID =  " + subject.getId() + " was updated");
        } catch (SQLException e) {
            log.error("update() | database: interaction failure", e);
            throw new DaoException("update() | database: interaction failure", e);
        }
        log.trace("update() | end");
    }
    
    public void delete(Integer id) throws DaoException {
        log.trace("delete() | start");
        if (doesNotExist(id)) {
            throw new DaoException("delete() | Subject with  ID = " + id + " does NOT exist!");
        }
        String sql = "delete from subjects as s where s.id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            log.info("delete() | Subject with  ID = " + id + " was deleted");
        } catch (SQLException e) {
            log.error("delete() | database: interaction failure", e);
            throw new DaoException("delete() | database: interaction failure", e);
        }
        log.trace("delete() | end");
    }
    
    private boolean doesNotExist(Integer id) throws DaoException {
        boolean notFound = true;
        String sql = "select exists(select 1 from subjects where id=?)";
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
