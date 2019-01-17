package net.imshenik.university.servlets;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.imshenik.university.dao.DaoException;
import net.imshenik.university.dao.GroupDaoPostgres;
import net.imshenik.university.dao.StudentDaoPostgres;
import net.imshenik.university.domain.Group;
import net.imshenik.university.domain.Student;

@WebServlet("/getGroup")
public class GetGroupServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            GroupDaoPostgres groupDaoPostgres = new GroupDaoPostgres();
            Group group = groupDaoPostgres.findOne(Integer.parseInt(request.getParameter("gr_id")));
            StudentDaoPostgres studentDaoPostgres = new StudentDaoPostgres();
            List<Student> students = null;
            if (group != null) {
                students = studentDaoPostgres.findAll().stream().filter(s -> s.getGroupId() == group.getId())
                        .collect(Collectors.toList());
            }
            request.setAttribute("group_obj", group);
            request.setAttribute("students", students);
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
        } catch (NumberFormatException | DaoException e) {
            request.setAttribute("group_obj", null);
            request.setAttribute("students", null);
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
            e.printStackTrace();
        }
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
