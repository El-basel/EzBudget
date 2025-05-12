import java.util.Scanner;

public class Planning {
    private static Planning instance;
    //figure out how to send emails to user using notification service class
    private NotificationService notificationService;
    private Database database;
    private Scanner scanner;
    //private User user;

    //add user id
    private Planning(){
        this.database = Database.getInstance();
        this.notificationService = NotificationService.getInstance(database);
        this.scanner = new Scanner(System.in);
    }

    private int parse_int(String input, String error_message) {
        int num = -1;
        try {
            if(input.isEmpty()) {
                throw new NumberFormatException("Empty input");
            }
            num = Integer.parseInt(input);
            return num;
        } catch (NumberFormatException e) {
            System.err.println(input + " " + error_message);
            return -1;
        }
    }

    public static Planning getInstance(NotificationService notificationService){
        if (instance == null){
            synchronized (Planning.class) {
                if (instance == null){
                    instance = new Planning();
                }
            }
        }
        return instance;
    }

    public void setGoal(int target, int saving_amount, String description){
        Goal goal = new Goal(target, saving_amount,  description);
        database.insertGoal(goal);
    }


    //add user id in constructor
    public void setReminder(String title, String date, String message){
        Reminder reminder = new Reminder();
        reminder.setTitle(title);
        reminder.setDate(date);
        reminder.setMessage(message);
//        database.insertReminder(reminder);
    }

    public boolean createGoal() {
        System.out.println("Insert Goal Name: ");
        String description = scanner.nextLine();
        if(description.isBlank()) {
            System.out.println("Empty input");
            return false;
        }
        System.out.println("Insert Target Amount: ");
        String unparsed_target = scanner.nextLine();
        int target = parse_int(unparsed_target, "is not a valid target, it should be a number");
        if(target == -1){
            return false;
        }
        System.out.println("Insert Amount to be saved each month: ");
        String unparsed_amount = scanner.nextLine();
        int amount = parse_int(unparsed_amount, "is not a valid amount, it should be a number");
        if(amount == -1){
            return false;
        }
        Goal goal = new Goal(target, amount, description);
        return database.insertGoal(goal);
    }

    public Goal getGoal() {
        System.out.println("Enter Goal Name: ");
        String description = scanner.nextLine();
        if(description.isBlank()) {
            System.out.println("Empty input");
            return null;
        }
        System.out.println("Enter Target Amount: ");
        String unparsed_target = scanner.nextLine();
        int target = parse_int(unparsed_target, "is not a valid target, it should be a number");
        if(target == -1){
            return null;
        }
        System.out.println("Enter Saving Amount: ");
        String unparsed_amount = scanner.nextLine();
        int amount = parse_int(unparsed_amount, "is not a valid amount, it should be a number");
        if(amount == -1){
            return null;
        }
        Goal[] goals = database.retrieveGoals();
        for (Goal goal : goals) {
            if(goal.getDescription().equals(description) && goal.getTarget() == target && goal.getSaving_amount() == amount) {
                return goal;
            }
        }
        return null;
    }

    public Goal[] getGoals() {
        return database.retrieveGoals();
    }

    public boolean createReminder() {
        System.out.println("Insert Reminder Title: ");
        String title = scanner.nextLine();
        if(title.isBlank()) {
            System.out.println("Empty input");
            return false;
        }
        System.out.println("Insert Reminder date (YYYY-MM-DD): ");
        String reminder_date = scanner.nextLine();
        if(reminder_date.isBlank()) {
            System.out.println("Empty input");
            return false;
        }
        System.out.println("Insert Reminder Message: ");
        String message = scanner.nextLine();
        if(message.isBlank()) {
            System.out.println("Empty input");
            return false;
        }
        Reminder reminder = new Reminder(title, reminder_date, message);
        return database.insertReminder(reminder);
    }

    public Reminder getReminder() {
        System.out.println("Enter Reminder Title: ");
        String title = scanner.nextLine();
        if(title.isBlank()) {
            System.out.println("Empty input");
            return null;
        }
        System.out.println("Enter Reminder date (YYYY-MM-DD): ");
        String reminder_date = scanner.nextLine();
        if(reminder_date.isBlank()) {
            System.out.println("Empty input");
            return null;
        }
        Reminder[] reminders = database.retrieveReminders();
        for(Reminder reminder: reminders) {
            if(reminder.getTitle().equals(title) && reminder.getDate().equals(reminder_date)) {
                return reminder;
            }
        }
        return null;
    }

    public Reminder[] getReminders() {
        return database.retrieveReminders();
    }

}
