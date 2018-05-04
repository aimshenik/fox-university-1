package net.imshenik.university.dao.postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;
import net.imshenik.university.domain.entities.Student;

public class DAOStudent {
    private static final Logger LOGGER     = Logger.getLogger(DAOStudent.class.getName());
    private static final String driverName = "org.postgresql.Driver";
    
    public Set<Student> findAll() throws DAOException {
        LOGGER.trace("Getting list of all students:");
        Set<Student> students = new HashSet<Student>();
        String sql = "select * from students;";
        try {
            Class.forName(driverName);
            LOGGER.trace("Creating Connection, PreparedStatement and ResultSet...");
            try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/university",
                    "andrey", "1234321");
                    PreparedStatement statement = connection.prepareStatement(sql);
                    ResultSet resultSet = statement.executeQuery();) {
                LOGGER.trace("Iterating by ResultSet...");
                while (resultSet.next()) {
                    Student student = new Student();
                    student.setId(resultSet.getInt("id"));
                    student.setFirstName((resultSet.getString("firstname")));
                    student.setLastName((resultSet.getString("lastname")));
                    students.add(student);
                }
                LOGGER.info("All " + students.size() + " students found");
            } catch (Exception e) {
                LOGGER.error("Unable to read all users from database", e);
                throw new DAOException("Unable to read all users from database", e);
            }
        } catch (ClassNotFoundException e) {
            LOGGER.fatal("Unable to load driver " + driverName, e);
            throw new DAOException("Unable to load driver " + driverName, e);
        }
        return students;
    }
    
    public Student findOne(int id) throws DAOException {
        LOGGER.trace("Finding student with ID = " + id);
        String sql = "select * from students where id=?;";
        try {
            Class.forName(driverName);
            LOGGER.trace("Creating Connection and PreparedStatement...");
            try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/university",
                    "andrey", "1234321"); PreparedStatement statement = connection.prepareStatement(sql);) {
                statement.setInt(1, id);
                LOGGER.trace("Creating ResultSet...");
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Student student = new Student();
                        student.setId(resultSet.getInt("id"));
                        student.setFirstName((resultSet.getString("firstname")));
                        student.setLastName((resultSet.getString("lastname")));
                        LOGGER.info("Found student with ID = " + id + " : " + student.toString());
                        return student;
                    }
                    LOGGER.warn("Unable to find student with ID = " + id);
                    return null;
                }
            } catch (Exception e) {
                LOGGER.error("Unable to create Connection", e);
                throw new DAOException("Unable to create Connection", e);
            }
        } catch (ClassNotFoundException e) {
            LOGGER.fatal("Unable to load driver " + driverName, e);
            throw new DAOException("Unable to load driver " + driverName, e);
        }
    }
    
    public Student create(String firstName, String lastName) throws DAOException {
        LOGGER.trace("Creating new student with First Name = " + firstName + " and Last Name = " + lastName);
        String sql = "insert into students (firstname,lastname) values (?,?);";
        String fName = inspectFirstName(firstName);
        String lName = inspectLastName(lastName);
        try {
            Class.forName(driverName);
            try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/university",
                    "andrey", "1234321");
                    PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
                statement.setString(1, fName);
                statement.setString(2, lName);
                int rowsInserted = statement.executeUpdate();
                LOGGER.trace("Inserted " + rowsInserted + " row(s)");
                if (rowsInserted == 0) {
                    throw new DAOException("Inserted  " + rowsInserted + " row(s)");
                }
                try (ResultSet generatedKey = statement.getGeneratedKeys()) {
                    generatedKey.next();
                    int id = generatedKey.getInt(1);
                    Student student = new Student(id, fName, lName);
                    LOGGER.info("Inserted student : " + student.toString());
                    return student;
                }
            } catch (Exception e) {
                LOGGER.error("Unable to open connection", e);
                throw new DAOException("Unable to open connection", e);
            }
        } catch (ClassNotFoundException e) {
            LOGGER.fatal("Unable to load driver " + driverName, e);
            throw new DAOException("Unable to load driver " + driverName, e);
        }
    }
    
    public void update(int id, String firstName, String lastName) throws DAOException {
        LOGGER.trace("Updating Student:");
        String sql = "update students set firstname=?,lastname=? where id=?;";
        try {
            Class.forName(driverName);
            try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/university",
                    "andrey", "1234321"); PreparedStatement statement = connection.prepareStatement(sql);) {
                statement.setString(1, firstName);
                statement.setString(2, lastName);
                statement.setInt(3, id);
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated == 0) {
                    LOGGER.warn("Updated " + rowsUpdated + " row(s)");
                    throw new DAOException("Updated " + rowsUpdated + " row(s)");
                } else {
                    LOGGER.info("Updated " + rowsUpdated + " row(s)");
                }
            } catch (Exception e) {
                LOGGER.error("Unable to open connection", e);
                throw new DAOException("Unable to open connection", e);
            }
        } catch (ClassNotFoundException e) {
            LOGGER.fatal("Unable to load driver " + driverName, e);
            throw new DAOException("Unable to load driver " + driverName, e);
        }
    }
    
    public void delete(int id) throws DAOException {
        LOGGER.trace("Deleting student with ID = " + id);
        String sql = "delete from students as s where s.id = ?;";
        try {
            Class.forName(driverName);
            LOGGER.trace("Creating Connection and PreparedStatement...");
            try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/university",
                    "andrey", "1234321"); PreparedStatement statement = connection.prepareStatement(sql);) {
                statement.setInt(1, id);
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted == 0) {
                    LOGGER.warn("Deleted " + rowsDeleted + " row(s)");
                    throw new DAOException("Updated " + rowsDeleted + " row(s)");
                } else {
                    LOGGER.info("Deleted " + rowsDeleted + " row(s)");
                }
            } catch (Exception e) {
                LOGGER.error("Unable to open connection", e);
                throw new DAOException("Unable to open connection", e);
            }
        } catch (ClassNotFoundException e) {
            LOGGER.fatal("Unable to load driver " + driverName, e);
            throw new DAOException("Unable to load driver " + driverName, e);
        }
    }
    
    private String inspectFirstName(String firstName) throws DAOException {
        if (firstName == null) {
            LOGGER.error("Unable to insert 'null' as first name");
            throw new DAOException("Unable to insert 'null' as first name");
        } else if (firstName.trim().length() == 0) {
            LOGGER.error("Unable to insert '' as first name");
            throw new DAOException("Unable to insert '' as first name");
        } else {
            return firstName.trim();
        }            
    }
    
    private String inspectLastName(String lastName) throws DAOException {
        if (lastName == null) {
            LOGGER.error("Unable to insert 'null' as last name");
            throw new DAOException("Unable to insert 'null' as last name");
        } else if (lastName.trim().length() == 0) {
            LOGGER.error("Unable to insert '' as last name");
            throw new DAOException("Unable to insert '' as last name");
        } else {
            return lastName.trim();
        }            
    }
}
