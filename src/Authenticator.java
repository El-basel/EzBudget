public class Authenticator {

    private Database database;
    public Authenticator(Database database) {
        this.database = database;
    }
    public boolean login(String username, String password) {
        User user = database.getUser(username, password);
        if (user != null) return false;
        return true;
    }
    public boolean register(User user) {
        database.insertUser(user);
    }
    public boolean VerifyCredentials(String username, String password, String Email) {
        // Check for null or empty inputs
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
