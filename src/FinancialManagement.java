public class FinancialManagement {
    protected Database database;
    protected AnalyticsService analytics;

    public FinancialManagement(Database database) {
        this.database = database;
        this.analytics = new AnalyticsService(database);
    }

    public void addIncome(String source, int amount){
        Income income = new Income();
        income.setSource(source);
        income.setAmount(amount);

        database.insertIncome(income);
    }

    //use retrieve funcs from database
    public Income getIncome(int incomeID){
        return database.retrieveIncome(incomeID);
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

    //retrieve the expense !
    public Expense getExpense(int expenseID){
        return database.retrieveExpense(expenseID);
    }

    public Expense[] getExpenses(){
        return database.retrieveExpenses();
    }

    //add setCategory and setPeriod to budget
    public void createBudget(String category, int limit, int period){
        Budget budget = new Budget();
        //budget.setCategory(category);
        budget.setAmount(limit);
        //budget.setPeriod(period);
        database.insertBudget(budget);
    }

    //retrieve the budgets !
    public Budget getBudget(int budgetID){
        return database.retrieveBudget(budgetID);
    }

    public Budget[] getBudgets(){
        return database.retrieveBudgets();
    }

    public void alanyzeFinancials(String type,int period){
        switch (type){
            case "Spending":
                System.out.println("Analyzing Spending");
                analytics.analyzeSpending(period);
                break;
            case "Report":
                System.out.println("Generating Report");
                analytics.generateReport(period);
                break;
        }
    }
}
