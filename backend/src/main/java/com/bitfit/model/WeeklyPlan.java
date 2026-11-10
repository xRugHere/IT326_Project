package com.bitfit.model;

/**
 * A user's weekly plan: a workout plan and a meal plan that repeat every week. Each account has
 * one, stored in Firestore as one document in the "weeklyPlans" collection with the account ID
 * as the document ID.
 */
public class WeeklyPlan {

    private String accountId;
    private WorkoutPlan workoutPlan = new WorkoutPlan();
    private MealPlan mealPlan;      // TODO: meal planning comes later
    private long updatedAt;

    public WeeklyPlan() {
    }

    public WeeklyPlan(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    public WorkoutPlan getWorkoutPlan() { return workoutPlan; }
    public void setWorkoutPlan(WorkoutPlan workoutPlan) { this.workoutPlan = workoutPlan; }

    public MealPlan getMealPlan() { return mealPlan; }
    public void setMealPlan(MealPlan mealPlan) { this.mealPlan = mealPlan; }

    public long getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(long updatedAt) { this.updatedAt = updatedAt; }
}
