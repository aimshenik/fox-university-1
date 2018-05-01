package net.imshenik.university.dao.postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;
import net.imshenik.university.domain.entities.Student;

public class DAOStudent {
    private static Logger logger = Logger.getLogger(DAOStudent.class.getName());
    
    public Set<Student> getAllStudents() throws DAOException {
        logger.trace("Getting list of all students:");
        Set<Student> students = new HashSet<Student>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            Class.forName("org.postgresql.Driver");
            logger.trace("Opening connection...");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/university", "andrey",
                    "1234321");
            String sql = "select * FROM students;";
            statement = connection.prepareStatement(sql);
            logger.trace("Executing SQL statement...");
            resultSet = statement.executeQuery();
            logger.trace("Data retrieved, going to iterate...");
            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setFirstName((resultSet.getString("firstname")));
                student.setLastName((resultSet.getString("lastname")));
                students.add(student);
            }
            logger.trace("ResultSet provided " + students.size() + "entities");
        } catch (Exception e) {
            throw new DAOException("Unable to read all users from database");
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
            }
        }
        return students;
    }
}
