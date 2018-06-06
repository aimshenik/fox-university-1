package net.imshenik.university;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.List;
import org.apache.log4j.Logger;
import org.junit.Test;
import net.imshenik.university.dao.ClassroomDaoPostgres;
import net.imshenik.university.dao.DaoException;
import net.imshenik.university.domain.Classroom;

public class ClassroomDaoTest {
    ClassroomDaoPostgres classroomDaoPostgres = null;
    
    public ClassroomDaoTest() throws DaoException {
        classroomDaoPostgres = new ClassroomDaoPostgres();
    }
    
    // @Test
    // public void findAllTest() throws DaoException {
    // List<Classroom> classrooms = null;
    // classrooms = classroomDaoPostgres.findAll();
    // assertTrue(classrooms.size() > 0);
    // }
    //
    // @Test
    // public void findOneTest() throws DaoException {
    // Classroom classroom = classroomDaoPostgres.findOne(Integer.MAX_VALUE);
    // assertNull(classroom);
    // Classroom classroom2 = classroomDaoPostgres.findOne(1);
    // assertNotNull(classroom2);
    // }
    //
    // @Test
    // public void createTest() throws DaoException {
    // Classroom classroom = classroomDaoPostgres.create(new Classroom(0, "213",
    // "26a", 50));
    // assertTrue(classroom.getId() != 0);
    // }
    //
    // @Test
    // public void updateTest() throws DaoException {
    // Classroom classroom = classroomDaoPostgres.create(new Classroom(0, null,
    // null, 0));
    // classroom.setBuilding("67b");
    // classroomDaoPostgres.update(classroom);
    // }
    //
    // @Test
    // public void deleteTest() throws DaoException {
    // Classroom newClassroom = classroomDaoPostgres.create(new Classroom(0, "TO
    // DELETE", "TO DELETE", 5000));
    // int lastID = newClassroom.getId();
    // classroomDaoPostgres.delete(lastID);
    // Classroom removedClassroom = classroomDaoPostgres.findOne(lastID);
    // assertNull(removedClassroom);
    // }
    @Test
    public void classroomCrudTest() throws DaoException {
        Classroom firstClassroom = new Classroom("22", "d", 50);
        Classroom secondClassroom = new Classroom("23", "d", 10);
        classroomDaoPostgres.create(firstClassroom);
        classroomDaoPostgres.create(secondClassroom);
        assertTrue(firstClassroom.getId() != 0);
        assertTrue(secondClassroom.getId() != 0);
        List<Classroom> classrooms = classroomDaoPostgres.findAll();
        assertTrue(classrooms.size() >= 2);
        Classroom foundClassroom = classroomDaoPostgres.findOne(firstClassroom.getId());
        assertEquals(foundClassroom, firstClassroom);
        secondClassroom.setBuilding("A");
        classroomDaoPostgres.update(secondClassroom);
        Classroom updatedClassroom = classroomDaoPostgres.findOne(secondClassroom.getId());
        assertEquals(updatedClassroom, secondClassroom);
        classroomDaoPostgres.delete(firstClassroom.getId());
        classroomDaoPostgres.delete(secondClassroom.getId());
        assertNull(classroomDaoPostgres.findOne(firstClassroom.getId()));
        assertNull(classroomDaoPostgres.findOne(secondClassroom.getId()));
    }
}
