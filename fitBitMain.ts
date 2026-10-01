/*
*                                    BitFit MainPage/login
*   This object is the main page startup for BitFit. The users should be able to 
*  open the app/website and start on a login page, then after enter thier information
*  they will be sent to their home page that has the thier information. -alex j.
*/

//I just like taking notes as I code please feel free to change these if you think they don't fit the section
//or if you have a better way of doing it

//login page requires, textbox creation, background manipulation, art?, make new account
//check the database of user logins if it contains username that uses password
function checkDataBase(username :string, password :string): bool{
    //temp method
    return false;
}
//get input via textboxes , working out logic first cause I need to learn the texbox creation and input catching 
username :string;
password :string;
if(checkDataBase(username,password)){
//if login sucessful -> go to mainPage
} else {
//if login failed(info not in database)-> messege tell user "invaild username or password"
}
//below login should be create account button (clicked)-> makes create account textboxes appear
//need texbox for username, password, confirm password(do we want username to be an email??)

//for the purpose of displaying error messeges on the create account could combine with the other error messege 
//from the signing in if we wanted?
function errorMessege(): void{
    if(req1 == false){
        //user does not meet the standards of our password 
    } else if (req2 == false){
        //user does not have pw1 and pw2 matching
    } else if (req3 == false){
        //username is already within the system need to change it 
    }
}
//confirm button -> checks if username is taken -> checks that password fits requirements? -> checks that both password boxes match
req1:bool = false; req2:bool = false; req3:bool = true;
if(req1=passwordReq(pw1)&&req2=(pw1 === pw2)&& !(req3=checkDataBase(username))){
//->success-> add information into the data base takes user back to the login page
    loginPageDisplay();
}else{
// -> fail error messege relavant to the infomation that is not correct
    errorMessege(req1,req2,req3);
}

//mainPage displays only after login is succesful

//get rank info from account information-> diplay info in center of page along with rankArt

//make button/clickable link for other pages want to make them in a pull out tab/ drop down menu

//workout/food plan(clicked)-> go to workout/food plan page?

//challange page(clicked)-> go to challange page

//friends list (clicked)-> go to friends list page

//what other tabs will we have? is it worth it to not have a drop down menu if we don't have a lot of things for the user interact with


//profile image top left display profie pic saved in database
//(clicked)-> open profile page 

