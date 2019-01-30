package net.imshenik.university.servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.imshenik.university.dao.SubjectDaoPostgres;
import net.imshenik.university.domain.Subject;

@WebServlet("/subjects")
public class SubjectsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            List<Subject> subjects = new SubjectDaoPostgres().findAll();
            request.setAttribute("subjects", subjects);
            request.getRequestDispatcher("jsp/all_subjects.jsp").forward(request, response);
    }
}
