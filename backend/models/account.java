/*
*Class:Account
*purpose:
*requires: needs progress and challengeList to be set up
*/

class account{
//Att__________________________________________________________________________________
int accountID = "-1";
String username = "nobody";
String email = "nobody";
int friendCode = "-1";

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
    public void signIn(String user, String password) throws{
        if(){
            if(){

            } else {
                throw new IllegalArgumentException();
            }
        }else { 
            //we can change this to be a custom error later
            throw new IllegalArgumentException();
        }

    }

    //signing out of an account
    public void signOut(){

    }


    //open the challenge page?
    public void getProgress(){


    }

    //display the ChallengeList?
    public void getChallengeList():void{

    }

}