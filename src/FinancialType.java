public abstract class FinancialType {
    protected String description;
    protected int amount, user_id;
    public FinancialType(String description, int amount) {
        this.description = description;
        this.amount = amount;
    }
    public FinancialType() {}
    protected String getDescription() {return description;}
    protected void setDescription(String description) {this.description = description;}
    public int getAmount() {return amount;}
    public void setAmount(int amount) {this.amount = amount;}
    public void changeAmount(int change) {this.amount += change;}
    public void setUserID(int user_id) {this.user_id = user_id;}
    public int getUserID() {return user_id;}
}

class Expense extends FinancialType {
    public Expense(String item, int amount) {super(item, amount);}
    public Expense() {}
    public String getItem() {return getDescription();}
    public void setItem(String item) {setDescription(item);}
}

class Budget extends FinancialType {
    private String category, start_date = null, end_date = null;

    public Budget(String source, int amount, String category, String start_date, String end_date) {
        super(source, amount);
        this.category = category;
        this.start_date = start_date;
        this.end_date = end_date;
    }
    public Budget() {}
    public String getSource() {return getDescription();}
    public void setSource(String source) {setDescription(source);}
    public String getCategory() {return category;}
    public void setCategory(String category) {this.category = category;}
    public String getPeriod() {return start_date + " - " + end_date;}
    public void setPeriod(String end_date) {this.end_date = end_date;}
    public void setPeriod(String start_date, String end_date) {
        this.start_date = start_date;
        this.end_date = end_date;
    }
}

class Income extends FinancialType {
    public Income(String source, int amount) {super(source, amount);}
    public Income() {}
    public String getSource() {return getDescription();}
    public void setSource(String source) {setDescription(source);}
}