package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.MealRepositoryImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);
    private MealRepository mealRepository;
    private static final String LIST_OF_MEALS = "/meals.jsp";
    private static final String INSERT_OR_EDIT = "/edit.jsp";

    @Override
    public void init() throws ServletException {
        mealRepository = new MealRepositoryImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action == null){
            req.setAttribute("mealsList", MealsUtil.getAllMeals(mealRepository.getAll(),MealsUtil.CALORIES_PER_DAY));
            req.getRequestDispatcher("/meals.jsp").forward(req,resp);
        }else if(action.equalsIgnoreCase("delete")){
            int id = Integer.parseInt(req.getParameter("mealId"));
            mealRepository.deleteMeal(id);
            resp.sendRedirect("meals");
        } else if(action.equalsIgnoreCase("edit")){
            int id = Integer.parseInt(req.getParameter("mealId"));
            Meal meal = mealRepository.findByIdMeal(id);
            req.setAttribute("meal",meal);
            req.getRequestDispatcher("/edit.jsp").forward(req,resp);
        }else {
            Meal meal = new Meal(LocalDateTime.now(),"",0);
            req.setAttribute("meal",meal);
            req.getRequestDispatcher("/edit.jsp").forward(req,resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");
    }
}
