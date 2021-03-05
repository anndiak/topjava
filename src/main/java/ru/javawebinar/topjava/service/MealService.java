package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.Util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }
    public Meal get(int id,int userId){

        return checkNotFoundWithId(repository.get(id, userId),id);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public List<Meal> getBetweenInclusive(@Nullable LocalDate startDate, @Nullable LocalDate endDate, int userId){
        return repository.getBetweenHalfOpen(DateTimeUtil.getStartInclusive(startDate), DateTimeUtil.getEndInclusive(endDate), userId);
    }

    public List<Meal> getAll(int userId){
        return (List<Meal>) repository.getAll(userId);
    }

    public void update(Meal meal, int userId){
        checkNotFoundWithId(repository.save(meal, userId),userId);
    }

    public Meal save(Meal meal,int userId){
        return repository.save(meal, userId);
    }

}