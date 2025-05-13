import org.w3c.dom.ls.LSOutput;

import java.sql.SQLOutput;

public class AnalyticsService {
    private static AnalyticsService instance;
    private Database database;

    private AnalyticsService() {
        this.database = Database.getInstance();
    }

    public static AnalyticsService getInstance() {
        if (instance == null) {
            instance = new AnalyticsService();
        }
        return instance;
    }

    private void printExpenses(Expense[] expenses) {
        if (expenses == null || expenses.length == 0) {
            System.out.println("+ No expenses found.");
            return;
        }
        System.out.println("+ Total Expenses: " + expenses.length);
        System.out.println("=======================================");
        for (Expense expense : expenses) {
            System.out.println("> Item   : " + expense.getItem());
            System.out.println("> Amount : $" + expense.getAmount());
            System.out.println();
        }
    }

    private void printIncomes(Income[] incomes) {
        if (incomes == null || incomes.length == 0) {
            System.out.println("+ No incomes found.");
            return;
        }
        System.out.println("+ Total Incomes: " + incomes.length);
        System.out.println("=======================================");
        for (Income income : incomes) {
            System.out.println("> Source : " + income.getSource());
            System.out.println("> Amount : $" + income.getAmount());
            System.out.println();
        }

    }

    private void printBugets(Budget[] budgets) {
        if (budgets == null || budgets.length == 0) {
            System.out.println("+ No budgets found.");
            return;
        }
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

    private void printGoals(Goal[] goals) {
        if (goals == null || goals.length == 0) {
            System.out.println("+ No goals found.");
            return;
        }
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
        Expense[] expenses = database.retrieveExpenses();

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
        Income[] incomes = database.retrieveIncomes();
        Budget[] budgets = database.retrieveBudgets();
        Goal[] goals = database.retrieveGoals();

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
