package com.bitfit.model;

import java.util.ArrayList;
import java.util.List;

/** A single workout made up of one or more exercises. */
public class Workout {

    private String workoutId;
    private String name;
    private List<Exercise> exercises = new ArrayList<>();

    public Workout() {
    }

    public Workout(String workoutId, String name) {
        this.workoutId = workoutId;
        this.name = name;
    }

    public void addExercise(Exercise exercise) {
        // TODO
    }

    /** Returns true if an exercise was removed. */
    public boolean removeExercise(String exerciseName) {
        // TODO
        return false;
    }

    public String getWorkoutId() { return workoutId; }
    public void setWorkoutId(String workoutId) { this.workoutId = workoutId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Exercise> getExercises() { return exercises; }
    public void setExercises(List<Exercise> exercises) { this.exercises = exercises; }
}
