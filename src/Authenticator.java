public class Authenticator {
    private static Authenticator authenticator = null;
    private Database database;
    private Authenticator() {
        this.database = Database.getInstance();
    }
    public static Authenticator getInstance() {
        if(authenticator == null) {
            authenticator = new Authenticator();
        }
        return authenticator;
    }
    public User login(String email, String password) {
        return database.getUser(email, password);
    }
    public boolean register(User user) {
        return database.insertUser(user);
    }
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
