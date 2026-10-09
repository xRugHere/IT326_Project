/*
*Class:challenge
*purpose:
*requires:workoutPlan to be created (?)thought both ref the a workout?
*/
class challange{
//Att__________________________________________________________________________________
    sender:account;
    recipient:account;
    deadline:Date;
    status:String;
    pointValue:int;
//constuctors_________________________________________________________________________
    //createing a new challenge 
    private challange(){

    }
//methods_____________________________________________________________________________
    //accepting a challenge (need to talk more about how this process would be expected to work)
    function accept():void{

    }

    //declining a challenge (same as accept just changing the state?)
    function decline():void{

    }

    //canceling an already sent chanllange or canceling an active challange?
    function cancel():void{

    }

    //do we need to check completion if they are just going to mark it themself, unless this is for past challenges for displaying
    function checkCompleton():boolean{
        //temp
        if(true){
            return true;
        }else{}
        return false;
        }
    }
}