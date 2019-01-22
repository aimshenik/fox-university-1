package net.imshenik.university.servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.imshenik.university.dao.StudentDaoPostgres;
import net.imshenik.university.domain.Student;

@WebServlet("/students")
public class StudentsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            List<Student> students = new StudentDaoPostgres().findAll();
            request.setAttribute("students", students);
            RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/students.jsp");
            dispatcher.forward(request, response);
    }
}
