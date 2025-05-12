/**
 * The Authenticator class is responsible for handling user authentication,
 * including login, registration, and credential validation.
 * @author Youssef
 */
public class Authenticator {
    private static Authenticator authenticator = null;
    private Database database;

    /**
     * Private constructor to initialize the database instance.
     */
    private Authenticator() {
        this.database = Database.getInstance();
    }

    /**
     * Gets the singleton instance of the Authenticator class.
     *
     * @return the singleton Authenticator instance.
     */
    public static Authenticator getInstance() {
        if (authenticator == null) {
            authenticator = new Authenticator();
        }
        return authenticator;
    }

    /**
     * Authenticates a user based on the provided email and password.
     *
     * @param email    the user's email address.
     * @param password the user's password.
     * @return the User object if login is successful, null otherwise.
     */
    public User login(String email, String password) {
        return database.getUser(email, password);
    }

    /**
     * Registers a new user in the database.
     *
     * @param user the User object to be registered.
     * @return true if registration is successful, false otherwise.
     */
    public boolean register(User user) {
        return database.insertUser(user);
    }

    /**
     * Validates the user's credentials during registration.
     *
     * @param username the username to be validated.
     * @param password the password to be validated.
     * @param Email    the email to be validated.
     * @return true if the credentials are valid, false otherwise.
     */
    public boolean VerifyCredentials(String username, String password, String Email) {
        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                Email == null || Email.trim().isEmpty()) {
            return false;
        }
        String emailRegex = "^[\\w.-]+@[\\w.-]+\\.\\w{2,}$";
        if (!Email.matches(emailRegex)) {
            return false;
        }
        if (password.length() < 8) {
            return false;
        }
        if (username.length() < 3) {
            return false;
        }
        return true;
    }
}
