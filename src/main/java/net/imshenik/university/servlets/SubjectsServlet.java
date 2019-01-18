package net.imshenik.university.servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.imshenik.university.dao.DaoException;
import net.imshenik.university.dao.SubjectDaoPostgres;
import net.imshenik.university.domain.Subject;

@WebServlet("/subjects")
public class SubjectsServlet extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Subject> subjects = new SubjectDaoPostgres().findAll();
            request.setAttribute("subjects", subjects);
            RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/subjects.jsp");
            dispatcher.forward(request, response);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
}
