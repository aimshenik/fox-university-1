package net.imshenik.university.dao.postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;
import net.imshenik.university.domain.entities.Group;


public class GroupDAO {
    private static final Logger LOGGER = Logger.getLogger(StudentDAO.class.getName());
    private static final String driver = "org.postgresql.Driver";

    public Set<Group> findAll() throws DAOException {
        LOGGER.trace("Getting list of all students:");
        Set<Group> groups = new HashSet<Group>();
        String sql = "select * from groups;";
        try {
            Class.forName(driver);
            LOGGER.trace("Creating Connection, PreparedStatement and ResultSet...");
            try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/university",
                    "andrey", "1234321");
                    PreparedStatement statement = connection.prepareStatement(sql);
                    ResultSet resultSet = statement.executeQuery();) {
                LOGGER.trace("Iterating by ResultSet...");
                while (resultSet.next()) {
                    Group grpup = new Group();
                    grpup.setId(resultSet.getInt("id"));
                    grpup.setName(resultSet.getString("name"));
                    groups.add(grpup);
                }
                LOGGER.info("All " + groups.size() + " groups found");
            } catch (Exception e) {
                LOGGER.error("Unable to read all groups from database", e);
                throw new DAOException("Unable to read all groups from database", e);
            }
        } catch (ClassNotFoundException e) {
            LOGGER.fatal("Unable to load driver " + driver, e);
            throw new DAOException("Unable to load driver " + driver, e);
        }
        return groups;
    }

    public Group findOne(int id) throws DAOException {
        LOGGER.trace("Finding student with ID = " + id);
        String sql = "select * from group where id=?;";
        try {
            Class.forName(driver);
            LOGGER.trace("Creating Connection and PreparedStatement...");
            try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/university",
                    "andrey", "1234321"); PreparedStatement statement = connection.prepareStatement(sql);) {
                statement.setInt(1, id);
                LOGGER.trace("Creating ResultSet...");
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Group group = new Group();
                        group.setId(resultSet.getInt("id"));
                        group.setName(resultSet.getString("name"));
                        LOGGER.info("Found group with ID = " + id + " : " + group.toString());
                        return group;
                    }
                    LOGGER.warn("Unable to find group with ID = " + id);
                    return null;
                }
            } catch (Exception e) {
                LOGGER.error("Unable to create Connection", e);
                throw new DAOException("Unable to create Connection", e);
            }
        } catch (ClassNotFoundException e) {
            LOGGER.fatal("Unable to load driver " + driver, e);
            throw new DAOException("Unable to load driver " + driver, e);
        }
    }

    public Group create(String name) throws DAOException {
        LOGGER.trace("Creating new group with Name = " + name);
        String sql = "insert into groups (name) values (?);";
        try {
            Class.forName(driver);
            try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/university",
                    "andrey", "1234321");
                    PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
                statement.setString(1, name);
                int rowsInserted = statement.executeUpdate();
                LOGGER.trace("Inserted " + rowsInserted + " row(s)");
                if (rowsInserted == 0) {
                    throw new DAOException("Inserted  " + rowsInserted + " row(s)");
                }
                try (ResultSet generatedKey = statement.getGeneratedKeys()) {
                    generatedKey.next();
                    int id = generatedKey.getInt(1);
                    Group student = new Group();
                    LOGGER.info("Inserted student : " + student.toString());
                    return student;
                }
            } catch (Exception e) {
                LOGGER.error("Unable to open connection", e);
                throw new DAOException("Unable to open connection", e);
            }
        } catch (ClassNotFoundException e) {
            LOGGER.fatal("Unable to load driver " + driver, e);
            throw new DAOException("Unable to load driver " + driver, e);
        }
    }

    public void update(int id, String firstName, String lastName) throws DAOException {
        LOGGER.trace("Updating Student:");
        String sql = "update students set firstname=?,lastname=? where id=?;";
        try {
            Class.forName(driver);
            try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/university",
                    "andrey", "1234321"); PreparedStatement statement = connection.prepareStatement(sql);) {
                statement.setString(1, firstName);
                statement.setString(2, lastName);
                statement.setInt(3, id);
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated == 0) {
                    LOGGER.warn("Updated " + rowsUpdated + " row(s)");
                } else {
                    LOGGER.info("Updated " + rowsUpdated + " row(s)");
                }
            } catch (Exception e) {
                LOGGER.error("Unable to open connection", e);
                throw new DAOException("Unable to open connection", e);
            }
        } catch (ClassNotFoundException e) {
            LOGGER.fatal("Unable to load driver " + driver, e);
            throw new DAOException("Unable to load driver " + driver, e);
        }
    }

    public void delete(int id) throws DAOException {
        LOGGER.trace("Deleting student with ID = " + id);
        String sqlGroupStudent = "delete from group_student as gs where gs.student_id = ?;";
        String sqlStudents = "delete from students as s where s.id = ?;";
        try {
            Class.forName(driver);
            LOGGER.trace("Creating Connection and PreparedStatement...");
            try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/university",
                    "andrey", "1234321");
                    PreparedStatement statementGS = connection.prepareStatement(sqlGroupStudent);
                    PreparedStatement statementS = connection.prepareStatement(sqlStudents);) {
                statementGS.setInt(1, id);
                statementGS.execute();
                statementS.setInt(1, id);
                int rowsDeleted = statementS.executeUpdate();
                if (rowsDeleted == 0) {
                    LOGGER.warn("Deleted " + rowsDeleted + " row(s)");
                } else {
                    LOGGER.info("Deleted " + rowsDeleted + " row(s)");
                }
            } catch (Exception e) {
                LOGGER.error("Unable to open connection", e);
                throw new DAOException("Unable to open connection", e);
            }
        } catch (ClassNotFoundException e) {
            LOGGER.fatal("Unable to load driver " + driver, e);
            throw new DAOException("Unable to load driver " + driver, e);
        }
    }
}
