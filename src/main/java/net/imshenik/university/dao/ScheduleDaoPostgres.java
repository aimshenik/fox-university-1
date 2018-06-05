package net.imshenik.university.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import net.imshenik.university.domain.Schedule;

public class ScheduleDaoPostgres implements ScheduleDao {
    private static final Logger log = Logger.getLogger(ScheduleDaoPostgres.class.getName());
    
    public List<Schedule> findAll() throws DaoException {
        log.trace("findAll() | start");
        String sql = "select * from schedules";
        List<Schedule> schedules = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            log.trace("findAll() | Getting Schedules from ResultSet...");
            while (resultSet.next()) {
                schedules.add(new Schedule(resultSet.getInt("id"),
                        new TeacherDaoPostgres().findOne(resultSet.getInt("teacher_id")),
                        new GroupDaoPostgres().findOne(resultSet.getInt("group_id")),
                        new ClassroomDaoPostgres().findOne(resultSet.getInt("classroom_id")),
                        new SubjectDaoPostgres().findOne(resultSet.getInt("subject_id")),
                        LocalDateTime.parse(resultSet.getString("start_time").replace(' ', 'T')),
                        LocalDateTime.parse(resultSet.getString("end_time").replace(' ', 'T'))));
            }
        } catch (SQLException e) {
            log.error("findAll() | database: interaction failure ", e);
            throw new DaoException("findAll() | database: interaction failure", e);
        }
        log.trace("findAll() | end");
        return schedules;
    }
    
    public Schedule findOne(Integer id) throws DaoException {
        log.trace("findOne() | start");
        String sql = "select * from schedules where id=?";
        Schedule schedule = null;
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
            try (ResultSet resultSet = statement.getResultSet()) {
                if (resultSet.next()) {
                    schedule = new Schedule(resultSet.getInt("id"),
                            new TeacherDaoPostgres().findOne(resultSet.getInt("teacher_id")),
                            new GroupDaoPostgres().findOne(resultSet.getInt("group_id")),
                            new ClassroomDaoPostgres().findOne(resultSet.getInt("classroom_id")),
                            new SubjectDaoPostgres().findOne(resultSet.getInt("subject_id")),
                            LocalDateTime.parse(resultSet.getString("start_time").replace(' ', 'T')),
                            LocalDateTime.parse(resultSet.getString("end_time").replace(' ', 'T')));
                }
            }
        } catch (SQLException e) {
            log.error("findOne() | database: interaction failure", e);
            throw new DaoException("findOne() | database: interaction failure", e);
        }
        log.trace(schedule == null ? "findOne() | Schedule was NOT found!" : "findOne() | Schedule was found");
        log.trace("findOne() | end");
        return schedule;
    }
    
    public Schedule create(Schedule schedule) throws DaoException {
        log.trace("create() | start");
        String sql = "insert into schedules (teacher_id, group_id, classroom_id, subject_id, start_time, end_time) values (?,?,?,?,?,?)";
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, schedule.getTeacher().getId());
            statement.setInt(2, schedule.getGroup().getId());
            statement.setInt(3, schedule.getClassroom().getId());
            statement.setInt(4, schedule.getSubject().getId());
            statement.setTimestamp(5, Timestamp.valueOf(schedule.getStart()));
            statement.setTimestamp(6, Timestamp.valueOf(schedule.getEnd()));
            if (statement.executeUpdate() == 1) {
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        schedule.setId(resultSet.getInt("Id"));
                        log.info("create() | Schedule was created | " + schedule.toString());
                    }
                }
            }
        } catch (SQLException e) {
            log.error("create() | database: interaction failure", e);
            throw new DaoException("create() | database: interaction failure", e);
        }
        log.trace("create() | end");
        return schedule;
    }
    
    public void update(Schedule schedule) throws DaoException {
        log.trace("update() | start");
        if (doesNotExist(schedule.getId())) {
            throw new DaoException("update() | Schedule with ID =  " + schedule.getId() + " does NOT exist!");
        }
        String sql = "update schedules set teacher_id=?, group_id=?, classroom_id=?,subject_id=?,start_time=?,end_time=? where id=?";
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, schedule.getTeacher().getId());
            statement.setInt(2, schedule.getGroup().getId());
            statement.setInt(3, schedule.getClassroom().getId());
            statement.setInt(4, schedule.getSubject().getId());
            statement.setTimestamp(5, Timestamp.valueOf(schedule.getStart()));
            statement.setTimestamp(6, Timestamp.valueOf(schedule.getEnd()));
            statement.setInt(7, schedule.getId());
            statement.executeUpdate();
            log.info("update() | Schedule with ID =  " + schedule.getId() + " was updated");
        } catch (SQLException e) {
            log.error("update() | database: interaction failure", e);
            throw new DaoException("update() | database: interaction failure", e);
        }
        log.trace("update() | end");
    }
    
    public void delete(Integer id) throws DaoException {
        log.trace("delete() | start");
        if (doesNotExist(id)) {
            throw new DaoException("delete() | Schedule with  ID = " + id + " does NOT exist!");
        }
        String sql = "delete from Schedules as t where t.id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            log.info("delete() | Schedule with  ID = " + id + " was deleted");
        } catch (SQLException e) {
            log.error("delete() | database: interaction failure", e);
            throw new DaoException("delete() | database: interaction failure", e);
        }
        log.trace("delete() | end");
    }
    
    private boolean doesNotExist(Integer id) throws DaoException {
        boolean notFound = true;
        String sql = "select exists(select 1 from schedules where id=?)";
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                if (resultSet.getBoolean("exists") == true) {
                    notFound = false;
                }
            }
        } catch (SQLException e) {
            throw new DaoException("exist() | database: interaction failure", e);
        }
        return notFound;
    }
}
