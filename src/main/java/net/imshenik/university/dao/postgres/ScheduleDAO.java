package net.imshenik.university.dao.postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;
import net.imshenik.university.domain.entities.Classroom;
import net.imshenik.university.domain.entities.Group;
import net.imshenik.university.domain.entities.Schedule;
import net.imshenik.university.domain.entities.Student;
import net.imshenik.university.domain.entities.Subject;
import net.imshenik.university.domain.entities.Teacher;

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
                    int id = resultSet.getInt("id");
                    Teacher teacher = new TeacherDAO().findOne(resultSet.getInt("teacher_id"));
                    Group group = new GroupDAO().findOne(resultSet.getInt("group_id"));
                    Classroom classroom = new ClassroomDAO().findOne(resultSet.getInt("classroom_id"));
                    Subject subject = new SubjectDAO().findOne(resultSet.getInt("subject_id"));
                    LocalDateTime start = LocalDateTime.parse(resultSet.getString("start_time").replace(' ', 'T'));
                    LocalDateTime end = LocalDateTime.parse(resultSet.getString("end_time").replace(' ', 'T'));
                    students.add(new Schedule(id, teacher, group, classroom, subject, start, end));
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
    
    public Schedule findOne(int id) throws DAOException {
        LOGGER.trace("findOne() | Finding student with ID = " + id);
        String sql = "select * from schedules where id=?;";
        try {
            Class.forName(DRIVER);
            LOGGER.trace("findOne() | Creating Connection and PreparedStatement...");
            try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
                    PreparedStatement statement = connection.prepareStatement(sql);) {
                statement.setInt(1, id);
                LOGGER.trace("findOne() | Creating ResultSet...");
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int foundId = resultSet.getInt("id");
                        Teacher teacher = new TeacherDAO().findOne(resultSet.getInt("teacher_id"));
                        Group group = new GroupDAO().findOne(resultSet.getInt("group_id"));
                        Classroom classroom = new ClassroomDAO().findOne(resultSet.getInt("classroom_id"));
                        Subject subject = new SubjectDAO().findOne(resultSet.getInt("subject_id"));
                        LocalDateTime start = LocalDateTime.parse(resultSet.getString("start_time").replace(' ', 'T'));
                        LocalDateTime end = LocalDateTime.parse(resultSet.getString("end_time").replace(' ', 'T'));
                        Schedule schedule = new Schedule(foundId, teacher, group, classroom, subject, start, end);
                        LOGGER.info("findOne() | Found student with ID = " + id + " : " + schedule.toString());
                        return schedule;
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
    
    public Schedule create(Teacher teacher, Group group, Classroom classroom, Subject subject, LocalDateTime start,
            LocalDateTime end) throws DAOException {
        LOGGER.trace("create() | Creating new schedule...");
        String sql = "insert into schedules (teacher_id, group_id, classroom_id, subject_id, start_time, end_time) values (?,?,?,?,?,?);";
        try {
            Class.forName(DRIVER);
            try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
                    PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                statement.setInt(1, teacher.getId());
                statement.setInt(2, group.getId());
                statement.setInt(3, classroom.getId());
                statement.setInt(4, subject.getId());
                statement.setTimestamp(5, Timestamp.valueOf(start.format(dtf)));
                statement.setTimestamp(6, Timestamp.valueOf(end.format(dtf)));
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted == 0) {
                    LOGGER.warn("create() | New schedule was NOT created!");
                    return null;
                }
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt(1);
                        Schedule schedule = new Schedule(id, teacher, group, classroom, subject, start, end);
                        LOGGER.info("create() | Created new schedule : " + schedule.toString());
                        return schedule;
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
    
    public void update(int id, Teacher teacher, Group group, Classroom classroom, Subject subject, LocalDateTime start,
            LocalDateTime end) throws DAOException {
        LOGGER.trace("update() | Updating schedule with id = " + id);
        String sql = "update schedules set teacher_id=?, group_id=?, classroom_id=?,subject_id=?,start_time=?,end_time=? where id=?;";
        try {
            Class.forName(DRIVER);
            try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
                    PreparedStatement statement = connection.prepareStatement(sql);) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                statement.setInt(1, teacher.getId());
                statement.setInt(2, group.getId());
                statement.setInt(3, classroom.getId());
                statement.setInt(3, classroom.getId());
                statement.setInt(4, subject.getId());
                statement.setTimestamp(5, Timestamp.valueOf(start.format(dtf)));
                statement.setTimestamp(6, Timestamp.valueOf(end.format(dtf)));
                statement.setInt(7, id);
                LOGGER.info("update() | Before update : " + this.findOne(id).toString());
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated == 0) {
                    LOGGER.warn("update() | Schedule with ID =  " + id + " was NOT updated!");
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
        LOGGER.trace("delete() | Deleting schedule with ID = " + id);
        String sql = "delete from schedules as s where s.id = ?;";
        try {
            Class.forName(DRIVER);
            LOGGER.trace("delete() | Creating Connection and PreparedStatement...");
            try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
                    PreparedStatement statement = connection.prepareStatement(sql);) {
                statement.setInt(1, id);
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted == 0) {
                    LOGGER.warn("delete() | Schedule with ID =  " + id + " was NOT deleted!");
                } else {
                    LOGGER.info("delete() | Schedule with ID =  " + id + " was deleted");
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
