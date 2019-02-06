package net.imshenik.university.servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.imshenik.university.dao.GroupDao;
import net.imshenik.university.dao.GroupDaoPostgres;
import net.imshenik.university.dao.StudentDao;
import net.imshenik.university.dao.StudentDaoPostgres;
import net.imshenik.university.domain.Group;
import net.imshenik.university.domain.Student;

@WebServlet("/student")
public class StudentServlet extends HttpServlet {
    private static final String EDIT_JSP = "jsp/one_student.jsp";
    private StudentDao studentDao;
    private GroupDao groupDao;
    
    @Override
    public void init() throws ServletException {
        studentDao = new StudentDaoPostgres();
        groupDao = new GroupDaoPostgres();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Student student = studentDao.findOne(Integer.parseInt(request.getParameter("id")));
        List<Group> groups = groupDao.findAll();
        Group currentGroup = groupDao.findOne(student.getGroupId());
        groups.remove(currentGroup);
        request.setAttribute("student", studentDao.findOne(Integer.parseInt(request.getParameter("id"))));
        request.setAttribute("groups", groups);
        request.setAttribute("currentGroup", currentGroup);
        request.getRequestDispatcher(EDIT_JSP).forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Student student = new Student(Integer.parseInt(request.getParameter("id")), request.getParameter("firstName"),
                request.getParameter("lastName"), Integer.parseInt(request.getParameter("groupId")));
        studentDao.update(student);
        response.sendRedirect(request.getContextPath() + "/students");
    }
}
