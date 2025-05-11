public class Planning {
    //figure out how to send emails to user using notification service class
    private NotificationService notificationService;
    private Database database;
    //private User user;

    //add user id
    public Planning(Database database, NotificationService notificationService){
        this.database = database;
        this.notificationService = notificationService;
    }

    public void setGoal(int target, String startDate, String endDate, String discription){
        Goal goal = new Goal(target, startDate, endDate, discription);
        database.insertGoal(goal);
    }

    public void createSavingPlan(int goal, int startAmount, int monthlyContribution){
        SavingPlan savingPlan = new SavingPlan(goal, startAmount, monthlyContribution);
        //database.insertSavingPlan(savingPlan);
    }

    //add user id in constructor
    public void setReminder(String title, String date, String message){
        Reminder reminder = new Reminder();
        reminder.setTitle(title);
        reminder.setDate(date);
        reminder.setMessage(message);
        //database.insertReminder(reminder);
    }

    //change to return Goal when goal is implemented
    public void getGoal(int goalID){
        //return database.retrieveGoal(goalID);
    }

    //change to return Goal[]
    public void getGoals(){
        //return database.retrieveGoals();
    }

    //change to return SavingPlan when it is implemented
    public void getplan(int planID){
        //return database.retrieveSavingPlan(planID);
    }

    //change to return SavingPlan[] when it is implemented
    public void getplans(){
        //return database.retrieveSavingPlans();
    }
}
