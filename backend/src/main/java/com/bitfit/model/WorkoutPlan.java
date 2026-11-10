package com.bitfit.model;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

/**
 * The workout half of a {@link WeeklyPlan}: a template that repeats every week, with one
 * workout per day. A day with no workout is a rest day.
 *
 * Days are stored by name ("MONDAY" ... "SUNDAY") because Firestore map keys must be strings.
 */
public class WorkoutPlan {

    private FitnessGoal goal;
    private Map<String, Workout> days = new HashMap<>();

    public WorkoutPlan() {
    }

    public WorkoutPlan(FitnessGoal goal) {
        this.goal = goal;
    }

    /** Sets or replaces the workout for a day. */
    public void setDay(DayOfWeek day, Workout workout) {
        days.put(day.name(), workout);
    }

    /** Makes the day a rest day. Returns true if a workout was removed. */
    public boolean clearDay(DayOfWeek day) {
        return days.remove(day.name()) != null;
    }

    /** The workout planned for a day, or null on a rest day. */
    public Workout workoutFor(DayOfWeek day) {
        return days.get(day.name());
    }

    public FitnessGoal getGoal() { return goal; }
    public void setGoal(FitnessGoal goal) { this.goal = goal; }

    public Map<String, Workout> getDays() { return days; }
    public void setDays(Map<String, Workout> days) { this.days = days; }
}
