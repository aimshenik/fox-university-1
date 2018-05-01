package net.imshenik.university;

import static org.junit.Assert.assertNotNull;
import java.util.Set;
import org.junit.Test;
import net.imshenik.university.dao.postgres.DAOException;
import net.imshenik.university.dao.postgres.DAOStudent;
import net.imshenik.university.domain.entities.Student;


public class TestDAOStudent {
    
    @Test
    public void getAllStudentsTest() throws DAOException {
        DAOStudent daoStudent = new DAOStudent();
        Set<Student> students = null;
        students = daoStudent.getAllStudents();
        assertNotNull(students);
    }
}
