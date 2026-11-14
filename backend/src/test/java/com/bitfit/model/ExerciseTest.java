package com.bitfit.model;

public class ExerciseTest {
    weightedExercise wE;
    weightlessExercise wLE;
    //sets up by making a new exercise of both concrete types
    @Before
    public void setUp(){
        weightedExercise wE = new weightedExercise("Deadlift");
        weightlessExercise wLE = new weightlessExercise("pushup");
    }
    //adds a Set to the list of sets for an Exercise
    @Test
    public void addSet(){
        ExerciseSet testSet = new ExerciseSet(1,100);
        wE.addSet(testSet);
        wLE.addSet(1);
    }
    //sets the value of Exercise 
    @Test
    public void setValue(){
        ExerciseSet testSet = new ExerciseSet(1,100);
        wE.addSet(testSet);
        wLE.addSet(1);
        wE.setSetsValue(1,10.0,0);
        wLE.setReps(1,0);
    }
    //gets the value of the exercise and checks that it is what is expected 
    @Test 
    public void getValue(){

    }
    //deletes the Excerise concrete objects and cleans memory
    @After
    public void cleanUp(){
    

    }
}
