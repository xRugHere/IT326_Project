/*
*Class:exercise
*purpose:
*requires: one of the base classes should not require other classes info
*/




//Att__________________________________________________________________________________
nameE:String;
amountE:int;
unit:String;


//methods_____________________________________________________________________________

//getter for the name of the exercise (ie pushups jumping jacks ...etc)
function getName():String{
return nameE;
}

//getter for the amount fo reps or units that need to be done ,does this require units(?)
function getAmount():int{
    return amount;
}