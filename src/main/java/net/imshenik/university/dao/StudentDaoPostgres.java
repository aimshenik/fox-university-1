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

public class StudentDaoPostgres {
    private static final Logger log = Logger.getLogger(StudentDaoPostgres.class.getName());
    
    public List<Student> findAll() throws DaoException {
        log.trace("findAll() | start");
        String sql = "select * from students";
        List<Student> students = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            log.trace("findAll() | Getting Students from ResultSet...");
            while (resultSet.next()) {
                students.add(new Student(resultSet.getInt("id"), resultSet.getString("firstname"),
                        resultSet.getString("lastname"), resultSet.getInt("group_id")));
            }
        } catch (SQLException e) {
            log.error("findAll() | database: interaction failure ", e);
            throw new DaoException("findAll() | database: interaction failure", e);
        }
        log.trace("findAll() | end");
        return students;
    }
    
    public Student findOne(Integer id) throws DaoException {
        log.trace("findOne() | start");
        String sql = "select * from students where id=?";
        Student student = null;
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
            try (ResultSet resultSet = statement.getResultSet()) {
                if (resultSet.next()) {
                    student = new Student(resultSet.getInt("id"), resultSet.getString("firstname"),
                            resultSet.getString("lastname"), resultSet.getInt("group_id"));
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
    
    public Student create(Student student) throws DaoException {
        log.trace("create() | start");
        String sql = "insert into students (firstname, lastname, group_id) values (?,?,?)";
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            if (student.getGroupId() == 0) {
                statement.setNull(3, java.sql.Types.NULL);
            } else {
                statement.setInt(3, student.getGroupId());
            }
            if (statement.executeUpdate() == 1) {
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        student.setId(resultSet.getInt("Id"));
                        log.info("create() | Student was created | " + student.toString());
                    }
                }
            }
        } catch (SQLException e) {
            log.error("create() | database: interaction failure", e);
            throw new DaoException("create() | database: interaction failure", e);
        }
        log.trace("create() | end");
        return student;
    }
    
    public void update(Student student) throws DaoException {
        log.trace("update() | start");
        if (doesNotExist(student.getId())) {
            throw new DaoException("update() | Student with ID =  " + student.getId() + " does NOT exist!");
        }
        String sql = "update students set firstname=?,lastname=?, group_id=? where id=?";
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            if (student.getGroupId() == 0) {
                statement.setNull(3, java.sql.Types.NULL);
            } else {
                statement.setInt(3, student.getGroupId());
            }            statement.setInt(4, student.getId());
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
        String sql = "delete from students as s where s.id = ?";
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
        boolean notFound = true;
        String sql = "select exists(select 1 from students where id=?)";
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
