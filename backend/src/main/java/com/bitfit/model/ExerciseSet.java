package com.bitfit.model;

/** One set of an exercise, for example 8 reps at 135 lbs. Use 0 lbs for bodyweight exercises. */
public class ExerciseSet {

    private int reps;
    private double weightLbs;

    public ExerciseSet() {
    }

    public ExerciseSet(int reps, double weightLbs) {
        this.reps = reps;
        this.weightLbs = weightLbs;
    }

    public int getReps() { return reps; }
    public void setReps(int reps) { this.reps = reps; }

    public double getWeightLbs() { return weightLbs; }
    public void setWeightLbs(double weightLbs) { this.weightLbs = weightLbs; }
}
