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

public class StudentDAO {
	private static final Logger LOGGER = Logger.getLogger(StudentDAO.class.getName());
	private static final String driver = "org.postgresql.Driver";

	public Set<Student> findAll() throws DAOException {
		LOGGER.trace("Getting list of all students:");
		Set<Student> students = new HashSet<Student>();
		String sql = "select * from students;";
		try {
			Class.forName(driver);
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
				LOGGER.error("Unable to read all students from database", e);
				throw new DAOException("Unable to read all students from database", e);
			}
		} catch (ClassNotFoundException e) {
			LOGGER.fatal("Unable to load driver " + driver, e);
			throw new DAOException("Unable to load driver " + driver, e);
		}
		return students;
	}

	public Student findOne(int id) throws DAOException {
		LOGGER.trace("Finding student with ID = " + id);
		String sql = "select * from students where id=?;";
		try {
			Class.forName(driver);
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
			LOGGER.fatal("Unable to load driver " + driver, e);
			throw new DAOException("Unable to load driver " + driver, e);
		}
	}

	public Student create(String firstName, String lastName) throws DAOException {
		LOGGER.trace("Creating new student with First Name = " + firstName + " and Last Name = " + lastName);
		String sql = "insert into students (firstname,lastname) values (?,?);";
		try {
			Class.forName(driver);
			try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/university",
					"andrey", "1234321");
					PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
				statement.setString(1, firstName);
				statement.setString(2, lastName);
				int rowsInserted = statement.executeUpdate();
				LOGGER.trace("Inserted " + rowsInserted + " row(s)");
				if (rowsInserted == 0) {
					throw new DAOException("Inserted  " + rowsInserted + " row(s)");
				}
				try (ResultSet generatedKey = statement.getGeneratedKeys()) {
					generatedKey.next();
					int id = generatedKey.getInt(1);
					Student student = new Student(id, firstName, lastName);
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
