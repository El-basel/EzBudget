public class FinancialManagement {
    private static volatile FinancialManagement instance;
    protected Database database;
    protected AnalyticsService analytics;

    private FinancialManagement(Database database) {
        this.database = database;
        this.analytics = AnalyticsService.getInstance(database);
    }

    public static FinancialManagement getInstance(Database database) {
        if (instance == null) {
            synchronized (FinancialManagement.class) {
                if (instance == null) {
                    instance = new FinancialManagement(database);
                }
            }
        }
        return instance;
    }

    public void addIncome(String source, int amount){
        Income income = new Income();
        income.setSource(source);
        income.setAmount(amount);

        database.insertIncome(income);
    }

    public Income getIncome(String source, int amount){
        return database.retrieveIncome(source, amount);
    }

    public Income[] getIncomes(){
        return database.retrieveIncomes();
    }

    public void trackExpense(String item, int amount){
        Expense expense = new Expense();
        expense.setItem(item);
        expense.setAmount(amount);

        database.insertExpense(expense);
    }

    public Expense getExpense(String item, int amount){
        return database.retrieveExpense(item, amount);
    }

    public Expense[] getExpenses(){
        return database.retrieveExpenses();
    }

    public void createBudget(String category, int limit, String startDate, String endDate){
        Budget budget = new Budget();
        budget.setCategory(category);
        budget.setAmount(limit);
        budget.setPeriod(startDate, endDate);
        database.insertBudget(budget);
    }

    public void createBudget(String category, int limit, String endDate){
        Budget budget = new Budget();
        budget.setCategory(category);
        budget.setAmount(limit);
        budget.setPeriod(endDate);
        database.insertBudget(budget);
    }

    public Budget getBudget(String source, int limit, String category){
        return database.retrieveBudget(source, limit, category);
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
