import org.w3c.dom.ls.LSOutput;

import java.sql.SQLOutput;

public class AnalyticsService {
    private static volatile AnalyticsService instance;
    private Database database;
    private Planning planning;
    private FinancialManagement financialManagement;

    private AnalyticsService() {
        this.database = Database.getInstance();
        this.financialManagement = FinancialManagement.getInstance();
        this.planning = Planning.getInstance();
    }

    public static AnalyticsService getInstance() {
        if (instance == null) {
            synchronized (AnalyticsService.class) {
                if (instance == null) {
                    instance = new AnalyticsService();
                }
            }
        }
        return instance;
    }

    public void printExpenses(Expense[] expenses) {
        System.out.println("+ Total Expenses: " + expenses.length);
        System.out.println("=======================================");
        for (Expense expense : expenses) {
            System.out.println("> Item   : " + expense.getItem());
            System.out.println("> Amount : $" + expense.getAmount());
            System.out.println();
        }
    }

    public void printIncomes(Income[] incomes) {
        System.out.println("+ Total Incomes: " + incomes.length);
        System.out.println("=======================================");
        for (Income income : incomes) {
            System.out.println("> Source : " + income.getSource());
            System.out.println("> Amount : $" + income.getAmount());
            System.out.println();
        }

    }

    public void printBugets(Budget[] budgets) {
        System.out.println("+ Total Budgets: " + budgets.length);
        System.out.println("=======================================");
        for (Budget budget : budgets) {
            System.out.println("> Category    : " + budget.getCategory());
            System.out.println("> Limit       : $" + budget.getLimit());
            System.out.println("> Strart date : " + budget.getStart_date());
            System.out.println("> End date    : " + budget.getEnd_date());
            System.out.println();
        }
    }

    public void printGoals(Goal[] goals) {
        System.out.println("+ Total Goals : " + goals.length);
        System.out.println("=======================================");
        for (Goal goal : goals) {
            System.out.println("> Goal Name        : " + goal.getDescription());
            System.out.println("> Target Amount    : $" + goal.getTarget());
            System.out.println("> Saved Each Month : $" + goal.getSaving_amount());
            System.out.println();
        }
    }

    public void analyzeSpending(String start, String end) {
        int totalExpenses = database.expenseSum(start, end);
        Expense[] expenses = financialManagement.getExpenses();

        System.out.println("|=====================================|");
        System.out.println("|          Spending Analysis          |");
        System.out.println("|=====================================|");
        System.out.println("Expenses:");
        System.out.println();
        printExpenses(expenses);
        System.out.println("---------------");
        System.out.println("+ Expenses: $" + totalExpenses);
    }

    public void generateReport(String start, String end){
        Expense[] expenses = database.retrieveExpenseFromPeriod(start, end);
        Income[] incomes = financialManagement.getIncomes();
        Budget[] budgets = financialManagement.getBudgets();
        Goal[] goals = planning.getGoals();

        int totalExpenses = database.expenseSum(start, end);
        int totalIncome = database.incomeSum(start, end);

        System.out.println("|======================================|");
        System.out.println("|            General Report            |");
        System.out.println("|======================================|");

        System.out.println("Incomes:");
        System.out.println();
        printIncomes(incomes);
        System.out.println("---------------");
        System.out.println("+ Total Income: $" + totalIncome);

        System.out.println("=======================================");

        System.out.println("Expenses:");
        System.out.println();
        printExpenses(expenses);
        System.out.println("---------------");
        System.out.println("+ Expenses: $" + totalExpenses);

        System.out.println("=========================================");

        System.out.println("Budgets:");
        System.out.println();
        printBugets(budgets);

        System.out.println("=========================================");

        System.out.println("Goals:");
        System.out.println();
        printGoals(goals);

        System.out.println("=========================================");
    }
}
