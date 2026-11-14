package com.bitfit.model;

import java.util.ArrayList;
import java.util.List;

/** A user's meal plan: meals that add up to a daily calorie goal. */
public class MealPlan {

    private String planId;
    private String accountId;
    private FitnessGoal goal;
    private int dailyCalorieGoal;
    private List<Meal> meals = new ArrayList<>();

    public MealPlan() {
    }

    public MealPlan(String planId, String accountId, FitnessGoal goal, int dailyCalorieGoal) {
        this.planId = planId;
        this.accountId = accountId;
        this.goal = goal;
        this.dailyCalorieGoal = dailyCalorieGoal;
    }

    public void addMeal(Meal meal) {
        if (meal == null) {
            return;
        }
        if (meals == null) {
            meals = new ArrayList<>();
        }
        meals.add(meal);
    }

    /** Returns true if a meal was removed. */
    public boolean removeMeal(String mealId) {
        if (meals == null || mealId == null) {
            return false;
        }

        return meals.removeIf(item -> item != null && mealId.equals(item.getMealId()));
    }

    /** Total calories of every meal in the plan. */
    public int calculatePlannedCalories() {
        if (meals == null) {
            return 0;
        }

        int total = 0;
        for (Meal meal : meals) {
            if (meal != null) {
                total += meal.calculateTotalCalories();
            }
        }
        return total;
    }

    public String getPlanId() { return planId; }
    public void setPlanId(String planId) { this.planId = planId; }

    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    public FitnessGoal getGoal() { return goal; }
    public void setGoal(FitnessGoal goal) { this.goal = goal; }

    public int getDailyCalorieGoal() { return dailyCalorieGoal; }
    public void setDailyCalorieGoal(int dailyCalorieGoal) { this.dailyCalorieGoal = dailyCalorieGoal; }

    public List<Meal> getMeals() { return meals; }
    public void setMeals(List<Meal> meals) { this.meals = meals == null ? new ArrayList<>() : meals; }
}
