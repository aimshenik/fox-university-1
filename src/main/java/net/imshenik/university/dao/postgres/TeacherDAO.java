package net.imshenik.university.dao.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import net.imshenik.university.domain.entities.Teacher;

public class TeacherDAO extends AbstractDAO<Teacher> {
    
    private static final Logger log = Logger.getLogger(TeacherDAO.class.getName());
    
    public TeacherDAO() throws DAOException {
        super();
    }
    
    public Set<Teacher> findAll() throws DAOException {
        log.trace("findAll() | Getting list of all teachers:");
        Set<Teacher> teachers = null;
        String sql = "select * from teachers;";
        log.trace("findAll() | Creating Connection, PreparedStatement and ResultSet...");
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();) {
            log.trace("findAll() | Iterating by ResultSet...");
            teachers = new HashSet<Teacher>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                String passport = resultSet.getString("passport");
                teachers.add(new Teacher(id, firstName, lastName, passport));
            }
            log.info("findAll() | All " + teachers.size() + " teachers found");
        } catch (SQLException e) {
            log.error("findAll() | Unable to read all teachers from database", e);
            throw new DAOException("findAll() | Unable to read all teachers from database", e);
        }
        return teachers;
    }
    
    public Teacher findOne(int id) throws DAOException {
        log.trace("findOne() | Finding teacher with ID = " + id);
        String sql = "select * from teachers where id=?;";
        Teacher teacher = null;
        log.trace("findOne() | Creating Connection and PreparedStatement...");
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, id);
            log.trace("findOne() | Creating ResultSet...");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String firstName = resultSet.getString("firstname");
                    String lastName = resultSet.getString("lastname");
                    String passport = resultSet.getString("passport");
                    teacher = new Teacher(id, firstName, lastName, passport);
                    log.info("findOne() | Found teacher with ID = " + id + " : " + teacher.toString());
                }
                log.info("findOne() | Unable to find teacher with ID = " + id);
            }
        } catch (SQLException e) {
            log.error("findOne() | Unable to create Connection", e);
            throw new DAOException("findOne() | Unable to create Connection", e);
        }
        return teacher;
    }
    
    public Teacher create(String firstName, String lastName, String passport) throws DAOException {
        log.trace("create() | Creating new teacher with First Name = " + firstName + " and Last Name = " + lastName);
        String sql = "insert into teachers (firstname,lastname,passport) values (?,?,?);";
        Teacher teacher = null;
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, passport);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted == 0) {
                log.error("create() | New teacher with first name = " + firstName + " and last name = " + lastName
                        + " was NOT created!");
            }
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    teacher = new Teacher(id, firstName, lastName, passport);
                    log.info("create() | Created new teacher : " + teacher.toString());
                }
            }
        } catch (SQLException e) {
            log.error("create() | Unable to open connection", e);
            throw new DAOException("create() | Unable to open connection", e);
        }
        return teacher;
    }
    
    public void update(int id, String firstName, String lastName, String passport) throws DAOException {
        log.trace("update() | Updating Teacher with id = " + id);
        String sql = "update teachers set firstname=?,lastname=?, passport=? where id=?;";
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, passport);
            statement.setInt(4, id);
            log.info("update() | Before update : " + this.findOne(id).toString());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                log.error("update() | Teacher with ID =  " + id + " was NOT updated!");
            } else {
                log.info("update() | After update " + this.findOne(id).toString());
            }
        } catch (SQLException e) {
            log.error("update() | Unable to open connection", e);
            throw new DAOException("update() | Unable to open connection", e);
        }
    }
    
    public void delete(int id) throws DAOException {
        log.trace("delete() | Deleting teacher with ID = " + id);
        String sql = "delete from teachers as t where t.id = ?;";
        log.trace("delete() | Creating Connection and PreparedStatement...");
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted == 0) {
                log.error("delete() | Teacher with ID =  " + id + " was NOT deleted!");
            } else {
                log.info("delete() | Teacher with ID =  " + id + " was deleted");
            }
        } catch (SQLException e) {
            log.error("delete() | Unable to open connection", e);
            throw new DAOException("delete() | Unable to open connection", e);
        }
    }
    
    @Override
    protected List<Teacher> retrieveAllEntitiesFromResultSet(ResultSet resultSet) throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    protected Teacher retrieveOneEntityFromResultSet(ResultSet resultSet) throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }
}
