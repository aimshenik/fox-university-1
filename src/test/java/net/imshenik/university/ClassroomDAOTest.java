package net.imshenik.university;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.util.List;
import org.junit.Test;
import net.imshenik.university.dao.postgres.ClassroomDAO;
import net.imshenik.university.dao.postgres.DAOException;
import net.imshenik.university.domain.entities.Classroom;

public class ClassroomDAOTest {
    ClassroomDAO classroomDAO = null;
    
    public ClassroomDAOTest() throws DAOException {
        classroomDAO = new ClassroomDAO();
    }
    
    @Test
    public void findAllTest() throws DAOException {
        List<Classroom> classrooms = null;
        classrooms = classroomDAO.findAll();
        assertNotNull(classrooms);
    }
    
    @Test
    public void findOneTest() throws DAOException {
        Classroom classroom = classroomDAO.findOne(Integer.MAX_VALUE);
        assertNull(classroom);
        Classroom classroom2 = classroomDAO.findOne(1);
        assertNotNull(classroom2);
        Classroom classroom3 = classroomDAO.findOne(Integer.MIN_VALUE);
        assertNull(classroom3);
    }
    
    @Test
    public void createTest() throws DAOException {
        Classroom classroom = classroomDAO.create("213", "26a", 50);
        assertNotNull(classroom);
    }
    
    @Test
    public void updateTest() throws DAOException {
        Classroom classroom = classroomDAO.create("213", "26a", 50);
        classroomDAO.update(classroom.getId(), "NEW" + classroom.getNumber(), classroom.getBuilding(),
                classroom.getCapacity());
    }
    
    @Test
    public void deleteTest() throws DAOException {
        Classroom newClassroom = classroomDAO.create("TO DELETE", "TO DELETE", 5000);
        int lastID = newClassroom.getId();
        classroomDAO.delete(lastID);
        Classroom removedClassroom = classroomDAO.findOne(lastID);
        assertNull(removedClassroom);
    }
}
