package com.bitfit.model;
import com.bitfit.abstractModels.*;
/** One exercise inside a workout, for example Bench Press, with every set the user does of it. */
public class weightlessExercise extends abstractExercise<Integer>{
    
    
    
    public weightlessExercise() { };
    public weightlessExercise(String name) {
        setName(name);
    }

    /*
    *adds a set to the list of sets with a number of reps
    *@params reps value that reps should be set to 
    */
    public void addSet(Integer unit){
        getSets().add(unit);
    }
    /* 
    * removes a set at a given index.
    * @params the index of the set that needs to be removed
    * @returns the value removed 
    */
    public Integer removeSet(int index)throws IndexOutOfBoundsException{
        if(index<0||index>setsNum()){
            throw new IndexOutOfBoundsException();
        } else{
        return getSets().remove(index);
        }
    }
    /*
    * sets the reps of a set in the list of sets. 
    *@params reps value that reps should be set to 
    *@params index the current set we would like to adjust
    *@throws NullPointerException if set is empty or index is larger then sets length
    */
    public void setReps(int rep, int index)throws IndexOutOfBoundsException{
        if(index<0 || setsNum()<index){
            throw new IndexOutOfBoundsException();
        } else {
            getSets().set(rep,index);
        }
    }
    /*
    * gets the reps of a set in the list of sets. 
    *@params index the set we want to take the value from
    *@throws NullPointerException if set is empty or index is larger then sets length
    */
    public int getReps(int index)throws IndexOutOfBoundsException{
        if(index<0 || setsNum()<index){
            throw new IndexOutOfBoundsException();
        } else {
            return getSets().get(index);
        }
    }





}
