package net.imshenik.university.servlets;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.imshenik.university.dao.GroupDaoPostgres;
import net.imshenik.university.dao.StudentDaoPostgres;
import net.imshenik.university.domain.Group;
import net.imshenik.university.domain.Student;

@WebServlet("/groups")
public class GroupsServlet extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        GroupDaoPostgres groupDaoPostgres = new GroupDaoPostgres();
        List<Group> groups = groupDaoPostgres.findAll();
        request.setAttribute("groups", groups);
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String param = params.nextElement();
            System.out.println("ATTR = " + param);
            if (param.equals("id")) {
                Integer id = Integer.parseInt("" + request.getParameter(param));
                List<Student> students = new StudentDaoPostgres().findAll().stream().filter(s -> s.getGroupId() == id)
                        .collect(Collectors.toList());
                request.setAttribute("students", students);
                request.setAttribute("group", groupDaoPostgres.findOne(id));
            }
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/groups.jsp");
        dispatcher.forward(request, response);
    }
}
