import java.util.Scanner;

public class UI {
    private Scanner scanner;
    private Authenticator authenticator;
    private boolean loggedIn = false;
    private User currentUser = null;

    public UI(Authenticator authenticator) {
        this.authenticator = authenticator;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Welcome");
        while (!loggedIn) {
            displayMainMenu();
            int choice = getUserChoice(1, 3);
            switch (choice) {
                case 1:
                    handleLogin();
                    break;
                case 2:
                    handleRegistration();
                    break;
                case 3:
                    System.out.println("Exiting the system. Goodbye!");
                    return;
            }
        }
        displayLoggedInMenu();
    }

    private void displayMainMenu() {
        System.out.println("\n===== Main Menu =====");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
    }

    private void displayLoggedInMenu() {
        System.out.println("\n===== Welcome, " + currentUser.getUsername() + "! =====");
        System.out.println("You are now logged in.");
        // Add more menu options for logged-in users as needed
        System.out.println("Logging out and exiting...");
    }
    private int getUserChoice(int min, int max) {
        int choice = -1;
        while (choice < min || choice > max) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice < min || choice > max) {
                    System.out.print("Please enter a number between " + min + " and " + max + ": ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
        return choice;
    }

    private void handleLogin() {
        System.out.println("\n===== Login =====");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (authenticator.login(username, password)) {
            System.out.println("Login successful!");
            loggedIn = true;
            currentUser = new User(username, password, "");
        } else {
            System.out.println("Login failed! Invalid username or password.");
        }
    }

    private void handleRegistration() {
        System.out.println("\n===== Registration =====");

        String username, password, email;
        boolean validCredentials = false;

        do {
            System.out.print("Enter username (minimum 3 characters): ");
            username = scanner.nextLine();

            System.out.print("Enter password (minimum 8 characters): ");
            password = scanner.nextLine();

            System.out.print("Enter email: ");
            email = scanner.nextLine();

            validCredentials = authenticator.VerifyCredentials(username, password, email);

            if (!validCredentials) {
                System.out.println("Invalid credentials! Please ensure:");
                System.out.println("- Username is at least 3 characters");
                System.out.println("- Password is at least 8 characters");
                System.out.println("- Email is in a valid format");
            }

        } while (!validCredentials);

        User newUser = new User(username, password, email);

        if (authenticator.register(newUser)) {
            System.out.println("Registration successful! You can now login.");
        } else {
            System.out.println("Registration failed! Username or email already exists.");
        }
    }
}
