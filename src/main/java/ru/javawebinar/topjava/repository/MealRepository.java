package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

public interface MealRepository {
    Meal saveMeal(Meal meal);
    Meal findByIdMeal(Integer id);
    void deleteMeal(Integer id);
}
