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
import net.imshenik.university.domain.entities.Subject;

public class SubjectDAO extends AbstractDAO<Subject> {

    public SubjectDAO() throws DAOException {
	super();
    }

    private static final Logger log = Logger.getLogger(SubjectDAO.class.getName());

    public Set<Subject> findAll() throws DAOException {
	log.trace("findAll() | Getting list of all subjects:");
	Set<Subject> subjects = null;
	String sql = "select * from subjects;";
	log.trace("findAll() | Creating Connection, PreparedStatement and ResultSet...");
	try (Connection connection = getConnection();
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();) {
	    log.trace("findAll() | Iterating by ResultSet...");
	    subjects = new HashSet<Subject>();
	    while (resultSet.next()) {
		int id = resultSet.getInt("id");
		String name = resultSet.getString("name");
		subjects.add(new Subject(id, name));
	    }
	    log.info("findAll() | All " + subjects.size() + " subjects found");
	} catch (SQLException e) {
	    log.error("findAll() | Unable to read all subjects from database", e);
	    throw new DAOException("findAll() | Unable to read all subjects from database", e);
	}
	return subjects;
    }

    public Subject findOne(int id) throws DAOException {
	log.trace("findOne() | Finding subject with ID = " + id);
	String sql = "select * from subjects where id=?;";
	Subject subject = null;
	log.trace("findOne() | Creating Connection and PreparedStatement...");
	try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql);) {
	    statement.setInt(1, id);
	    log.trace("findOne() | Creating ResultSet...");
	    try (ResultSet resultSet = statement.executeQuery()) {
		log.trace("findOne() | Iterating by ResultSet...");
		while (resultSet.next()) {
		    String name = resultSet.getString("name");
		    subject = new Subject(id, name);
		    log.info("findOne() | Found subject with ID = " + id + " : " + subject.toString());
		}
		log.info("findOne() | Unable to find subject with ID = " + id);
	    }
	} catch (SQLException e) {
	    log.error("findOne() | Unable to create Connection", e);
	    throw new DAOException("findOne() | Unable to create Connection", e);
	}
	return subject;
    }

    public Subject create(String name) throws DAOException {
	log.trace("create() | Creating new subject with name = " + name);
	String sql = "insert into subjects (name) values (?);";
	Subject subject = null;
	try (Connection connection = getConnection();
		PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
	    statement.setString(1, name);
	    int rowsInserted = statement.executeUpdate();
	    if (rowsInserted == 0) {
		log.error("create() | New subject with name = " + name + " was NOT created!");
	    }
	    try (ResultSet resultSet = statement.getGeneratedKeys()) {
		while (resultSet.next()) {
		    int id = resultSet.getInt(1);
		    subject = new Subject(id, name);
		    log.info("create() | Created new subject : " + subject.toString());
		}
	    }
	} catch (SQLException e) {
	    log.error("create() | Unable to open connection", e);
	    throw new DAOException("create() | Unable to open connection", e);
	}
	return subject;
    }

    public void update(int id, String name) throws DAOException {
	log.trace("update() | Updating subject with id = " + id);
	String sql = "update subjects set name=? where id=?;";
	try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql);) {
	    statement.setString(1, name);
	    statement.setInt(2, id);
	    log.info("update() | Before update : " + this.findOne(id).toString());
	    int rowsUpdated = statement.executeUpdate();
	    if (rowsUpdated == 0) {
		log.error("update() | Subject with ID =  " + id + " was NOT updated!");
	    } else {
		log.info("update() | After update " + this.findOne(id).toString());
	    }
	} catch (SQLException e) {
	    log.error("update() | Unable to open connection", e);
	    throw new DAOException("update() | Unable to open connection", e);
	}
    }

    public void delete(int id) throws DAOException {
	log.trace("delete() | Deleting subject with ID = " + id);
	String sql = "delete from subjects as s where s.id = ?;";
	log.trace("delete() | Creating Connection and PreparedStatement...");
	try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql);) {
	    statement.setInt(1, id);
	    int rowsDeleted = statement.executeUpdate();
	    if (rowsDeleted == 0) {
		log.error("delete() | Subject with ID =  " + id + " was NOT deleted!");
	    } else {
		log.info("delete() | Subject with ID =  " + id + " was deleted");
	    }
	} catch (SQLException e) {
	    log.error("delete() | Unable to open connection", e);
	    throw new DAOException("delete() | Unable to open connection", e);
	}
    }

    @Override
    protected List<Subject> retrieveAllEntitiesFromResultSet(ResultSet resultSet) throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Subject retrieveOneEntityFromResultSet(ResultSet resultSet) throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }
}
