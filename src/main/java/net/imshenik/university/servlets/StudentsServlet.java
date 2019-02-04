package net.imshenik.university.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.imshenik.university.dao.GroupDaoPostgres;
import net.imshenik.university.dao.StudentDaoPostgres;
import net.imshenik.university.domain.Group;
import net.imshenik.university.domain.Student;

@WebServlet("/students")
public class StudentsServlet extends HttpServlet {
    public static final String LIST_STUDENTS = "jsp/all_students.jsp";
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Student> students = new StudentDaoPostgres().findAll();
        List<Group> groups = new GroupDaoPostgres().findAll();
        Map<Integer, String> groupsMap = groups.stream().collect(Collectors.toMap(Group::getId, Group::getName));
        request.setAttribute("students", students);
        request.setAttribute("groups", groups);
        request.setAttribute("groupsMap", groupsMap);
        request.getRequestDispatcher(LIST_STUDENTS).forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Student student = new Student(request.getParameter("firstName"), request.getParameter("lastName"),
                Integer.parseInt(request.getParameter("groupId")));
        new StudentDaoPostgres().create(student);
        response.sendRedirect(request.getContextPath() + "/students");
    }
}
