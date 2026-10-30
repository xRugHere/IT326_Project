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
        // TODO
    }

    /** Returns true if a meal was removed. */
    public boolean removeMeal(String mealId) {
        // TODO
        return false;
    }

    /** Total calories of every meal in the plan. */
    public int calculatePlannedCalories() {
        // TODO
        return 0;
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
    public void setMeals(List<Meal> meals) { this.meals = meals; }
}
