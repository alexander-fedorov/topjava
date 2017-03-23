package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        List<UserMealWithExceed> res = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(22,0), 2000);
        for (UserMealWithExceed meal : res) {
            System.out.println(meal.getDateTime().toLocalDate() + " " +
                    meal.getDateTime().toLocalTime() + ", " + meal.getDescription() + ", " + meal.getCalories() + " " +
                    meal.isExceed());
        }

    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> userCalPerDayMap = getUserCalPerDate(mealList);
        List<UserMealWithExceed> result = new ArrayList<>();
        for(UserMeal meal : mealList) {
            if (TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                result.add(new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                        userCalPerDayMap.get(meal.getDateTime().toLocalDate()) > caloriesPerDay));
            }

        }
        return result;
    }

    public static Map<LocalDate, Integer> getUserCalPerDate(List<UserMeal> mealList) {
        Map<LocalDate, Integer> userCalPerDayMap = new HashMap<>();
        LocalDate mealDate;
        for (UserMeal meal : mealList) {
            mealDate = meal.getDateTime().toLocalDate();
            userCalPerDayMap.merge(mealDate,meal.getCalories(),(v1,v2) -> v1 + v2);
            }
        return userCalPerDayMap;
    }
}
