package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.summingInt;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        // System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> list = new ArrayList<>();
        for (UserMeal meal1 : meals) {
            if (TimeUtil.isBetweenHalfOpen(meal1.getDateTime().toLocalTime(), startTime, endTime)) {
                int sumOfCalories = 0;
                for (UserMeal meal2 : meals) {
                    if (meal2.getDateTime().getDayOfMonth() == meal1.getDateTime().getDayOfMonth()) {
                        sumOfCalories += meal2.getCalories();
                    }
                }
                list.add(getObject(meal1.getDateTime(), meal1.getDescription(), meal1.getCalories(), sumOfCalories));
            }
        }
        return list;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> meal;
        Map<String, Integer> map3 = meals.stream()
                .collect(Collectors.groupingBy(UserMeal::getDate,
                        summingInt(UserMeal::getCalories)));
        meal = meals.stream().filter((s) -> TimeUtil.isBetweenHalfOpen(s.getDateTime().toLocalTime(), startTime, endTime))
                .map((s) -> getObject(s.getDateTime(), s.getDescription(), s.getCalories(), map3.get(s.getDate()))).collect(Collectors.toList());
        return meal;
    }

    public static UserMealWithExcess getObject(LocalDateTime dateTime, String description, int caloriesPerDay, int calories) {
        if (calories > 2000) {
            return new UserMealWithExcess(dateTime, description, caloriesPerDay, true);
        } else {
            return new UserMealWithExcess(dateTime, description, caloriesPerDay, false);
        }
    }
}
