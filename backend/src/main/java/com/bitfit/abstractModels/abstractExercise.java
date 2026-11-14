package com.bitfit.abstractModels;
import java.util.List;

public abstract class abstractExercise<T>{

    private String name;
    private int timeMins;
    private List<T> sets;

    //getters and setters 
    public String getName(){return this.name;};
    public void setName(String name){this.name = name;};
    
    //return time
    public int getTime(){return this.timeMins;};
    public void setTime(int timeMins){this.timeMins = timeMins;};

    // get set List
    public List<T> getSets(){return this.sets;};
    //number of sets in a list of sets(ie the size);
    public int setsNum(){return this.sets.size();};
    public void setSets(List<T> sets){this.sets = sets;};
    //removing and returning the value removed from the list of sets
    abstract public T removeSet(int index);
    abstract public void addSet(T unit);
}