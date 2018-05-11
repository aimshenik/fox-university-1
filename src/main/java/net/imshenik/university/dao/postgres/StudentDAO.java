package net.imshenik.university.dao.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;
import net.imshenik.university.domain.entities.Student;

public class StudentDAO extends AbstractDAO {
    private static final Logger log = Logger.getLogger(StudentDAO.class.getName());
    
    public StudentDAO() throws DAOException {
        super();
    }
    
    public Set<Student> findAll() throws DAOException {
        log.trace("findAll() | Getting list of all students:");
        Set<Student> students = null;
        String sql = "select * from students;";
        log.trace("findAll() | Creating Connection, PreparedStatement and ResultSet...");
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();) {
            log.trace("findAll() | Iterating by ResultSet...");
            students = new HashSet<Student>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                int group_id = resultSet.getInt("group_id");
                students.add(new Student(id, firstName, lastName, group_id));
            }
            log.info("findAll() | All " + students.size() + " students found");
        } catch (SQLException e) {
            log.error("findAll() | Unable to read all students from database", e);
            throw new DAOException("findAll() | Unable to read all students from database", e);
        }
        return students;
    }
    
    public Student findOne(int id) throws DAOException {
        log.trace("findOne() | Finding student with ID = " + id);
        String sql = "select * from students where id=?;";
        Student student = null;
        log.trace("findOne() | Creating Connection and PreparedStatement...");
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, id);
            log.trace("findOne() | Creating ResultSet...");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String firstName = resultSet.getString("firstname");
                    String lastName = resultSet.getString("lastname");
                    student = new Student(id, firstName, lastName);
                    log.info("findOne() | Found student with ID = " + id + " : " + student.toString());
                }
                log.info("findOne() | Unable to find student with ID = " + id);
            }
        } catch (SQLException e) {
            log.error("findOne() | Unable to create Connection", e);
            throw new DAOException("findOne() | Unable to create Connection", e);
        }
        return student;
    }
    
    public Student create(String firstName, String lastName) throws DAOException {
        log.trace("create() | Creating new student with First Name = " + firstName + " and Last Name = " + lastName);
        String sql = "insert into students (firstname,lastname) values (?,?);";
        Student student = null;
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted == 0) {
                log.error("create() | New student with first name = " + firstName + " and last name = " + lastName
                        + " was NOT created!");
            }
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    student = new Student(id, firstName, lastName);
                    log.info("create() | Created new student : " + student.toString());
                }
            }
        } catch (SQLException e) {
            log.error("create() | Unable to open connection", e);
            throw new DAOException("create() | Unable to open connection", e);
        }
        return student;
    }
    
    public void update(int id, String firstName, String lastName, int group_id) throws DAOException {
        log.trace("update() | Updating Student with id = " + id);
        String sql = "update students set firstname=?,lastname=?, group_id=? where id=?;";
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setInt(3, group_id);
            statement.setInt(4, id);
            log.info("update() | Before update : " + this.findOne(id).toString());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                log.error("update() | Student with ID =  " + id + " was NOT updated!");
            } else {
                log.info("update() | After update " + this.findOne(id).toString());
            }
        } catch (SQLException e) {
            log.error("update() | Unable to open connection", e);
            throw new DAOException("update() | Unable to open connection", e);
        }
    }
    
    public void delete(int id) throws DAOException {
        log.trace("delete() | Deleting student with ID = " + id);
        String sql = "delete from students as s where s.id = ?;";
        log.trace("delete() | Creating Connection and PreparedStatement...");
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted == 0) {
                log.error("delete() | Student with ID =  " + id + " was NOT deleted!");
            } else {
                log.info("delete() | Student with ID =  " + id + " was deleted");
            }
        } catch (SQLException e) {
            log.error("delete() | Unable to open connection", e);
            throw new DAOException("delete() | Unable to open connection", e);
        }
    }
}
