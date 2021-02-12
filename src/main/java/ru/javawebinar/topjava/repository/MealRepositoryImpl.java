package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealRepositoryImpl implements MealRepository{
    Map<Integer,Meal> map = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public Meal addMeal(Meal meal) {
        return null;
    }

    @Override
    public Meal findByIdMeal(Integer id) {
        return null;
    }

    @Override
    public Meal updateMeal(Meal meal) {
        return null;
    }

    @Override
    public void deleteMeal(Integer id) {

    }
}
