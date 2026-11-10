package com.bitfit.model;

import java.util.ArrayList;
import java.util.List;

/** One exercise inside a workout, for example Bench Press, with every set the user does of it. */
public class Exercise {

    private String name;
    private List<ExerciseSet> sets = new ArrayList<>();

    public Exercise() {
    }

    public Exercise(String name) {
        this.name = name;
    }

    public void addSet(ExerciseSet set) {
        sets.add(set);
    }

    /** Returns true if a set was removed. */
    public boolean removeSet(int index) {
        if (index < 0 || index >= sets.size()) {
            return false;
        }
        sets.remove(index);
        return true;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<ExerciseSet> getSets() { return sets; }
    public void setSets(List<ExerciseSet> sets) { this.sets = sets; }
}
