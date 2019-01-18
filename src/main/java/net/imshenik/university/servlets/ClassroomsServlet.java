package net.imshenik.university.servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.imshenik.university.dao.ClassroomDaoPostgres;
import net.imshenik.university.domain.Classroom;

@WebServlet("/classrooms")
public class ClassroomsServlet extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            List<Classroom> classrooms = new ClassroomDaoPostgres().findAll();
            request.setAttribute("classrooms", classrooms);
            RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/classrooms.jsp");
            dispatcher.forward(request, response);
    }
}
