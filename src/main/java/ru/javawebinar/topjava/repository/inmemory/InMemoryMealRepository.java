package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.util.CollectionUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.Util;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository.Admin_ID;
import static ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository.USER_ID;

public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> usersMealsMap = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal,USER_ID));
        save( new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510),Admin_ID);
        save( new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ужин", 1500),Admin_ID);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Map<Integer,Meal> meals = usersMealsMap.computeIfAbsent(userId, ConcurrentHashMap::new);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meals.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return meals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer,Meal> meals = usersMealsMap.get(userId);
        return meals != null && meals.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer,Meal> meals = usersMealsMap.get(userId);
        return meals == null? null : meals.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return (List<Meal>) getAllFiltred(userId, meal -> true);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDate, LocalDateTime endTime, int userId) {
        return (List<Meal>) getAllFiltred(userId, meal -> Util.isBetweenHalfOpen(meal.getDateTime(),startDate,endTime));
    }


    private Collection<Meal> getAllFiltred(int userId, Predicate<Meal> filter) {
        Map<Integer,Meal> meals = usersMealsMap.get(userId);
        return CollectionUtils.isEmpty(meals)? Collections.emptyList(): meals.values().stream()
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());

    }


}

