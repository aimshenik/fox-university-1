package net.imshenik.university.servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.imshenik.university.dao.GroupDaoPostgres;
import net.imshenik.university.dao.StudentDaoPostgres;
import net.imshenik.university.domain.Group;
import net.imshenik.university.domain.Student;

@WebServlet("/student")
public class StudentServlet extends HttpServlet {
    public static final String EDIT_JSP = "jsp/one_student.jsp";
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Student student = new StudentDaoPostgres().findOne(Integer.parseInt(request.getParameter("id")));
        GroupDaoPostgres groupDaoPostgres = new GroupDaoPostgres();
        List<Group> groups = groupDaoPostgres.findAll();
        Group currentGroup = groupDaoPostgres.findOne(student.getGroupId());
        groups.remove(currentGroup);
        request.setAttribute("student", new StudentDaoPostgres().findOne(Integer.parseInt(request.getParameter("id"))));
        request.setAttribute("groups", groups);
        request.setAttribute("currentGroup", currentGroup);
        request.getRequestDispatcher(EDIT_JSP).forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Student student = new Student(Integer.parseInt(request.getParameter("id")), request.getParameter("firstName"),
                request.getParameter("lastName"), Integer.parseInt(request.getParameter("groupId")));
        new StudentDaoPostgres().update(student);
        response.sendRedirect(request.getContextPath() + "/students");
    }
}
