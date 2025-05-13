import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Scanner;

/**
 * Responsible for handling income, expenses, budgets, and financial analysis.
 * Provides interfaces to interact with user input and persists financial records.
 *
 * @author Fares
 */
public class FinancialManagement {
    /** Singleton instance of FinancialManagement */
    private static volatile FinancialManagement instance;

    /** Instance of Database for database operations */
    protected Database database;

    /** Instance of AnalyticsService for analytics operations */
    protected AnalyticsService analytics;

    /** Scanner for taking input from the user */
    protected Scanner scanner;

    /**
     * Initializes the database, analytics service, and scanner.
     */
    private FinancialManagement() {
        this.database = Database.getInstance();
        this.analytics = AnalyticsService.getInstance();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Retrieves the singleton instance of FinancialManagement.
     *
     * @return The singleton instance of FinancialManagement
     */
    public static FinancialManagement getInstance() {
        if (instance == null) {
            synchronized (FinancialManagement.class) {
                if (instance == null) {
                    instance = new FinancialManagement();
                }
            }
        }
        return instance;
    }

    /**
     * Parses a string to an integer and validates its correctness.
     *
     * @param input The string input to be parsed
     * @param error_message The error message to display on failure
     * @return The parsed integer or -1 on error
     */
    private int parse_int(String input, String error_message) {
        int num = -1;
        try {
            if(input.isEmpty()) {
                throw new NumberFormatException("Empty input");
            }
            num = Integer.parseInt(input);
            return num;
        } catch (NumberFormatException e) {
            System.err.println(input + " " + error_message);
            return -1;
        }
    }

    /**
     * Validates a date input in format (yyyy-MM-dd) or 'T' for today's date.
     *
     * @param input The date string to validate
     * @return true if the input is valid, false otherwise
     */
    private boolean valid_date(String input) {
        try {
            if(input.equals("T") || input.equals("t")) {
                return true;
            }
            DateTimeFormatter isoFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd")
                    .withResolverStyle(ResolverStyle.STRICT);
            LocalDate.parse(input, isoFormatter);
            return true;
        } catch (DateTimeParseException e) {
            System.err.println(input + " is not a valid date");
            System.err.println("Date have the following format (YYYY-MM-DD), single digit days or month must be preceded by 0");
            return false;
        }
    }

    /**
     * Prompts the user for income details (ie {@code Source} and {@code Amount}) and creates
     * a new instance in the database corresponding to the recorded details.
     *
     * @return {@code True} if income was added successfully, false otherwise
     */
    public boolean addIncome(){
        System.out.println("Insert Income Source: ");
        String source = scanner.nextLine();
        if(source.isBlank()) {
            System.out.println("Empty input");
            return false;
        }
        System.out.println("Insert Income Amount: ");
        String unparsed_amount = scanner.nextLine();
        int amount = parse_int(unparsed_amount, "is not a valid amount, it should be a number");
        if(amount == -1){
            return false;
        }
        Income income = new Income(source, amount);
        return database.insertIncome(income);
    }

    /**
     * Deletes an expense
     * @return true if the deletion succeeded otherwise false
     */
    public boolean deleteIncome() {
        System.out.println("Enter Income Source: ");
        String source = scanner.nextLine();
        if(source.isBlank()) {
            System.out.println("Empty input");
            return false;
        }
        System.out.println("Enter Income Amount: ");
        String unparsed_amount = scanner.nextLine();
        int amount = parse_int(unparsed_amount, "is not a valid amount, it should be a number");
        if(amount == -1){
            return false;
        }
        return database.deleteIncome(source, amount);
    }

    /**
     * Retrieves a specific income record based on user-provided source and amount.
     *
     * @return The matched income object, or null if not found
     */
    public Income getIncome(){
        System.out.println("Enter Income Source: ");
        String source = scanner.nextLine();
        if(source.isBlank()) {
            System.out.println("Empty input");
            return null;
        }
        System.out.println("Enter Income Amount: ");
        String unparsed_amount = scanner.nextLine();
        int amount = parse_int(unparsed_amount, "is not a valid amount, it should be a number");
        if(amount == -1){
            return null;
        }
        Income[] incomes = database.retrieveIncomes();
        for (int i = 0; i < incomes.length; i++) {
            if (incomes[i].getSource().equals(source) && incomes[i].getAmount() == amount) {
                return incomes[i];
            }
        }
        return null;
    }

    /**
     * Retrieves all recorded income entries from the database.
     *
     * @return An array of Income objects
     */
    public Income[] getIncomes(){
        return database.retrieveIncomes();
    }

    /**
     * Prompts the user for Expense details (ie. {@code Item Name} and {@code Amount}) and creates
     * a new instance in the database corresponding to the recorded details.
     *
     * @return True if expense was tracked successfully, false otherwise
     */
    public boolean trackExpense(){
        System.out.println("Insert Item Name: ");
        String item = scanner.nextLine();
        if(item.isBlank()) {
            System.out.println("Empty input");
            return false;
        }
        System.out.println("Insert Expense Amount: ");
        String unparsed_amount = scanner.nextLine();
        int amount = parse_int(unparsed_amount, "is not a valid amount, it should be a number");
        if(amount == -1){
            return false;
        }
        Expense expense = new Expense(item, amount);

        return database.insertExpense(expense);
    }
    /**
     * Deletes an expense
     * @return true if the deletion succeeded otherwise false
     */
    public boolean deleteExpense() {
        System.out.println("Enter Item Name: ");
        String item = scanner.nextLine();
        if(item.isBlank()) {
            System.out.println("Empty input");
            return false;
        }
        System.out.println("Enter Expense Amount: ");
        String unparsed_amount = scanner.nextLine();
        int amount = parse_int(unparsed_amount, "is not a valid amount, it should be a number");
        if(amount == -1){
            return false;
        }
        return database.deleteExpense(item, amount);
    }

    /**
     * Retrieves a specific expense record based on user input.
     *
     * @return The matched Expense object, or null if not found
     */
    public Expense getExpense(){
        System.out.println("Enter Item Name: ");
        String item = scanner.nextLine();
        if(item.isBlank()) {
            System.out.println("Empty input");
            return null;
        }
        System.out.println("Enter Expense Amount: ");
        String unparsed_amount = scanner.nextLine();
        int amount = parse_int(unparsed_amount, "is not a valid amount, it should be a number");
        if(amount == -1){
            return null;
        }
        Expense[] expenses = database.retrieveExpenses();
        for (Expense expense : expenses) {
            if (expense.getItem().equals(item) && expense.getAmount() == amount) {
                return expense;
            }
        }
        return null;
    }

    /**
     * Retrieves all recorded expense entries from the database.
     *
     * @return An array of Expense objects
     */
    public Expense[] getExpenses(){
        return database.retrieveExpenses();
    }

    /**
     * Prompts the user for Budget details (ie. {@code Category}, {@code Limit}, {@code Start Date}, and {@code End Date}) and creates
     * a new instance in the database corresponding to the inputed details.
     *
     * @return True if the budget was successfully created, false otherwise
     */
    public boolean createBudget(){
        System.out.println("Insert Budget Category: ");
        String category = scanner.nextLine();
        if(category.isBlank()) {
            System.out.println("Empty input");
            return false;
        }
        System.out.println("Insert Budget Limit: ");
        String unparsed_limit = scanner.nextLine();
        int limit = parse_int(unparsed_limit, "is not a valid limit, it should be a number");
        if(limit == -1){
            return false;
        }
        System.out.println("Insert Start Date 'YYYY-MM-DD' (If you want to the start date to be today type 'T': ");
        String start_date = scanner.nextLine();
        if(!valid_date(start_date)) {
            return false;
        }
        System.out.println("Insert End Date 'YYYY-MM-DD': ");
        String end_date = scanner.nextLine();
        if(!valid_date(end_date)) {
            return false;
        }
        Budget budget;
        if(start_date.equals("T")) {
            budget = new Budget(category, limit, end_date);
        } else {
            budget = new Budget(category, limit, start_date, end_date);
        }

        return database.insertBudget(budget);
    }

    /**
     * Retrieves a specific budget record based on user-provided category and amount.
     *
     * @return The matched Budget object, or null if not found
     */
    public Budget getBudget(){
        System.out.println("Enter Category Name: ");
        String category = scanner.nextLine();
        if(category.isBlank()) {
            System.out.println("Empty input");
            return null;
        }
        System.out.println("Enter Budget Limit: ");
        String unparsed_limit = scanner.nextLine();
        int limit = parse_int(unparsed_limit, "is not a valid limit, it should be a number");
        if(limit == -1){
            return null;
        }
        Budget[] budgets = database.retrieveBudgets();
        for (Budget budget : budgets) {
            if (budget.getCategory().equals(category) && budget.getAmount() == limit) {
                return budget;
            }
        }
        return null;
    }

    /**
     * Retrieves all recorded budget entries from the database.
     *
     * @return An array of Budget objects
     */
    public Budget[] getBudgets(){
        return database.retrieveBudgets();
    }

    /**
     * Add spending to a budget
     * @return true if the addition succeeded otherwise false
     */
    public boolean addToBudget() {
        System.out.println("Enter The Category Name: ");
        String category = scanner.nextLine();
        if(category.isBlank()) {
            System.out.println("Empty input");
            return false;
        }
        System.out.println("Enter The Add Amount: ");
        String unparsed_spent = scanner.nextLine();
        int spent = parse_int(unparsed_spent, "is not a valid amount, it should be a number");
        if(spent == -1){
            return false;
        }
        return database.addToBudget(category, spent);
    }

    /**
     * Analyzes financial data within a specified date range using the analytics service.
     */
    public void analyzeFinancials(){
        while(true){
            System.out.println("What type of financials do you want to analyze? ");
            System.out.println("1. Spending");
            System.out.println("2. Generate Report");
            int choice = getUserChoice(1, 2);

            System.out.println("Insert Start Date 'YYYY-MM-DD' (If you want to the start date to be today type 'T': ");
            String startDate = scanner.nextLine();
            if(!valid_date(startDate)) {
                continue;
            }
            System.out.println("Insert End Date 'YYYY-MM-DD' : ");
            String endDate = scanner.nextLine();
            if(!valid_date(endDate)) {
                continue;
            }

            switch (choice){
                case 1:
                    System.out.println("Analyzing Spending");
                    analytics.analyzeSpending(startDate, endDate);
                    return;
                case 2:
                    System.out.println("Generating Report");
                    analytics.generateReport(startDate, endDate);
                    return;
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
}
