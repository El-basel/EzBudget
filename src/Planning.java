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

    //change to return Goal when goal is implemented
//    public Goal getGoal(int goalID){
//        return database.retrieveGoal(goalID);
//    }

    //change to return Goal[]
//    public void getGoals(){
//        //return database.retrieveGoals();
//    }

//    public boolean setReminder(String title, String date, String message){
//        Reminder reminder = new Reminder(title, date, message);
//        return database.insertReminder(reminder);
//    }
}
