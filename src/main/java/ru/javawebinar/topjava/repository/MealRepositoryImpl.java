package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealRepositoryImpl implements MealRepository{
    public static Map<Integer,Meal> map = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);
    {
        for(Meal meal : MealsUtil.meals){
            saveMeal(meal);
        }
    }

    @Override
    public Collection<Meal> getAll() {
        return map.values();
    }

    @Override
    public Meal saveMeal(Meal meal) {
        if(isNew(meal.getId())){
            meal.setId(counter.incrementAndGet());
            map.put(counter.get(),meal);
        }
        map.put(meal.getId(), meal);
     return meal;
    }

    @Override
    public Meal findByIdMeal(Integer id) {
        if(map.get(id) != null){
            return map.get(id);
        }
        return null;
    }

    @Override
    public void deleteMeal(Integer id) {
        map.remove(id);
    }
    private boolean isNew(Integer id){
        return findByIdMeal(id) == null;
    }
}
