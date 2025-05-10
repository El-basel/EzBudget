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
//    public Income getIncome(int userID){
//        return database.retrieveIncome(userID);
//    }
//
//    public Income[] getIncomes(){
//        return database.retrieveIncomes();
//    }

    public void trackExpense(String item, int amount){
        Expense expense = new Expense();
        expense.setItem(item);
        expense.setAmount(amount);

        //database.insertExpense(expense);
    }

    //retrieve the expense !
//    public Expense getExpense(int expenseID){
//        return database.retrieveExpense(expenseID);
//    }
//
//    public Expense[] getExpenses(){
//        return database.retrieveExpenses();
//    }

    //add setCategory and setPeriod to budget
    public void createBudget(String category, int limit, int startDate, int endDate){
        Budget budget = new Budget();
        //budget.setCategory(category);
        budget.setAmount(limit);
        //budget.setStart(startDate);
        //budget.setEnd(endDate);
        //database.insertBudget(budget);
    }

    //retrieve the budgets !
//    public Budget getBudget(int budgetID){
//        return database.retrieveBudget(budgetID);
//    }
//
//    public Budget[] getBudgets(){
//        return database.retrieveBudgets();
//    }

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
