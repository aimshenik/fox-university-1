package net.imshenik.university.dao.postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;
import net.imshenik.university.domain.entities.Student;

public class DAOStudent {
    private static Logger logger = Logger.getLogger(DAOStudent.class.getName());
    
    public Set<Student> findAll() throws DAOException {
        logger.trace("Getting list of all students:");
        Set<Student> students = new HashSet<Student>();
        String sql = "select * FROM students;";
        try {
            Class.forName("org.postgresql.Driver");
            logger.trace("Trying to create Connection, PreparedStatement and ResultSet...");
            try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/university",
                    "andrey", "1234321");
                    PreparedStatement statement = connection.prepareStatement(sql);
                    ResultSet resultSet = statement.executeQuery();) {
                while (resultSet.next()) {
                    Student student = new Student();
                    student.setId(resultSet.getInt("id"));
                    student.setFirstName((resultSet.getString("firstname")));
                    student.setLastName((resultSet.getString("lastname")));
                    students.add(student);
                }
            } catch (Exception e) {
                logger.error("Unable to read all users from database", e);
                throw new DAOException("Unable to read all users from database", e);
            }
        } catch (ClassNotFoundException e) {
            logger.error("Unable to load driver org.postgresql.Driver", e);
            throw new DAOException("Unable to load driver org.postgresql.Driver", e);
        }
        return students;
    }
    
    public Student findOne(int id) throws DAOException {
        logger.trace("Finding Student by ID:");
        String sql = "select * from students where id=?;";
        try {
            Class.forName("org.postgresql.Driver");
            try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/university",
                    "andrey", "1234321"); PreparedStatement statement = connection.prepareStatement(sql);) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery();) {
                    resultSet.next();
                    Student student = new Student();
                    student.setId(resultSet.getInt("id"));
                    student.setFirstName((resultSet.getString("firstname")));
                    student.setLastName((resultSet.getString("lastname")));
                    return student;
                } catch (Exception e) {
                    logger.error("Unable to create ResultSet", e);
                    throw new DAOException("Unable to create ResultSet", e);
                }
            } catch (Exception e) {
                logger.error("Unable to create Connection", e);
                throw new DAOException("Unable to create Connection", e);
            }
        } catch (ClassNotFoundException e) {
            logger.error("Unable to load driver org.postgresql.Driver", e);
            throw new DAOException("Unable to load driver org.postgresql.Driver", e);
        }
    }
    
    public boolean create(String firstName, String lastName) throws DAOException {
        logger.trace("Creating new Student:");
        String sql = "insert into students (firstname,lastname) values (?,?);";
        try {
            Class.forName("org.postgresql.Driver");
            try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/university",
                    "andrey", "1234321"); PreparedStatement statement = connection.prepareStatement(sql);) {
                statement.setString(1, firstName);
                statement.setString(2, lastName);
                int rowsInserted = statement.executeUpdate();
                logger.trace("Inserted " + rowsInserted + " row(s)");
                return rowsInserted > 0 ? true : false;
            } catch (Exception e) {
                logger.error("Unable to open connection", e);
                throw new DAOException("Unable to open connection", e);
            }
        } catch (ClassNotFoundException e) {
            logger.error("Unable to load driver org.postgresql.Driver", e);
            throw new DAOException("Unable to load driver org.postgresql.Driver", e);
        }
    }
    
    public boolean update(int id, String firstName, String lastName) throws DAOException {
        logger.trace("Updating Student:");
        String sql = "update students set firstname=?,lastname=? where id=?;";
        try {
            Class.forName("org.postgresql.Driver");
            try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/university",
                    "andrey", "1234321"); PreparedStatement statement = connection.prepareStatement(sql);) {
                statement.setString(1, firstName);
                statement.setString(2, lastName);
                statement.setInt(3, id);
                int rowsUpdated = statement.executeUpdate();
                logger.trace("Updated " + rowsUpdated + " row(s)");
                return rowsUpdated > 0 ? true : false;
            } catch (Exception e) {
                logger.error("Unable to open connection", e);
                throw new DAOException("Unable to open connection", e);
            }
        } catch (ClassNotFoundException e) {
            logger.error("Unable to load driver org.postgresql.Driver", e);
            throw new DAOException("Unable to load driver org.postgresql.Driver", e);
        }
    }
    
    public boolean delete(int id) throws DAOException {
        logger.trace("Updating Student:");
        String sql = "delete from students as s where s.id = ?;";
        try {
            Class.forName("org.postgresql.Driver");
            try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/university",
                    "andrey", "1234321"); PreparedStatement statement = connection.prepareStatement(sql);) {
                statement.setInt(1, id);
                int rowsDeleted = statement.executeUpdate();
                logger.trace("Deleted " + rowsDeleted + " row(s)");
                return rowsDeleted > 0 ? true : false;
            } catch (Exception e) {
                logger.error("Unable to open connection", e);
                throw new DAOException("Unable to open connection", e);
            }
        } catch (ClassNotFoundException e) {
            logger.error("Unable to load driver org.postgresql.Driver", e);
            throw new DAOException("Unable to load driver org.postgresql.Driver", e);
        }
    }
}
