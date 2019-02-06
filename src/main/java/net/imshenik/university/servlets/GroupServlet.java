package net.imshenik.university.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.imshenik.university.dao.GroupDao;
import net.imshenik.university.dao.GroupDaoPostgres;
import net.imshenik.university.domain.Group;

@WebServlet("/group")
public class GroupServlet extends HttpServlet {
    public static final String EDIT_JSP = "jsp/one_group.jsp";
    private GroupDao groupDao;
    
    @Override
    public void init() throws ServletException {
        groupDao = new GroupDaoPostgres();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        request.setAttribute("group", groupDao.findOne(id));
        request.getRequestDispatcher(EDIT_JSP).forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        groupDao.update(new Group(Integer.parseInt(request.getParameter("id")), request.getParameter("name")));
        response.sendRedirect(request.getContextPath() + "/groups");
    }
}
