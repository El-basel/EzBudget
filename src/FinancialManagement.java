import java.util.Scanner;

public class FinancialManagement {
    private static volatile FinancialManagement instance;
    protected Database database;
    protected AnalyticsService analytics;
    protected Scanner scanner;
    private FinancialManagement() {
        this.database = Database.getInstance();
        this.analytics = AnalyticsService.getInstance();
        this.scanner = new Scanner(System.in);
    }

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

    public Income[] getIncomes(){
        return database.retrieveIncomes();
    }

    public boolean trackExpense(){
        System.out.println("Insert Item Name: ");
        String item = scanner.nextLine();
        if(item.isBlank()) {
            System.out.println("Empty input");
            return false;
        }
        System.out.println("Insert Income Amount: ");
        String unparsed_amount = scanner.nextLine();
        int amount = parse_int(unparsed_amount, "is not a valid amount, it should be a number");
        if(amount == -1){
            return false;
        }
        Expense expense = new Expense(item, amount);

        return database.insertExpense(expense);
    }

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

    public Expense[] getExpenses(){
        return database.retrieveExpenses();
    }

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
        System.out.println("Insert Start Date (If you want to the start date to be today type 'T': ");
        String start_date = scanner.nextLine();
        System.out.println("Insert End Date: ");
        String end_date = scanner.nextLine();
        Budget budget;
        if(start_date.equals("T")) {
            budget = new Budget(category, limit, end_date);
        } else {
            budget = new Budget(category, limit, start_date, end_date);
        }

        return database.insertBudget(budget);
    }


    public Budget getBudget(String source, int limit, String category){

        return database.retrieveBudget(category, limit);
    }

    public Budget[] getBudgets(){
        return database.retrieveBudgets();
    }

    //finish analyzeFinancials!
    public void analyzeFinancials(String type, int startDate, int endDate){
        switch (type){
            case "Spending":
                System.out.println("Analyzing Spending");
                analytics.analyzeSpending(startDate, endDate);
                break;
            case "Report":
                System.out.println("Generating Report");
                analytics.generateReport(startDate, endDate);
                break;
        }
    }
}
