public class User {
    private String username;
    private String password;
    private String email;
    private FinancialManagement financialmanagement;
    private Planning planning;
    private Reminder reminder;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.financialmanagement = FinancialManagement.getInstance(); // Initialize singleton
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public boolean addIncome() {
        return financialmanagement.addIncome();
    }

    public boolean trackExpense() {
        return financialmanagement.trackExpense();
    }

    public boolean createBudget() {
        return financialmanagement.createBudget();
    }

    public String[] Incomes() {
        Income[] income = financialmanagement.getIncomes();
        if (income == null || income.length == 0) return new String[0];
        String[] incomeStrings = new String[income.length];
        for (int i = 0; i < income.length; i++) {
            incomeStrings[i] = income[i].toString();
        }
        return incomeStrings;
    }

    public String[] budgets() {
        Budget[] budget = financialmanagement.getBudgets();
        if (budget == null || budget.length == 0) return new String[0];
        String[] budgetStrings = new String[budget.length];
        for (int i = 0; i < budget.length; i++) {
            budgetStrings[i] = budget[i].toString();
        }
        return budgetStrings;
    }

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
