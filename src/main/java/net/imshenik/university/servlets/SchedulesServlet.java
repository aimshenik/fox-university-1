package net.imshenik.university.servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.imshenik.university.dao.ScheduleDaoPostgres;
import net.imshenik.university.domain.Schedule;

@WebServlet("/schedules")
public class SchedulesServlet extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/schedules.jsp");
            List<Schedule> schedules = new ScheduleDaoPostgres().findAll();
            request.setAttribute("schedules", schedules);
            dispatcher.forward(request, response);
    }
}
