package com.bitfit.model;

import com.bitfit.abstractModels.*;
public class weightedExercise extends abstractExercise<ExerciseSet>{

    public weightedExercise(String name){
        setName(name);
    }
    /*
    * adds a set to the list of sets
    *@params  ExerciseSet unit contains reps and weight
    */
    public void addSet(ExerciseSet unit){
        getSets().add(unit);
    }
    /*
    * remove a Set from the list of sets
    *@params int index of set that is to be removed 
    *@Return ExerciseSet item, the removed value of the set
    *@throws IndexOutOfBoundsException when index is out of scope of sets list
    */
    public ExerciseSet removeSet(int index){
         if(index<0||index>setsNum()){
            throw new IndexOutOfBoundsException();
        } else{
        return getSets().remove(index);
        }
    }

    /*
    * set the reps and weights of an 
    *@params  int reps number of reps for a set
    *@params  double weightLbs amount of weight for a set 
    *@params index of the set that is to be changed 
    *@throws IndexOutOfBoundsException when index is out of scope of sets list
    */
    public void setSetsValue(int rep,double weightLbs, int index)throws IndexOutOfBoundsException{
        if(index<0 || setsNum()<index){
            throw new IndexOutOfBoundsException();
        } else {
            getSets().get(index).setReps(rep);
            getSets().get(index).setWeightLbs(weightLbs);
        }
    }

    /*
    * set only reps for a given set index
    *@params int reps number of reps for a set
    *@params index of the set that is to be changed 
    *@throws IndexOutOfBoundsException when index is out of scope of sets list
    */
    public void setReps(int rep, int index)throws IndexOutOfBoundsException{
        if(index<0 || setsNum()<index){
            throw new IndexOutOfBoundsException();
        } else {
            getSets().get(index).setReps(rep);
        }
    }
    /*
    * set only weightLbs for a given set index
    *@params double weightLbs that the set will change its value to 
    *@params index of the set that is to be changed 
    *@throws IndexOutOfBoundsException when index is out of scope of sets list
    */
    public void setWeightLbs(double weightLbs, int index)throws IndexOutOfBoundsException{
        if(index<0 || setsNum()<index){
            throw new IndexOutOfBoundsException();
        } else {
            getSets().get(index).setWeightLbs(weightLbs);
        }
    }
    /*
    * gets the ExeciseSet of a set in the list of sets. 
    *@params index the set we want to take the Execise set from(use the returns on the exercise set to get values)
    *@throws NullPointerException if set is empty or index is larger then sets length
    */
    public ExerciseSet getSetsValue(int index)throws IndexOutOfBoundsException{
        if(index<0 || setsNum()<index){
            throw new IndexOutOfBoundsException();
        } else {
            return getSets().get(index);
        }
    }



}
