public class Authenticator {

    private Database database;
    public Authenticator(Database database) {
        this.database = Database.getInstance();
    }
    public boolean login(String email, String password) {
        User user = database.getUser(email, password);
        return user != null;
    }
    public boolean register(User user) {
        if (database.insertUser(user)) return true;
        return false;
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
