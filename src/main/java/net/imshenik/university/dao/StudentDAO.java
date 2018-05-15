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

public class StudentDAO extends AbstractDAO<Student> {
    
    private static final Logger log = Logger.getLogger(StudentDAO.class.getName());
    
    public List<Student> findAll() throws DAOException {
        log.trace("findAll() | call AbstractDAO.findAll() | start");
        String sql = "select * from students;";
        List<Student> students = super.findAll(sql);
        log.trace("findAll() | call AbstractDAO.findAll() | end");
        return students;
    }
    
    public Student findOne(int id) throws DAOException {
        log.trace("findOne() | call AbstractDAO.findOne() | start");
        String sql = "select * from students where id=?;";
        Student student = super.findOne(id, sql);
        if (student == null) {
            log.info("findOne() | Student with ID = " + id + " was NOT found!");
        } else {
            log.info("findOne() | Student with ID = " + id + " was found | " + student.toString());
        }
        log.trace("findOne() | call AbstractDAO.findOne() | end");
        return student;
    }
    
    public Student create(String firstName, String lastName) throws DAOException {
        log.trace("create() | start");
        String sql = "insert into students (firstname,lastname) values (?,?);";
        Student student = null;
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted == 0) {
                log.error("create() | Student was NOT created!");
            }
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                student = collectOneElementFromResultSet(resultSet);
            } catch (SQLException e) {
                log.error("create() | Unable to create ResultSet", e);
                throw new DAOException("create() | Unable to create ResultSet", e);
            }
        } catch (SQLException e) {
            log.error("create() | Unable to open connection", e);
            throw new DAOException("create() | Unable to open connection", e);
        }
        return student;
    }
    
    public void update(int id, String firstName, String lastName, int group_id) throws DAOException {
        log.trace("update() | start");
        String sql = "update students set firstname=?,lastname=?, group_id=? where id=?;";
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setInt(3, group_id);
            statement.setInt(4, id);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                log.error("update() |   Student with ID =  " + id + " was NOT updated!");
            } else {
                log.info("update()  |    Student with ID =  " + id + " was updated");
            }
        } catch (SQLException e) {
            log.error("update() |   Unable to create SQL resourses", e);
            throw new DAOException("update()    |   Unable to create SQL resourses", e);
        }
        log.trace("update() | end");
    }
    
    public void delete(int id) throws DAOException {
        log.trace("delete() | call AbstractDAO.delete() |   start");
        String sql = "delete from students as s where s.id = ?;";
        int rowsDeleted = super.delete(id, sql);
        if (rowsDeleted == 0) {
            log.error("deleteEntity()   | Student with ID = " + id + " was NOT deleted!");
        } else {
            log.info("deleteEntity()    | Student with ID = " + id + " was deleted");
        }
        log.trace("delete() | call AbstractDAO.delete() | end");
    }
    
    @Override
    protected List<Student> collectManyElementsFromResultSet(ResultSet resultSet) throws DAOException {
        List<Student> students = new ArrayList<>();
        int id = 0;
        String firstName = null;
        String lastName = null;
        log.trace("collectManyElementsFromResultSet() | Getting Students from ResultSet...");
        try {
            while (resultSet.next()) {
                id = resultSet.getInt("id");
                firstName = resultSet.getString("firstname");
                lastName = resultSet.getString("lastname");
                students.add(new Student(id, firstName, lastName));
            }
        } catch (SQLException e) {
            log.error("collectManyElementsFromResultSet() | error while handling ResultSet with Students", e);
            throw new DAOException("collectManyElementsFromResultSet() | error while handling ResultSet with Students",
                    e);
        }
        return students;
    }
    
    @Override
    protected Student collectOneElementFromResultSet(ResultSet resultSet) throws DAOException {
        Student student = null;
        List<Student> students = collectManyElementsFromResultSet(resultSet);
        if (students.size() > 0) {
            student = students.get(0);
        }
        return student;
    }
}
