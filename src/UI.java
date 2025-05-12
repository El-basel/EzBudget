import java.util.Scanner;

/**
 * Represents the user interface for interacting with the personal budgeting system.
 * It handles login, registration, and the display of different menus for managing finances.
 * @author Youssef
 */
public class UI {
    private Scanner scanner;
    private Authenticator authenticator;
    private boolean loggedIn = false;
    private User currentUser = null;
    /**
     * Constructs a new UI instance and initializes the Authenticator and Scanner objects.
     */
    public UI() {
        this.authenticator = Authenticator.getInstance();
        this.scanner = new Scanner(System.in);
    }
    /**
     * Starts the user interface by displaying the main menu and handling login or registration.
     * Once logged in, it displays the logged-in menu for further actions.
     */
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
    /**
     * Displays the main menu with options to login, register, or exit.
     */
    private void displayMainMenu() {
        System.out.println("\n===== Main Menu =====");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
    }
    /**
     * Displays the logged-in menu with options for managing finances and logging out.
     * Allows the user to add income, track expenses, create a budget, and view financial records.
     */
    private void displayLoggedInMenu() {
        while (loggedIn) {
            System.out.println("\n===== Welcome, " + currentUser.getUsername() + "! =====");
            System.out.println("1. Add Income");
            System.out.println("2. Track Expense");
            System.out.println("3. Create Budget");
            System.out.println("4. View Incomes");
            System.out.println("5. View Expenses");
            System.out.println("6. View Budgets");
            System.out.println("7. Logout");
            System.out.print("Enter your choice: ");
            int choice = getUserChoice(1, 7);
            switch (choice) {
                case 1:
                    if (currentUser.addIncome())
                        System.out.println("Income added successfully.");
                    else
                        System.out.println("Failed to add income.");
                    break;
                case 2:
                    if (currentUser.trackExpense())
                        System.out.println("Expense tracked successfully.");
                    else
                        System.out.println("Failed to track expense.");
                    break;
                case 3:
                    if (currentUser.createBudget())
                        System.out.println("Budget created successfully.");
                    else
                        System.out.println("Failed to create budget.");
                    break;
                case 4:
                    String[] incomes = currentUser.Incomes();
                    if (incomes.length == 0) {
                        System.out.println("No income records found.");
                    } else {
                        System.out.println("Incomes:");
                        for (String income : incomes) {
                            System.out.println("- " + income);
                        }
                    }
                    break;
                case 5:
                    String[] expenses = currentUser.expense();
                    if (expenses.length == 0) {
                        System.out.println("No expense records found.");
                    } else {
                        System.out.println("Expenses:");
                        for (String expense : expenses) {
                            System.out.println("- " + expense);
                        }
                    }
                    break;
                case 6:
                    String[] budgets = currentUser.budgets();
                    if (budgets.length == 0) {
                        System.out.println("No budget records found.");
                    } else {
                        System.out.println("Budgets:");
                        for (String budget : budgets) {
                            System.out.println("- " + budget);
                        }
                    }
                    break;
                case 7:
                    System.out.println("Logging out...");
                    loggedIn = false;
                    currentUser = null;
                    break;
            }
        }
    }
    /**
     * Prompts the user to enter a valid number between the given range.
     * Ensures the input is an integer and within the specified bounds.
     *
     * @param min the minimum valid number
     * @param max the maximum valid number
     * @return the user's choice as an integer
     */
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
    /**
     * Handles the login process by prompting the user for their email and password.
     * Attempts to authenticate the user and sets the currentUser if login is successful.
     */
    private void handleLogin() {
        System.out.println("\n===== Login =====");
        System.out.print("Enter Email: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        currentUser = authenticator.login(username, password);
        if (currentUser != null) {
            System.out.println("Login successful!");
            loggedIn = true;
        } else {
            System.out.println("Login failed! Invalid Email or Password.");
        }
    }
    /**
     * Handles the registration process by prompting the user for their credentials.
     * Verifies the credentials and registers a new user if they are valid.
     */
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
            currentUser = newUser;
            System.out.println("Registration successful! You can now login.");
        } else {
            System.out.println("Registration failed! Username or email already exists.");
        }
    }
}
