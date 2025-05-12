/**
 * An abstract base class representing a financial entity with a description and an amount.
 * Provides common functionality for different types of financial transactions.
 * @author Mahmoud
 */
public abstract class FinancialType {
    /** The description of the financial type (e.g., item, source, category). */
    protected String description;
    /** The monetary amount associated with this financial type. */
    protected int amount;
    /**
     * Constructs a FinancialType with a specific description and amount.
     *
     * @param description A descriptive name for the financial type
     * @param amount The monetary value associated with this type
     */
    public FinancialType(String description, int amount) {
        this.description = description;
        this.amount = amount;
    }
    /**
     * Default constructor for creating an empty FinancialType.
     */
    public FinancialType() {}
    /**
     * Retrieves the description of the financial type.
     * @return The current description
     */
    protected String getDescription() {return description;}
    /**
     * Sets a new description for the financial type.
     * @param description The new description to be set
     */
    protected void setDescription(String description) {this.description = description;}
    /**
     * Retrieves the monetary amount.
     *
     * @return The current amount
     */
    public int getAmount() {return amount;}
    /**
     * Sets a new monetary amount.
     *
     * @param amount The new amount to be set
     */
    public void setAmount(int amount) {this.amount = amount;}
    /**
     * Modifies the current amount by adding a specified change.
     *
     * @param change The amount to add (can be positive or negative)
     */
    public void changeAmount(int change) {this.amount += change;}
}

/**
 * Represents an expense with a description and amount.
 * Extends the FinancialType class with expense-specific methods.
 */
class Expense extends FinancialType {
    /**
     * Constructs an Expense with a specific item and amount.
     * @param item The name or description of the expense
     * @param amount The monetary value of the expense
     */
    public Expense(String item, int amount) {super(item, amount);}
    /**
     * Default constructor for creating an empty Expense.
     */
    public Expense() {}
    /**
     * Retrieves the item description of the expense.
     * @return The current expense item
     */
    public String getItem() {return getDescription();}
    /**
     * Sets a new item description for the expense.
     * @param item The new expense item to be set
     */
    public void setItem(String item) {setDescription(item);}
}

/**
 * Represents a budget with a category, amount, and time period.
 * Extends the FinancialType class with budget-specific methods.
 */
class Budget extends FinancialType {
    /** The start date of the budget period. */
    private String start_date = null;
    /** The end date of the budget period. */
    private String end_date = null;
    /**
     * Constructs a Budget with a category, amount, start date, and end date.
     * @param category The budget category
     * @param amount The monetary limit of the budget
     * @param start_date The beginning of the budget period
     * @param end_date The end of the budget period
     */
    public Budget(String category, int amount,  String start_date, String end_date) {
        super(category, amount);
        this.start_date = start_date;
        this.end_date = end_date;
    }
    /**
     * Constructs a Budget with a category, amount, and end date.
     *
     * @param category The budget category
     * @param amount The monetary limit of the budget
     * @param end_date The end of the budget period
     */
    public Budget(String category, int amount, String end_date) {
        super(category, amount);
        this.end_date = end_date;
    }
    /**
     * Default constructor for creating an empty Budget.
     */
    public Budget() {}
    /**
     * Retrieves the budget category.
     * @return The current budget category
     */
    public String getCategory() {return getDescription();}
    /**
     * Sets a new budget category.
     * @param category The new budget category to be set
     */
    public void setCategory(String category) {this.description = category;}
    /**
     * Retrieves the full budget period as a string.
     * @return A string representing the budget period (start date - end date)
     */
    public String getPeriod() {return start_date + " - " + end_date;}
    /**
     * Retrieves the start date of the budget period.
     * @return The current start date
     */
    public String getStart_date() {return start_date;}
    /**
     * Retrieves the end date of the budget period.
     * @return The current end date
     */
    public String getEnd_date() {return end_date;}
    /**
     * Sets the end date of the budget period.
     * @param end_date The new end date to be set
     */
    public void setPeriod(String end_date) {this.end_date = end_date;}
    /**
     * Sets both the start and end dates of the budget period.
     * @param start_date The new start date to be set
     * @param end_date The new end date to be set
     */
    public void setPeriod(String start_date, String end_date) {
        this.start_date = start_date;
        this.end_date = end_date;
    }
    /**
     * Retrieves the budget limit (same as the amount).
     * @return The current budget limit
     */
    public int getLimit() {return getAmount();}
    /**
     * Sets the budget limit.
     * @param limit The new budget limit to be set
     */
    public void setLimit(int limit) {setAmount(limit);}
}

/**
 * Represents an income with a source and amount.
 * Extends the FinancialType class with income-specific methods.
 */
class Income extends FinancialType {
    /**
     * Constructs an Income with a specific source and amount.
     * @param source The origin of the income
     * @param amount The monetary value of the income
     */
    public Income(String source, int amount) {super(source, amount);}
    /**
     * Default constructor for creating an empty Income.
     */
    public Income() {}
    /**
     * Retrieves the source of the income.
     * @return The current income source
     */
    public String getSource() {return getDescription();}
    /**
     * Sets a new income source.
     * @param source The new income source to be set
     */
    public void setSource(String source) {setDescription(source);}
}