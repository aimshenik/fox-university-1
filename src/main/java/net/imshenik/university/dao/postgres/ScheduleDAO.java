package net.imshenik.university.dao.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import net.imshenik.university.domain.entities.Subject;
import net.imshenik.university.domain.entities.Teacher;

public class ScheduleDAO extends AbstractDAO {
    private static final Logger log = Logger.getLogger(ScheduleDAO.class.getName());
    
    public ScheduleDAO() throws DAOException {super();
    }
    
    public Set<Schedule> findAll() throws DAOException {
        log.trace("findAll() | Getting list of all students:");
        Set<Schedule> schedules = null;
        String sql = "select * from schedules;";
        log.trace("findAll() | Creating Connection, PreparedStatement and ResultSet...");
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();) {
            log.trace("findAll() | Iterating by ResultSet...");
            schedules = new HashSet<Schedule>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                Teacher teacher = new TeacherDAO().findOne(resultSet.getInt("teacher_id"));
                Group group = new GroupDAO().findOne(resultSet.getInt("group_id"));
                Classroom classroom = new ClassroomDAO().findOne(resultSet.getInt("classroom_id"));
                Subject subject = new SubjectDAO().findOne(resultSet.getInt("subject_id"));
                LocalDateTime start = LocalDateTime.parse(resultSet.getString("start_time").replace(' ', 'T'));
                LocalDateTime end = LocalDateTime.parse(resultSet.getString("end_time").replace(' ', 'T'));
                schedules.add(new Schedule(id, teacher, group, classroom, subject, start, end));
            }
            log.info("findAll() | All " + schedules.size() + " students found");
        } catch (SQLException e) {
            log.error("findAll() | Unable to read all students from database", e);
            throw new DAOException("findAll() | Unable to read all students from database", e);
        }
        return schedules;
    }
    
    public Schedule findOne(int id) throws DAOException {
        log.trace("findOne() | Finding student with ID = " + id);
        String sql = "select * from schedules where id=?;";
        Schedule schedule = null;
        log.trace("findOne() | Creating Connection and PreparedStatement...");
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, id);
            log.trace("findOne() | Creating ResultSet...");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int foundId = resultSet.getInt("id");
                    Teacher teacher = new TeacherDAO().findOne(resultSet.getInt("teacher_id"));
                    Group group = new GroupDAO().findOne(resultSet.getInt("group_id"));
                    Classroom classroom = new ClassroomDAO().findOne(resultSet.getInt("classroom_id"));
                    Subject subject = new SubjectDAO().findOne(resultSet.getInt("subject_id"));
                    LocalDateTime start = LocalDateTime.parse(resultSet.getString("start_time").replace(' ', 'T'));
                    LocalDateTime end = LocalDateTime.parse(resultSet.getString("end_time").replace(' ', 'T'));
                    schedule = new Schedule(foundId, teacher, group, classroom, subject, start, end);
                    log.info("findOne() | Found student with ID = " + id + " : " + schedule.toString());
                }
                log.info("findOne() | Unable to find student with ID = " + id);
            }
        } catch (SQLException e) {
            log.error("findOne() | Unable to create Connection", e);
            throw new DAOException("findOne() | Unable to create Connection", e);
        }
        return schedule;
    }
    
    public Schedule create(Teacher teacher, Group group, Classroom classroom, Subject subject, LocalDateTime start,
            LocalDateTime end) throws DAOException {
        log.trace("create() | Creating new schedule...");
        String sql = "insert into schedules (teacher_id, group_id, classroom_id, subject_id, start_time, end_time) values (?,?,?,?,?,?);";
        Schedule schedule = null;
        try (Connection connection = getConnection();
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
                log.error("create() | New schedule was NOT created!");
            }
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    schedule = new Schedule(id, teacher, group, classroom, subject, start, end);
                    log.info("create() | Created new schedule : " + schedule.toString());
                }
            }
        } catch (SQLException e) {
            log.error("create() | Unable to open connection", e);
            throw new DAOException("create() | Unable to open connection", e);
        }
        return schedule;
    }
    
    public void update(int id, Teacher teacher, Group group, Classroom classroom, Subject subject, LocalDateTime start,
            LocalDateTime end) throws DAOException {
        log.trace("update() | Updating schedule with id = " + id);
        String sql = "update schedules set teacher_id=?, group_id=?, classroom_id=?,subject_id=?,start_time=?,end_time=? where id=?;";
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql);) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            statement.setInt(1, teacher.getId());
            statement.setInt(2, group.getId());
            statement.setInt(3, classroom.getId());
            statement.setInt(3, classroom.getId());
            statement.setInt(4, subject.getId());
            statement.setTimestamp(5, Timestamp.valueOf(start.format(dtf)));
            statement.setTimestamp(6, Timestamp.valueOf(end.format(dtf)));
            statement.setInt(7, id);
            log.info("update() | Before update : " + this.findOne(id).toString());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                log.error("update() | Schedule with ID =  " + id + " was NOT updated!");
            } else {
                log.info("update() | After update " + this.findOne(id).toString());
            }
        } catch (SQLException e) {
            log.error("update() | Unable to open connection", e);
            throw new DAOException("update() | Unable to open connection", e);
        }
    }
    
    public void delete(int id) throws DAOException {
        log.trace("delete() | Deleting schedule with ID = " + id);
        String sql = "delete from schedules as s where s.id = ?;";
        log.trace("delete() | Creating Connection and PreparedStatement...");
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted == 0) {
                log.error("delete() | Schedule with ID =  " + id + " was NOT deleted!");
            } else {
                log.info("delete() | Schedule with ID =  " + id + " was deleted");
            }
        } catch (SQLException e) {
            log.error("delete() | Unable to open connection", e);
            throw new DAOException("delete() | Unable to open connection", e);
        }
    }
}
