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

@WebServlet("/groups")
public class GroupsServlet extends HttpServlet {
    public static final String LIST_GROUP = "jsp/all_groups.jsp";
    public static final String ERROR_404 = "jsp/error.jsp";
    private StudentDao studentDao;
    private GroupDao groupDao;
    
    @Override
    public void init() throws ServletException {
        studentDao = new StudentDaoPostgres();
        groupDao = new GroupDaoPostgres();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = null;
        if (request.getParameter("id") != null) {
            try {
                id = Integer.parseInt(request.getParameter("id"));
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Incorrect Group ID format");
            }
        }
        List<Group> groups = groupDao.findAll();
        request.setAttribute("groups", groups);
        if (id != null) {
            int primitiveId = id;
            Group group = null;
            if (groups.parallelStream().filter(g -> g.getId() == primitiveId).findFirst().isPresent()) {
                group = groups.parallelStream().filter(g -> g.getId() == primitiveId).findFirst().get();
                request.setAttribute("group", group);
            } else {
                request.getRequestDispatcher(ERROR_404).forward(request, response);
            }
            if (group != null) {
                request.setAttribute("students", ((StudentDaoPostgres) studentDao).findByGroup(id));
            }
        }
        request.getRequestDispatcher(LIST_GROUP).forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        groupDao.create(new Group(request.getParameter("name")));
        response.sendRedirect(request.getContextPath() + "/groups");
    }
}
