package net.imshenik.university.dao.postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import org.apache.log4j.Logger;
import net.imshenik.university.domain.entities.Schedule;
import net.imshenik.university.domain.entities.Student;

public class ScheduleDAO {
    private static final Logger LOGGER   = Logger.getLogger(ScheduleDAO.class.getName());
    private static final String DRIVER   = "org.postgresql.Driver";
    private static final String URL      = "jdbc:postgresql://localhost:5432/university";
    private static final String LOGIN    = "andrey";
    private static final String PASSWORD = "1234321";
    
    public Set<Schedule> findAll() throws DAOException {
        LOGGER.trace("findAll() | Getting list of all students:");
        Set<Schedule> students = null;
        String sql = "select * from schedules;";
        try {
            Class.forName(DRIVER);
            LOGGER.trace("findAll() | Creating Connection, PreparedStatement and ResultSet...");
            try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
                    PreparedStatement statement = connection.prepareStatement(sql);
                    ResultSet resultSet = statement.executeQuery();) {
                LOGGER.trace("findAll() | Iterating by ResultSet...");
                students = new HashSet<Schedule>();
                while (resultSet.next()) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd hh:mm:ss a");
//                    DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;                    
                    Schedule schedule = new Schedule();
                    schedule.setId(resultSet.getInt("id"));
                    schedule.setTeacher(new TeacherDAO().findOne(resultSet.getInt("teacher_id")));
                    schedule.setGroup(new GroupDAO().findOne(resultSet.getInt("group_id")));
                    schedule.setClassroom(new ClassroomDAO().findOne(resultSet.getInt("classroom_id")));
                    schedule.setSubject(new SubjectDAO().findOne(resultSet.getInt("subject_id")));
                    schedule.setStart(LocalDateTime.parse(resultSet.getString("start_time"),formatter));
                    schedule.setEnd(LocalDateTime.parse(resultSet.getString("end_time"),formatter));
                    students.add(schedule);
                }
                LOGGER.info("findAll() | All " + students.size() + " students found");
            } catch (Exception e) {
                LOGGER.error("findAll() | Unable to read all students from database", e);
                throw new DAOException("findAll() | Unable to read all students from database", e);
            }
        } catch (ClassNotFoundException e) {
            LOGGER.fatal("findAll() | Unable to load driver " + DRIVER, e);
            throw new DAOException("findAll() | Unable to load driver " + DRIVER, e);
        }
        return students;
    }
    
    public Student findOne(int id) throws DAOException {
        LOGGER.trace("findOne() | Finding student with ID = " + id);
        String sql = "select * from students where id=?;";
        try {
            Class.forName(DRIVER);
            LOGGER.trace("findOne() | Creating Connection and PreparedStatement...");
            try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
                    PreparedStatement statement = connection.prepareStatement(sql);) {
                statement.setInt(1, id);
                LOGGER.trace("findOne() | Creating ResultSet...");
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Student student = new Student();
                        student.setId(resultSet.getInt("id"));
                        student.setFirstName(resultSet.getString("firstname"));
                        student.setLastName(resultSet.getString("lastname"));
                        LOGGER.info("findOne() | Found student with ID = " + id + " : " + student.toString());
                        return student;
                    }
                    LOGGER.warn("findOne() | Unable to find student with ID = " + id);
                    return null;
                }
            } catch (Exception e) {
                LOGGER.error("findOne() | Unable to create Connection", e);
                throw new DAOException("findOne() | Unable to create Connection", e);
            }
        } catch (ClassNotFoundException e) {
            LOGGER.fatal("findOne() | Unable to load driver " + DRIVER, e);
            throw new DAOException("findOne() | Unable to load driver " + DRIVER, e);
        }
    }
    
    public Student create(String firstName, String lastName) throws DAOException {
        LOGGER.trace("create() | Creating new student with First Name = " + firstName + " and Last Name = " + lastName);
        String sql = "insert into students (firstname,lastname) values (?,?);";
        try {
            Class.forName(DRIVER);
            try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
                    PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
                statement.setString(1, firstName);
                statement.setString(2, lastName);
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted == 0) {
                    LOGGER.warn("create() | New student with first name = " + firstName + " and last name = " + lastName
                            + " was NOT created!");
                    return null;
                }
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt(1);
                        Student student = new Student(id, firstName, lastName);
                        LOGGER.info("create() | Created new student : " + student.toString());
                        return student;
                    }
                }
            } catch (Exception e) {
                LOGGER.error("create() | Unable to open connection", e);
                throw new DAOException("create() | Unable to open connection", e);
            }
        } catch (ClassNotFoundException e) {
            LOGGER.fatal("create() | Unable to load driver " + DRIVER, e);
            throw new DAOException("create() | Unable to load driver " + DRIVER, e);
        }
        return null;
    }
    
    public void update(int id, String firstName, String lastName, int group_id) throws DAOException {
        LOGGER.trace("update() | Updating Student with id = " + id);
        String sql = "update students set firstname=?,lastname=?, group_id=? where id=?;";
        try {
            Class.forName(DRIVER);
            try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
                    PreparedStatement statement = connection.prepareStatement(sql);) {
                statement.setString(1, firstName);
                statement.setString(2, lastName);
                statement.setInt(3, group_id);
                statement.setInt(4, id);
                LOGGER.info("update() | Before update : " + this.findOne(id).toString());
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated == 0) {
                    LOGGER.warn("update() | Student with ID =  " + id + " was NOT updated!");
                } else {
                    LOGGER.info("update() | After update " + this.findOne(id).toString());
                }
            } catch (Exception e) {
                LOGGER.error("update() | Unable to open connection", e);
                throw new DAOException("update() | Unable to open connection", e);
            }
        } catch (ClassNotFoundException e) {
            LOGGER.fatal("update() | Unable to load driver " + DRIVER, e);
            throw new DAOException("update() | Unable to load driver " + DRIVER, e);
        }
    }
    
    public void delete(int id) throws DAOException {
        LOGGER.trace("delete() | Deleting student with ID = " + id);
        String sql = "delete from students as s where s.id = ?;";
        try {
            Class.forName(DRIVER);
            LOGGER.trace("delete() | Creating Connection and PreparedStatement...");
            try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
                    PreparedStatement statement = connection.prepareStatement(sql);) {
                statement.setInt(1, id);
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted == 0) {
                    LOGGER.warn("delete() | Student with ID =  " + id + " was NOT deleted!");
                } else {
                    LOGGER.info("delete() | Student with ID =  " + id + " was deleted");
                }
            } catch (Exception e) {
                LOGGER.error("delete() | Unable to open connection", e);
                throw new DAOException("delete() | Unable to open connection", e);
            }
        } catch (ClassNotFoundException e) {
            LOGGER.fatal("delete() | Unable to load driver " + DRIVER, e);
            throw new DAOException("delete() | Unable to load driver " + DRIVER, e);
        }
    }
}
