public class Planning {
    private static Planning instance;
    //figure out how to send emails to user using notification service class
    private NotificationService notificationService;
    private Database database;
    //private User user;

    //add user id
    private Planning(Database database){
        this.database = database;
        this.notificationService = NotificationService.getInstance(database);
    }

    public static Planning getInstance(Database database, NotificationService notificationService){
        if (instance == null){
            synchronized (Planning.class) {
                if (instance == null){
                    instance = new Planning(database);
                }
            }
        }
        return instance;
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
