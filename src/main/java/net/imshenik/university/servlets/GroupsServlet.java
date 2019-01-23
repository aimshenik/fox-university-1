package net.imshenik.university.servlets;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.imshenik.university.dao.GroupDaoPostgres;
import net.imshenik.university.dao.StudentDaoPostgres;
import net.imshenik.university.domain.Group;

@WebServlet("/groups")
public class GroupsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Group> groups = new GroupDaoPostgres().findAll();
        request.setAttribute("groups", groups);
        if (request.getParameter("id") != null) {
            Integer id = null;
            try {
                id = Integer.parseInt(request.getParameter("id"));
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Incorrect Group ID format");
            }
            if (id != null) {
                int primitiveId = id;
                Group group = null;
                try {
                    group = groups.parallelStream().filter(g -> g.getId() == primitiveId).findFirst().get();
                    request.setAttribute("group", group);
                } catch (NoSuchElementException e) {
                    request.setAttribute("errorMessage", String.format("Group with ID=%d was not found", primitiveId));
                }
                if (group != null) {
                    request.setAttribute("students", new StudentDaoPostgres().findByGroup(id));
                }
            }
        }
        request.getRequestDispatcher("jsp/groups.jsp").forward(request, response);
    }
}
