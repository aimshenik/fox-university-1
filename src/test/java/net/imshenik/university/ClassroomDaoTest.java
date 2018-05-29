package net.imshenik.university;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.util.List;
import org.junit.Test;
import net.imshenik.university.dao.ClassroomDaoPostgres;
import net.imshenik.university.dao.DaoException;
import net.imshenik.university.domain.Classroom;

public class ClassroomDaoTest {
    ClassroomDaoPostgres classroomDaoPostgres = null;
    
    public ClassroomDaoTest() throws DaoException {
        classroomDaoPostgres = new ClassroomDaoPostgres();
    }
    
    @Test
    public void findAllTest() throws DaoException {
        List<Classroom> classrooms = null;
        classrooms = classroomDaoPostgres.findAll();
        assertNotNull(classrooms);
    }
            
    @Test
    public void findOneTest() throws DaoException {
        Classroom classroom = classroomDaoPostgres.findOne(Integer.MAX_VALUE);
        assertNull(classroom);
        Classroom classroom2 = classroomDaoPostgres.findOne(1);
        assertNotNull(classroom2);
        Classroom classroom3 = classroomDaoPostgres.findOne(Integer.MIN_VALUE);
        assertNull(classroom3);
    }
    
    @Test
    public void createTest() throws DaoException {
        Classroom classroom = classroomDaoPostgres.create(new Classroom(0, "213", "26a", 50));
        assertNotNull(classroom);
    }
    
    @Test
    public void updateTest() throws DaoException {
        Classroom classroom = classroomDaoPostgres.create(new Classroom(0, "213", "26a", 50));
        classroom.setBuilding("67b");
        classroomDaoPostgres.update(classroom);
    }
    
    @Test
    public void deleteTest() throws DaoException {
        Classroom newClassroom = classroomDaoPostgres.create(new Classroom(0, "TO DELETE", "TO DELETE", 5000));
        int lastID = newClassroom.getId();
        classroomDaoPostgres.delete(lastID);
        Classroom removedClassroom = classroomDaoPostgres.findOne(lastID);
        assertNull(removedClassroom);
    }
}
