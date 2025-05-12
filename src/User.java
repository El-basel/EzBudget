/**
 * Represents a user of the personal budgeting application.
 * A user has credentials and access to financial management functionalities.
 * @author Youssef
 */
public class User {
    private String username;
    private String password;
    private String email;
    private FinancialManagement financialmanagement;
    private Planning planning;
    private Reminder reminder;

    /**
     * Constructs a new User with the given username, password, and email.
     * Initializes the FinancialManagement singleton instance.
     *
     * @param username the user's username
     * @param password the user's password
     * @param email the user's email
     */
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.financialmanagement = FinancialManagement.getInstance(); // Initialize singleton
    }

    /**
     * Returns the user's username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the user's password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the user's email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Adds an income entry using the FinancialManagement instance.
     *
     * @return true if the income was added successfully, false otherwise
     */
    public boolean addIncome() {
        return financialmanagement.addIncome();
    }

    /**
     * Tracks an expense using the FinancialManagement instance.
     *
     * @return true if the expense was tracked successfully, false otherwise
     */
    public boolean trackExpense() {
        return financialmanagement.trackExpense();
    }

    /**
     * Creates a budget using the FinancialManagement instance.
     *
     * @return true if the budget was created successfully, false otherwise
     */
    public boolean createBudget() {
        return financialmanagement.createBudget();
    }

    /**
     * Retrieves all incomes as an array of strings.
     *
     * @return an array of income descriptions, or an empty array if none
     */
    public String[] Incomes() {
        Income[] income = financialmanagement.getIncomes();
        if (income == null || income.length == 0) return new String[0];
        String[] incomeStrings = new String[income.length];
        for (int i = 0; i < income.length; i++) {
            incomeStrings[i] = income[i].toString();
        }
        return incomeStrings;
    }

    /**
     * Retrieves all budgets as an array of strings.
     *
     * @return an array of budget descriptions, or an empty array if none
     */
    public String[] budgets() {
        Budget[] budget = financialmanagement.getBudgets();
        if (budget == null || budget.length == 0) return new String[0];
        String[] budgetStrings = new String[budget.length];
        for (int i = 0; i < budget.length; i++) {
            budgetStrings[i] = budget[i].toString();
        }
        return budgetStrings;
    }

    /**
     * Retrieves all expenses as an array of strings.
     *
     * @return an array of expense descriptions, or an empty array if none
     */
    public String[] expense() {
        Expense[] expense = financialmanagement.getExpenses();
        if (expense == null || expense.length == 0) return new String[0];
        String[] expenseStrings = new String[expense.length];
        for (int i = 0; i < expense.length; i++) {
            expenseStrings[i] = expense[i].toString();
        }
        return expenseStrings;
    }
}
