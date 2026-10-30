package com.bitfit.model;

import java.util.ArrayList;
import java.util.List;

/** Tracks a user's points, rank, workouts and calories. Each Account owns one Progress, which holds the Rank. */
public class Progress {

    private int totalPoints;
    private Rank rank;                                          // TODO: start new users at Rank.forPoints(0)
    private int workoutsCompleted;
    private List<String> workoutHistory = new ArrayList<>();   // IDs of logged workouts
    private int caloriesToday;
    private List<Integer> calorieHistory = new ArrayList<>();  // one total per past day

    public Progress() {
    }

    /** Adds points and updates the rank. Returns true if the user moved up a rank. */
    public boolean addPoints(int points) {
        // TODO
        return false;
    }

    /** Records a completed workout and awards its points. Returns true if the user ranked up. */
    public boolean recordWorkout(String workoutLogId, int pointsEarned) {
        // TODO
        return false;
    }

    /** Adds a meal's calories to today's total. */
    public void recordMeal(int calories) {
        // TODO
    }

    /** Saves today's calorie total to the history and resets it to 0. */
    public void startNewDay() {
        // TODO
    }

    public int getTotalPoints() { return totalPoints; }
    public void setTotalPoints(int totalPoints) { this.totalPoints = totalPoints; }

    public Rank getRank() { return rank; }
    public void setRank(Rank rank) { this.rank = rank; }

    public int getWorkoutsCompleted() { return workoutsCompleted; }
    public void setWorkoutsCompleted(int workoutsCompleted) { this.workoutsCompleted = workoutsCompleted; }

    public List<String> getWorkoutHistory() { return workoutHistory; }
    public void setWorkoutHistory(List<String> workoutHistory) { this.workoutHistory = workoutHistory; }

    public int getCaloriesToday() { return caloriesToday; }
    public void setCaloriesToday(int caloriesToday) { this.caloriesToday = caloriesToday; }

    public List<Integer> getCalorieHistory() { return calorieHistory; }
    public void setCalorieHistory(List<Integer> calorieHistory) { this.calorieHistory = calorieHistory; }
}
