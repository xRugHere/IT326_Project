//-alex reworking artitecture based on class diagram assuming this class will just have 
//each page layout and just call one diplay method of the layout we want?

//just a switch to call any display of a page that we want using a string for the page id 
function displayPage(page:String):void{
    switch(page){
        case "Login":
            loDisplay();
        case "Main":
            mDisplay();
        case "ChallengeList":
            clDisplay();
        case "WorkoutPlan":
            wDisplay();
        case "MealPlan":
            mpDisplay();
        case "Profile":
            pDisplay();
        case "Leaderboard":
            leDisplay();
        default: 
        //deciding on what error to throw or we could just show an error page 
        throw new Error
    }
}

//display page for the login page should display two textboxes in the middle of the screen one for the username and one for the password
//two buttons one for signin and the other for create new account bellow the text boxes
function loDisplay():void{

}

//diplays the main page for the user account we are logined into, prob needs sone discussion on how we wnat this to look
function mDisplay():void{

}

//displays the page for the challenge list should show a list of all the challenges yet to be completed 
function clDisplay():void{

}
//diplays the workout plan page should have a week schedule of the workouts to be completed
function wDisplay():void{

}

//displays the meal plan for a user , need to talk more about how we expect this layout to look
function mpDisplay():void{

}
//displays the profile page of a user should show the user information(friend code, stats?, and ...) and allow the user to delete account,
function pDisplay():void{

}
//display the leaderboard page should show a ranking of all current users, and a way to filter the list and maybe see self ranking compared to other users 
function leDisplay():void{

}