package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.Collection;

public interface MealRepository {
    Collection<Meal> getAll();
    Meal saveMeal(Meal meal);
    Meal findByIdMeal(Integer id);
    void deleteMeal(Integer id);
}
