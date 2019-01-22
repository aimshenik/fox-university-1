package net.imshenik.university.servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.imshenik.university.dao.TeacherDaoPostgres;
import net.imshenik.university.domain.Teacher;

@WebServlet("/teachers")
public class TeachersServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            List<Teacher> teachers = new TeacherDaoPostgres().findAll();
            request.setAttribute("teachers", teachers);
            request.getRequestDispatcher("jsp/teachers.jsp").forward(request, response);
    }
}
