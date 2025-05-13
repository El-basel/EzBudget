import java.util.Scanner;

/**
 * Manages user interaction for setting and retrieving financial goals and reminders.
 *
 * @author Fares
 */
public class Planning {
    /** Singleton instance of the Planning class. */
    private static Planning instance;

    /** Instance of {@code NotificationService} to handle user notifications. */
    private NotificationService notificationService;

    /** instance of {@code Database} to handle storage and retrieval of data. */
    private Database database;

    /** Scanner for receiving console input from the user. */
    private Scanner scanner;

    /**
     * Private constructor that enforces singleton pattern.
     * Initializes database, notification service, and scanner.
     */
    private Planning(){
        this.database = Database.getInstance();
        this.notificationService = NotificationService.getInstance();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Safely parses a string input into an integer.
     * Prints an error message if parsing fails or if the input is empty.
     *
     * @param input The string input to parse
     * @param error_message Message to display if parsing fails
     * @return Parsed integer or -1 if parsing fails
     */
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

    /**
     * Returns the singleton instance of the Planning class.
     *
     * @return The single instance of Planning
     */
    public static Planning getInstance(){
        if (instance == null){
            synchronized (Planning.class) {
                if (instance == null){
                    instance = new Planning();
                }
            }
        }
        return instance;
    }

    /**
     * Prompts the user to create a new goal with input validation.
     *
     * @return true if the goal was successfully created and stored, otherwise false
     */
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

    /**
     * Retrieves a goal from the database based on user-provided input.
     *
     * @return The matched goal object or null if no match is found
     */
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

    /**
     * Retrieves all stored financial goals.
     *
     * @return An array of all goal objects in the database
     */
    public Goal[] getGoals() {
        return database.retrieveGoals();
    }

    /**
     * Prompts the user to create a new reminder with validation.
     *
     * @return true if the reminder was successfully created and stored, otherwise false
     */
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

    /**
     * Retrieves a reminder based on user input (ie. title and date).
     *
     * @return The matched reminder object or null if no match is found
     */
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

    /**
     * Retrieves all stored reminders.
     *
     * @return An array of all reminder objects in the database
     */
    public Reminder[] getReminders() {
        return database.retrieveReminders();
    }

    /**
     * Sends notifications for all reminders scheduled for today.
     *
     * @return The number of notifications sent
     */
    public int sendTodayReminders() {
        return notificationService.sendTodayReminders();
    }

    /**
     * Configure the email notification service settings.
     *
     * @param senderEmail The email address to send notifications from
     * @param password The password or app password for the sender email
     * @param smtpHost The SMTP host server
     * @param smtpPort The SMTP port number
     */
    public void configureNotifications(String senderEmail, String password, String smtpHost, int smtpPort) {
        notificationService.configureEmailSender(senderEmail, password, smtpHost, smtpPort);
    }

}
