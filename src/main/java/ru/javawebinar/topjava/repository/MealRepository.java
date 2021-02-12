package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

public interface MealRepository {
    Meal addMeal(Meal meal);
    Meal findByIdMeal(Integer id);
    Meal updateMeal(Meal meal);
    void deleteMeal(Integer id);
}
