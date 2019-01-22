package net.imshenik.university.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import net.imshenik.university.domain.Student;

public class StudentDaoPostgres implements StudentDao {
    private static final Logger log = Logger.getLogger(StudentDaoPostgres.class.getName());
    private static final String TABLENAME = "STUDENTS";
    private static final String ID = "ID";
    private static final String FIRSTNAME = "FIRSTNAME";
    private static final String LASTNAME = "LASTNAME";
    private static final String GROUP_ID = "GROUP_ID";
    
    public List<Student> findAll() throws DaoException {
        log.trace("findAll() | start");
        String sql = String.format("SELECT * FROM %s", TABLENAME);
        List<Student> students = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            log.trace("findAll() | Getting Students from ResultSet...");
            while (resultSet.next()) {
                students.add(new Student(resultSet.getInt(ID), resultSet.getString(FIRSTNAME),
                        resultSet.getString(LASTNAME), resultSet.getInt(GROUP_ID)));
            }
        } catch (SQLException e) {
            log.error("findAll() | database: interaction failure ", e);
            throw new DaoException("findAll() | database: interaction failure", e);
        }
        log.trace("findAll() | end");
        return students;
    }
    
    public List<Student> findByGroup(Integer groupId) throws DaoException {
        log.trace("findByGroup() | start");
        String sql = String.format("SELECT * FROM %s where group_id=%d", TABLENAME, groupId);
        List<Student> students = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            log.trace("findByGroup() | Getting Students from ResultSet...");
            while (resultSet.next()) {
                students.add(new Student(resultSet.getInt(ID), resultSet.getString(FIRSTNAME),
                        resultSet.getString(LASTNAME), resultSet.getInt(GROUP_ID)));
            }
        } catch (SQLException e) {
            log.error("findByGroup() | database: interaction failure ", e);
            throw new DaoException("findByGroup() | database: interaction failure", e);
        }
        log.trace("findByGroup() | end");
        return students;
    }
    
    public Student findOne(Integer id) throws DaoException {
        log.trace("findOne() | start");
        String sql = String.format("SELECT * FROM %s WHERE ID=?", TABLENAME);
        Student student = null;
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
            try (ResultSet resultSet = statement.getResultSet()) {
                if (resultSet.next()) {
                    student = new Student(resultSet.getInt(ID), resultSet.getString(FIRSTNAME),
                            resultSet.getString(LASTNAME), resultSet.getInt(GROUP_ID));
                }
            }
        } catch (SQLException e) {
            log.error("findOne() | database: interaction failure", e);
            throw new DaoException("findOne() | database: interaction failure", e);
        }
        log.trace(student == null ? "findOne() | Student was NOT found!" : "findOne() | Student was found");
        log.trace("findOne() | end");
        return student;
    }
    
    public Student create(Student Student) throws DaoException {
        log.trace("create() | start");
        String sql = String.format("INSERT INTO %s (FIRSTNAME,LASTNAME,GROUP_ID) VALUES (?,?,?)", TABLENAME);
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, Student.getFirstName());
            statement.setString(2, Student.getLastName());
            statement.setInt(3, Student.getGroupId());
            if (statement.executeUpdate() == 1) {
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        Student.setId(resultSet.getInt(ID));
                        log.info("create() | Student was created | " + Student.toString());
                    }
                }
            }
        } catch (SQLException e) {
            log.error("create() | database: interaction failure", e);
            throw new DaoException("create() | database: interaction failure", e);
        }
        log.trace("create() | end");
        return Student;
    }
    
    public void update(Student student) throws DaoException {
        log.trace("update() | start");
        if (doesNotExist(student.getId())) {
            throw new DaoException("update() | Student with ID =  " + student.getId() + " does NOT exist!");
        }
        String sql = String.format("UPDATE %s SET FIRSTNAME=?,LASTNAME=?, GROUP_ID=? WHERE ID=?", TABLENAME);
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            statement.setInt(3, student.getGroupId());
            statement.setInt(4, student.getId());
            statement.executeUpdate();
            log.info("update() | Student with ID =  " + student.getId() + " was updated");
        } catch (SQLException e) {
            log.error("update() | database: interaction failure", e);
            throw new DaoException("update() | database: interaction failure", e);
        }
        log.trace("update() | end");
    }
    
    public void delete(Integer id) throws DaoException {
        log.trace("delete() | start");
        if (doesNotExist(id)) {
            throw new DaoException("delete() | Student with  ID = " + id + " does NOT exist!");
        }
        String sql = String.format("DELETE FROM %s WHERE ID = ?", TABLENAME);
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            log.info("delete() | Student with  ID = " + id + " was deleted");
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
