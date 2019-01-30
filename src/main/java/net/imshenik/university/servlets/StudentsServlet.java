package net.imshenik.university.servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.imshenik.university.dao.StudentDaoPostgres;
import net.imshenik.university.domain.Student;

@WebServlet("/students")
public class StudentsServlet extends HttpServlet {
    public static final String LIST_STUDENTS = "jsp/all_students.jsp";
    public static final String INSERT_OR_EDIT_STUDENTS = "jsp/one_student.jsp";
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Student> students = new StudentDaoPostgres().findAll();
        request.setAttribute("students", students);
        request.getRequestDispatcher(LIST_STUDENTS).forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        Integer id = request.getParameter("id") != null ? Integer.parseInt(request.getParameter("id")) : null;
        if (action.equals("update") || action.equals("create")) {
            if (id != null) {
                request.setAttribute("student", new StudentDaoPostgres().findOne(id));
            }
            request.getRequestDispatcher(INSERT_OR_EDIT_STUDENTS).forward(request, response);
        } else if (action.equals("delete")) {
            new StudentDaoPostgres().delete(id);
            request.setAttribute("students", new StudentDaoPostgres().findAll());
            request.getRequestDispatcher(LIST_STUDENTS).forward(request, response);
        } else if (action.equals("confirm")) {
            Student student = new Student();
            student.setFirstName(request.getParameter("firstName"));
            student.setLastName(request.getParameter("lastName"));
            student.setGroupId(Integer.parseInt(request.getParameter("groupId")));
            if (id != null) {
                student.setId(id);
                new StudentDaoPostgres().update(student);
            } else {
                new StudentDaoPostgres().create(student);
            }
            request.setAttribute("students", new StudentDaoPostgres().findAll());
            request.getRequestDispatcher(LIST_STUDENTS).forward(request, response);
        }
    }
}
