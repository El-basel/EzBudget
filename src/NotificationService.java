import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.mail.Authenticator;


/**
 * Service class that handles sending notifications to users.
 * Uses Jakarta Mail API to send email notifications based on reminders.
 *
 * @author Fares
 */
public class NotificationService {
    /** Singleton instance of the NotificationService class. */
    private static volatile NotificationService instance;

    /** Database instance for data retrieval. */
    private Database database;

    /** Email sender configuration. */
    private String senderEmail = "notifications@ezbudget.com";
    private String senderPassword = "your_app_password"; // For production, use secure methods to store credentials
    private String smtpHost = "smtp.gmail.com";
    private int smtpPort = 587;

    /**
     * Private constructor that enforces singleton pattern.
     * Initializes the database connection.
     */
    private NotificationService() {
        this.database = Database.getInstance();
    }

    /**
     * Returns the singleton instance of the NotificationService class.
     *
     * @return The single instance of NotificationService
     */
    public static NotificationService getInstance() {
        if (instance == null) {
            synchronized (NotificationService.class) {
                if (instance == null) {
                    instance = new NotificationService();
                }
            }
        }
        return instance;
    }

    /**
     * Sends reminder for all reminders scheduled for today.
     *
     * @return The number of reminders successfully sent
     */
    public int sendTodayReminders() {
        Reminder[] todayReminders = database.retrieveTodayReminders();
        if (todayReminders == null || todayReminders.length == 0) {
            return 0;
        }

        int sentCount = 0;
        String userEmail = getUserEmail();
        if (userEmail == null || userEmail.isEmpty()) {
            System.err.println("User email not found or invalid.");
            return 0;
        }

        for (Reminder reminder : todayReminders) {
            if (sendEmail(userEmail, reminder)) {
                sentCount++;
            }
        }

        if (!database.deleteTodayReminders()) {
            System.out.println("Failed to delete today's reminders.");
        }

        return sentCount;
    }

    /**
     * Helper method to get the email address of the currently logged-in user.
     *
     * @return The user's email address or null if not found
     */
    private String getUserEmail() {
        User currentUser = database.getCurrentUser();
        return currentUser != null ? currentUser.getEmail() : null;
    }

    /**
     * Helper method to retrieve a reminder by its ID from the database.
     *
     * @param reminderID The ID of the reminder to retrieve
     * @return The Reminder object or null if not found
     */
    private Reminder getReminderById(int reminderID) {
        return database.getReminderById(reminderID);
    }

    /**
     * Sends an email notification based on the reminder details.
     *
     * @param recipientEmail The email address of the recipient
     * @param reminder The reminder object containing notification details
     * @return true if the email was sent successfully, false otherwise
     */
    private boolean sendEmail(String recipientEmail, Reminder reminder) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", smtpPort);

        try {
            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(senderEmail, senderPassword);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Notification from EzBudget");

            String content = "Reminder: " + reminder.getTitle() + "\n\n" +
                    "Date: " + reminder.getDate() + "\n\n" +
                    "Message: " + reminder.getMessage();

            message.setText(content);

            Transport.send(message);
            System.out.println();
            System.out.println("Notification sent successfully to " + recipientEmail);
            return true;

        } catch (MessagingException e) {
            System.err.println("Failed to send email: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Configure the email sender settings.
     *
     * @param email The sender's email address
     * @param password The sender's email password or app password
     * @param host The SMTP host server
     * @param port The SMTP port
     */
    public void configureEmailSender(String email, String password, String host, int port) {
        this.senderEmail = email;
        this.senderPassword = password;
        this.smtpHost = host;
        this.smtpPort = port;
    }
}