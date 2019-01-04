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

public class TeacherDaoPostgres implements TeacherDao {
    private static final Logger log = Logger.getLogger(TeacherDaoPostgres.class.getName());
    private static final String TABLENAME = "TEACHERS";
    private static final String ID = "ID";
    private static final String FIRSTNAME = "FIRSTNAME";
    private static final String LASTNAME = "LASTNAME";
    private static final String PASSPORT = "PASSPORT";

    public List<Teacher> findAll() throws DaoException {
        log.trace("findAll() | start");
        String sql = String.format("SELECT * FROM %s", TABLENAME);
        List<Teacher> teachers = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            log.trace("findAll() | Getting Teachers from ResultSet...");
            while (resultSet.next()) {
                teachers.add(new Teacher(resultSet.getInt(ID), resultSet.getString(FIRSTNAME),
                        resultSet.getString(LASTNAME), resultSet.getString(PASSPORT)));
            }
        } catch (SQLException e) {
            log.error("findAll() | database: interaction failure ", e);
            throw new DaoException("findAll() | database: interaction failure", e);
        }
        log.trace("findAll() | end");
        return teachers;
    }

    public Teacher findOne(Integer id) throws DaoException {
        log.trace("findOne() | start");
        String sql = String.format("SELECT * FROM %s WHERE ID=?", TABLENAME);
        Teacher teacher = null;
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
            try (ResultSet resultSet = statement.getResultSet()) {
                if (resultSet.next()) {
                    teacher = new Teacher(resultSet.getInt(ID), resultSet.getString(FIRSTNAME),
                            resultSet.getString(LASTNAME), resultSet.getString(PASSPORT));
                }
            }
        } catch (SQLException e) {
            log.error("findOne() | database: interaction failure", e);
            throw new DaoException("findOne() | database: interaction failure", e);
        }
        log.trace(teacher == null ? "findOne() | Teacher was NOT found!" : "findOne() | Teacher was found");
        log.trace("findOne() | end");
        return teacher;
    }

    public Teacher create(Teacher teacher) throws DaoException {
        log.trace("create() | start");
        String sql = String.format("INSERT INTO %s (FIRSTNAME,LASTNAME,PASSPORT) VALUES (?,?,?)", TABLENAME);
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, teacher.getFirstName() );
            statement.setString(2, teacher.getLastName());
            statement.setString(3, teacher.getPassport());
            if (statement.executeUpdate() == 1) {
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        teacher.setId(resultSet.getInt(ID));
                        log.info("create() | Teacher was created | " + teacher.toString());
                    }
                }
            }
        } catch (SQLException e) {
            log.error("create() | database: interaction failure", e);
            throw new DaoException("create() | database: interaction failure", e);
        }
        log.trace("create() | end");
        return teacher;
    }

    public void update(Teacher teacher) throws DaoException {
        log.trace("update() | start");
        if (doesNotExist(teacher.getId())) {
            throw new DaoException("update() | Teacher with ID =  " + teacher.getId() + " does NOT exist!");
        }
        String sql = String.format("UPDATE %s SET FIRSTNAME=?,LASTNAME=?, PASSPORT=? WHERE ID=?", TABLENAME);
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, teacher.getFirstName());
            statement.setString(2, teacher.getLastName());
            statement.setString(3, teacher.getPassport());
            statement.setInt(4, teacher.getId());
            statement.executeUpdate();
            log.info("update() | Teacher with ID =  " + teacher.getId() + " was updated");
        } catch (SQLException e) {
            log.error("update() | database: interaction failure", e);
            throw new DaoException("update() | database: interaction failure", e);
        }
        log.trace("update() | end");
    }

    public void delete(Integer id) throws DaoException {
        log.trace("delete() | start");
        if (doesNotExist(id)) {
            throw new DaoException("delete() | Teacher with  ID = " + id + " does NOT exist!");
        }
        String sql = String.format("DELETE FROM %s WHERE ID = ?", TABLENAME);
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            log.info("delete() | Teacher with  ID = " + id + " was deleted");
        } catch (SQLException e) {
            log.error("delete() | database: interaction failure", e);
            throw new DaoException("delete() | database: interaction failure", e);
        }
        log.trace("delete() | end");
    }

    private boolean doesNotExist(Integer id) throws DaoException {
        if (id == null) {
            return true;
        }
        boolean notFound = true;
        String sql = String.format("SELECT EXISTS(SELECT 1 FROM %s WHERE ID=?)", TABLENAME);
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                if (resultSet.getBoolean(1) == true) {
                    notFound = false;
                }
            }
        } catch (SQLException e) {
            throw new DaoException("exist() | database: interaction failure", e);
        }
        return notFound;
    }
}
