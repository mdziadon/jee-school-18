package pl.coderslab.controller;

import pl.coderslab.dao.SolutionDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/solutions/delete")
public class SolutionDeleteServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        int solutionId = Integer.parseInt(id);

        SolutionDao dao = new SolutionDao();
        dao.delete(solutionId);
        response.sendRedirect("/");
    }

}
