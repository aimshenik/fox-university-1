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
import net.imshenik.university.dao.StudentDaoPostgres;
import net.imshenik.university.domain.Group;
import net.imshenik.university.domain.Student;

@WebServlet("/groups")
public class GroupsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        GroupDao groupDaoPostgres = new GroupDaoPostgres();
        List<Group> groups = groupDaoPostgres.findAll();
        request.setAttribute("groups", groups);
        Integer id = null;
        try {
            id = Integer.parseInt("" + request.getParameter("id"));
        } catch (NumberFormatException e) {
        }
        if (id != null) {
            List<Student> students = new StudentDaoPostgres().findByGroup(id);
            request.setAttribute("students", students);
            request.setAttribute("group", groupDaoPostgres.findOne(id));
        }
        request.getRequestDispatcher("jsp/groups.jsp").forward(request, response);
    }
}
