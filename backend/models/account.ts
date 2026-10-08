/*
*Class:Account
*purpose:
*requires: needs progress and challengeList to be set up
*/

class account{


//Att__________________________________________________________________________________
accountID:String;
username:String;
email:String;
friendCode:String;

//constuctors_________________________________________________________________________
//should only be called when we are creating a new account
private account(username:String, email:String,password:string){
    //might use a differnt more method of account creation like discribed in class however for now this is a temp
    if(!(checkDataBase(username))){
        if(passwordVaild(password)){
            this.username = username;
            this.email = email;
            //we need a password var for each account so they can login need to change this in our class diagram
            this.password = password;
        }
    }

}

//methods_____________________________________________________________________________
//signing into an account, should this return a boolean if it fails?
function signIn():void{

}

//signing out of an account
function signOut():void{

}


//open the challenge page?
function getProgress():void{


}

//display the ChallengeList?
function getChallengeList():void{

}

}