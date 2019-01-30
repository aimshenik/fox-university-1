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
    public static final String LIST_GROUP = "jsp/all_groups.jsp";
    public static final String INSERT_OR_EDIT_GROUP = "jsp/one_group.jsp";
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = null;
        if (request.getParameter("id") != null) {
            try {
                id = Integer.parseInt(request.getParameter("id"));
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Incorrect Group ID format");
            }
        }
        List<Group> groups = new GroupDaoPostgres().findAll();
        request.setAttribute("groups", groups);
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
        request.getRequestDispatcher(LIST_GROUP).forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        Integer id = request.getParameter("id") != null ? Integer.parseInt(request.getParameter("id")) : null;
        if (action.equals("update") || action.equals("create")) {
            if (id != null) {
                request.setAttribute("group", new GroupDaoPostgres().findOne(id));
            }
            request.getRequestDispatcher(INSERT_OR_EDIT_GROUP).forward(request, response);
        } else if (action.equals("delete")) {
            new GroupDaoPostgres().delete(id);
            request.setAttribute("groups", new GroupDaoPostgres().findAll());
            request.getRequestDispatcher(LIST_GROUP).forward(request, response);
        } else if (action.equals("confirm")) {
            Group group = new Group();
            group.setName(request.getParameter("name"));
            if (id != null) {
                group.setId(id);
                new GroupDaoPostgres().update(group);
            } else {
                new GroupDaoPostgres().create(group);
            }
            request.setAttribute("groups", new GroupDaoPostgres().findAll());
            request.getRequestDispatcher(LIST_GROUP).forward(request, response);
        }
    }
}
