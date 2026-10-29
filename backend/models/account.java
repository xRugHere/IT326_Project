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
String password = "passwordddddd";
//constuctors_________________________________________________________________________
//should only be called when we are creating a new account
private account(username:String, email:String,password:string){
    //might use a differnt more method of account creation like discribed in class however for now this is a temp
    if(checkDataBase(username) == null){
        if(passwordVaild(password)){
            this.username = username;
            this.email = email;
            //we need a password var for each account so they can login need to change this in our class diagram
            this.password = password;
        }
    }

}

//methods_____________________________________________________________________________
    
    //signing into an account checks if the account exists in the database then checks if the password matches the one on that account
    //if the passwords match we set the account to this current users info 
    //need to change to have signIn in a user operations class and make a new instance of account after the sign in is successful
    public void signIn(String user, String password) throws IllegalArgumentException{
        account* temp = null;
        if(!((temp = checkDataBase(user)) == null)){
            if(temp.password.equals(password)){
                //might need to make a custom setter for this 
                this = temp;
            } else {
                //if password doesn't match throw a error
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