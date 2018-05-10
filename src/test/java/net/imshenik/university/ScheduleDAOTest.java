package net.imshenik.university;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.util.Iterator;
import java.util.Set;
import org.junit.Test;
import net.imshenik.university.dao.postgres.DAOException;
import net.imshenik.university.dao.postgres.ScheduleDAO;
import net.imshenik.university.dao.postgres.StudentDAO;
import net.imshenik.university.domain.entities.Schedule;
import net.imshenik.university.domain.entities.Student;

public class ScheduleDAOTest {
    ScheduleDAO studentDAO = new ScheduleDAO();
    
    @Test
    public void findAllTest() throws DAOException {
        Set<Schedule> schedules = null;
        schedules = studentDAO.findAll();
        assertNotNull(schedules);
        for (Iterator iterator = schedules.iterator(); iterator.hasNext();) {
            Schedule schedule = (Schedule) iterator.next();
            System.out.println(schedule.toString());
            
        }
    }
//    
//    @Test
//    public void findOneTest() throws DAOException {
//        Student student1 = studentDAO.findOne(Integer.MAX_VALUE);
//        assertNull(student1);
//        Student student2 = studentDAO.findOne(22);
//        assertNotNull(student2);
//        Student student3 = studentDAO.findOne(Integer.MIN_VALUE);
//        assertNull(student3);
//    }
//    
//    @Test
//    public void createTest() throws DAOException {
//        Student student = studentDAO.create("SERGEY", "IVANOV");
//        assertNotNull(student);
//    }
//    
//    @Test
//    public void updateTest() throws DAOException {
//        Student student = studentDAO.create("FORUPDATE", "FORUPDATE");
//        studentDAO.update(student.getId(), "NEW" + student.getFirstName(), "NEW" + student.getLastName(), 101);
//    }
//    
//    @Test
//    public void deleteTest() throws DAOException {
//        Student newStudent = studentDAO.create("TODELETE:", "TODELETE");
//        int lastID = newStudent.getId();
//        studentDAO.delete(lastID);
//        Student removedStudent = studentDAO.findOne(lastID);
//        assertNull(removedStudent);
//    }
}
