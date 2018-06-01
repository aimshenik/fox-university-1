package net.imshenik.university.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import net.imshenik.university.domain.Classroom;
import net.imshenik.university.domain.Group;
import net.imshenik.university.domain.Schedule;
import net.imshenik.university.domain.Subject;
import net.imshenik.university.domain.Teacher;

public class ScheduleDao extends AbstractDao<Schedule> {
    private static final Logger log = Logger.getLogger(ScheduleDao.class.getName());
    
    public List<Schedule> findAll() throws DaoException {
        log.trace("findAll() | call AbstractDAO.findAll() | start");
        String sql = "select * from schedules;";
        List<Schedule> schedules = super.findAll(sql);
        log.trace("findAll() | call AbstractDAO.findAll() | end");
        return schedules;
    }
    
    public Schedule findOne(int id) throws DaoException {
        log.trace("findOne() | call AbstractDAO.findOne() | start");
        String sql = "select * from schedules where id=?;";
        Schedule schedule = super.findOne(id, sql);
        if (schedule == null) {
            log.info("findOne() | Schedule with ID = " + id + " was NOT found!");
        } else {
            log.info("findOne() | Schedule with ID = " + id + " was found | " + schedule.toString());
        }
        log.trace("findOne() | call AbstractDAO.findOne() | end");
        return schedule;
    }
    
    public Schedule create(Teacher teacher, Group group, Classroom classroom, Subject subject, LocalDateTime start,
            LocalDateTime end) throws DaoException {
        log.trace("create() | start");
        String sql = "insert into schedules (teacher_id, group_id, classroom_id, subject_id, start_time, end_time) values (?,?,?,?,?,?);";
        Schedule schedule = null;
        try (Connection connection = ConnectionFactory.getConnection();
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
                schedule = collectOneElementFromResultSet(resultSet);
            } catch (SQLException e) {
                log.error("create() | Unable to create ResultSet", e);
                throw new DaoException("create() | Unable to create ResultSet", e);
            }
        } catch (SQLException e) {
            log.error("create() | Unable to create SQL resourses", e);
            throw new DaoException("create() | Unable to create SQL resourses", e);
        }
        return schedule;
    }
    
    public void update(int id, Teacher teacher, Group group, Classroom classroom, Subject subject, LocalDateTime start,
            LocalDateTime end) throws DaoException {
        log.trace("update() | Updating schedule with id = " + id);
        String sql = "update schedules set teacher_id=?, group_id=?, classroom_id=?,subject_id=?,start_time=?,end_time=? where id=?;";
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            statement.setInt(1, teacher.getId());
            statement.setInt(2, group.getId());
            statement.setInt(3, classroom.getId());
            statement.setInt(3, classroom.getId());
            statement.setInt(4, subject.getId());
            statement.setTimestamp(5, Timestamp.valueOf(start.format(dtf)));
            statement.setTimestamp(6, Timestamp.valueOf(end.format(dtf)));
            statement.setInt(7, id);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                log.error("update() | Schedule with ID =  " + id + " was NOT updated!");
            } else {
                log.info("update() | Schedule with ID =  " + id + " was updated!");
            }
        } catch (SQLException e) {
            log.error("update() | Unable to create SQL resourses", e);
            throw new DaoException("update() | Unable to create SQL resourses", e);
        }
    }
    
    public void delete(int id) throws DaoException {
        log.trace("delete() | call AbstractDAO.delete() | start");
        String sql = "delete from schedules as s where s.id = ?;";
        int rowsDeleted = super.delete(id, sql);
        if (rowsDeleted == 0) {
            log.error("delete() | Teacher with ID = " + id + " was NOT deleted!");
        } else {
            log.info("delete() | Teacher with ID = " + id + " was deleted");
        }
        log.trace("delete() | call AbstractDAO.delete() | end");
    }
    
    @Override
    protected List<Schedule> collectManyElementsFromResultSet(ResultSet resultSet) throws DaoException {
        List<Schedule> schedules = new ArrayList<>();
        int id = 0;
        Teacher teacher = null;
        Group group = null;
        Classroom classroom = null;
        Subject subject = null;
        LocalDateTime start = null;
        LocalDateTime end = null;
        try {
            while (resultSet.next()) {
                id = resultSet.getInt("id");
                teacher = new TeacherDao().findOne(resultSet.getInt("teacher_id"));
                group = new GroupDaoPostgres().findOne(resultSet.getInt("group_id"));
                classroom = new ClassroomDaoPostgres().findOne(resultSet.getInt("classroom_id"));
                subject = new SubjectDao().findOne(resultSet.getInt("subject_id"));
                start = LocalDateTime.parse(resultSet.getString("start_time").replace(' ', 'T'));
                end = LocalDateTime.parse(resultSet.getString("end_time").replace(' ', 'T'));
                schedules.add(new Schedule(id, teacher, group, classroom, subject, start, end));
            }
        } catch (SQLException e) {
            log.error("collectManyElementsFromResultSet() | error while handling ResultSet with Schedules", e);
            throw new DaoException("collectManyElementsFromResultSet() | error while handling ResultSet with Schedules",
                    e);
        }
        return schedules;
    }
    
    @Override
    protected Schedule collectOneElementFromResultSet(ResultSet resultSet) throws DaoException {
        Schedule schedule = null;
        List<Schedule> schedules = collectManyElementsFromResultSet(resultSet);
        if (schedules.size() > 0) {
            schedule = schedules.get(0);
        }
        return schedule;
    }
}
