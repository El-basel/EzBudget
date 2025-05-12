/**
 * Represents a reminder with a title, date, and message.
 * Used for storing and managing scheduled notifications or alerts.
 *
 * @author Fares
 */
public class Reminder{
    /** The title or brief heading of the reminder. */
    private String title;

    /** The date associated with the reminder (format assumed as String). */
    private String date;

    /** The detailed message or note of the reminder. */
    private String message;

    /**
     * Default constructor that constructs a new Reminder with default values.
     */
    public Reminder(){}

    /**
     * Constructs a new Reminder with specified title, date, and message.
     *
     * @param title The heading or name of the reminder
     * @param date The scheduled date for the reminder
     * @param message The full message or description of the reminder
     */
    public Reminder(String title, String date, String message){
        this.title = title;
        this.date = date;
        this.message = message;
    }

    /**
     * Retrieves the title of the reminder.
     *
     * @return The reminder's title
     */
    public String getTitle(){return title;}

    /**
     * Retrieves the date of the reminder.
     *
     * @return The reminder's date
     */
    public String getDate(){return date;}

    /**
     * Retrieves the message or description of the reminder.
     *
     * @return The reminder's message
     */
    public String getMessage(){return message;}

    /**
     * Updates the title of the reminder.
     *
     * @param title The new title to be set
     */
    public void setTitle(String title){this.title = title;}

    /**
     * Updates the date of the reminder.
     *
     * @param date The new date to be set
     */
    public void setDate(String date){this.date = date;}

    /**
     * Updates the message of the reminder.
     *
     * @param message The new message to be set
     */
    public void setMessage(String message){this.message = message;}
}