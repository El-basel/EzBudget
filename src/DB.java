import java.util.ArrayList;
import java.util.List;
public class DB {
    private List<User> users;

    public DB() {
        this.users = new ArrayList<>();
        // Add some initial test users
        users.add(new User("testuser", "password123", "test@example.com"));
        users.add(new User("admin", "admin12345", "admin@example.com"));
    }

    public User getUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public boolean insertUser(User user) {
        // Check if username or email already exists
        for (User existingUser : users) {
            if (existingUser.getUsername().equals(user.getUsername()) ||
                    existingUser.getEmail().equals(user.getEmail())) {
                return false; // User already exists
            }
        }

        // Add new user
        users.add(user);
        return true;
    }

    // Helper method to print all users (for testing)
    public void printAllUsers() {
        System.out.println("Current users in database:");
        for (User user : users) {
            System.out.println("Username: " + user.getUsername() +
                    ", Email: " + user.getEmail());
        }
    }
}
