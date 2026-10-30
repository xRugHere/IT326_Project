package com.bitfit.model;

import java.util.ArrayList;
import java.util.List;

/** A user's workout plan: the workouts they plan to do toward a goal. */
public class WorkoutPlan {

    private String planId;
    private String accountId;
    private FitnessGoal goal;
    private List<Workout> workouts = new ArrayList<>();
    private List<String> completedWorkoutIds = new ArrayList<>();

    public WorkoutPlan() {
    }

    public WorkoutPlan(String planId, String accountId, FitnessGoal goal) {
        this.planId = planId;
        this.accountId = accountId;
        this.goal = goal;
    }

    public void addWorkout(Workout workout) {
        // TODO
    }

    /** Returns true if a workout was removed. */
    public boolean removeWorkout(String workoutId) {
        // TODO
        return false;
    }

    /** Marks a workout in this plan as done. */
    public void completeWorkout(String workoutId) {
        // TODO
    }

    public String getPlanId() { return planId; }
    public void setPlanId(String planId) { this.planId = planId; }

    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    public FitnessGoal getGoal() { return goal; }
    public void setGoal(FitnessGoal goal) { this.goal = goal; }

    public List<Workout> getWorkouts() { return workouts; }
    public void setWorkouts(List<Workout> workouts) { this.workouts = workouts; }

    public List<String> getCompletedWorkoutIds() { return completedWorkoutIds; }
    public void setCompletedWorkoutIds(List<String> completedWorkoutIds) { this.completedWorkoutIds = completedWorkoutIds; }
}
