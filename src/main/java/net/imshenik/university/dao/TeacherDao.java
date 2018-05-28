package net.imshenik.university.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import net.imshenik.university.domain.Teacher;

public class TeacherDao extends AbstractDao<Teacher> {
    private static final Logger log = Logger.getLogger(TeacherDao.class.getName());
    
    public List<Teacher> findAll() throws DaoException {
        log.trace("findAll() | call AbstractDAO.findAll() | start");
        String sql = "select * from teachers;";
        List<Teacher> teachers = super.findAll(sql);
        log.trace("findAll() | call AbstractDAO.findAll() | end");
        return teachers;
    }
    
    public Teacher findOne(int id) throws DaoException {
        log.trace("findOne() | call AbstractDAO.findOne() | start");
        String sql = "select * from teachers where id=?;";
        Teacher teacher = super.findOne(id, sql);
        if (teacher == null) {
            log.info("findOne() | Teacher with ID = " + id + " was NOT found!");
        } else {
            log.info("findOne() | Teacher with ID = " + id + " was found | " + teacher.toString());
        }
        log.trace("findOne() | call AbstractDAO.findOne() | end");
        return teacher;
    }
    
    public Teacher create(String firstName, String lastName, String passport) throws DaoException {
        log.trace("create() | start");
        String sql = "insert into teachers (firstname,lastname,passport) values (?,?,?);";
        Teacher teacher = null;
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, passport);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted == 0) {
                log.error("create() | Teacher was NOT created!");
            }
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                teacher = collectOneElementFromResultSet(resultSet);
            } catch (SQLException e) {
                log.error("create() | Unable to create ResultSet", e);
                throw new DaoException("create() | Unable to create ResultSet", e);
            }
        } catch (SQLException e) {
            log.error("create() | Unable to create SQL resourses", e);
            throw new DaoException("create() | Unable to create SQL resourses", e);
        }
        return teacher;
    }
    
    public void update(int id, String firstName, String lastName, String passport) throws DaoException {
        log.trace("update() | start");
        String sql = "update teachers set firstname=?,lastname=?, passport=? where id=?;";
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, passport);
            statement.setInt(4, id);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                log.error("update() | Teacher with ID =  " + id + " was NOT updated!");
            } else {
                log.info("update() | Teacher with ID =  " + id + " was updated");
            }
        } catch (SQLException e) {
            log.error("update() | Unable to create SQL resourses", e);
            throw new DaoException("update() | Unable to create SQL resourses", e);
        }
        log.trace("update() | end");
    }
    
    public void delete(int id) throws DaoException {
        log.trace("delete() | call AbstractDAO.delete() | start");
        String sql = "delete from teachers as t where t.id = ?;";
        int rowsDeleted = super.delete(id, sql);
        if (rowsDeleted == 0) {
            log.error("delete() | Teacher with ID = " + id + " was NOT deleted!");
        } else {
            log.info("delete() | Teacher with ID = " + id + " was deleted");
        }
        log.trace("delete() | call AbstractDAO.delete() | end");
    }
    
    @Override
    protected List<Teacher> collectManyElementsFromResultSet(ResultSet resultSet) throws DaoException {
        List<Teacher> teachers = new ArrayList<>();
        int id = 0;
        String firstName = null;
        String lastName = null;
        String passport = null;
        log.trace("collectManyElementsFromResultSet() | Getting Teachers from ResultSet...");
        try {
            while (resultSet.next()) {
                id = resultSet.getInt("id");
                firstName = resultSet.getString("firstname");
                lastName = resultSet.getString("lastname");
                passport = resultSet.getString("passport");
                teachers.add(new Teacher(id, firstName, lastName, passport));
            }
        } catch (SQLException e) {
            log.error("collectManyElementsFromResultSet() | error while handling ResultSet with Teachers", e);
            throw new DaoException("collectManyElementsFromResultSet() | error while handling ResultSet with Teachers",
                    e);
        }
        return teachers;
    }
    
    @Override
    protected Teacher collectOneElementFromResultSet(ResultSet resultSet) throws DaoException {
        Teacher teacher = null;
        List<Teacher> teachers = collectManyElementsFromResultSet(resultSet);
        if (teachers.size() > 0) {
            teacher = teachers.get(0);
        }
        return teacher;
    }
}
