package pl.coderslab.controller;

import pl.coderslab.dao.ExerciseDao;
import pl.coderslab.dao.SolutionDao;
import pl.coderslab.dao.UserDao;
import pl.coderslab.model.Exercise;
import pl.coderslab.model.Solution;
import pl.coderslab.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/solutions/add")
public class SolutionAddServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String exerciseId = request.getParameter("exerciseId");
        String userId = request.getParameter("userId");
        String description = request.getParameter("description");

        int exerciseIdInt = Integer.parseInt(exerciseId);
        int userIdInt = Integer.parseInt(userId);

        Solution solution = new Solution(exerciseIdInt,userIdInt,description);
        SolutionDao solutionDao = new SolutionDao();
        solutionDao.create(solution);
        response.sendRedirect("/");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserDao userDao = new UserDao();
        ExerciseDao exerciseDao = new ExerciseDao();

        User[] users = userDao.findAll();
        Exercise[] exercises = exerciseDao.findAll();

        request.setAttribute("users", users);
        request.setAttribute("exercises", exercises);

        getServletContext().getRequestDispatcher("/solutionAdd.jsp")
                .forward(request, response);

    }
}
