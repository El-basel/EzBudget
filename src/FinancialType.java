public abstract class FinancialType {
    protected String description;
    protected int amount;
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
}

class Expense extends FinancialType {
    public Expense(String item, int amount) {super(item, amount);}
    public Expense() {}
    public String getItem() {return getDescription();}
    public void setItem(String item) {setDescription(item);}
}

class Budget extends FinancialType {
    private String start_date = null, end_date = null;

    public Budget(String category, int amount,  String start_date, String end_date) {
        super(category, amount);
        this.start_date = start_date;
        this.end_date = end_date;
    }
    public Budget(String category, int amount, String end_date) {
        super(category, amount);
        this.end_date = end_date;
    }
    public Budget() {}
    public String getCategory() {return getDescription();}
    public void setCategory(String category) {this.description = category;}
    public String getPeriod() {return start_date + " - " + end_date;}
    public String getStart_date() {return start_date;}
    public String getEnd_date() {return end_date;}
    public void setPeriod(String end_date) {this.end_date = end_date;}
    public void setPeriod(String start_date, String end_date) {
        this.start_date = start_date;
        this.end_date = end_date;
    }
    public int getLimit() {return getAmount();}
    public void setLimit(int limit) {setAmount(limit);}
}

class Income extends FinancialType {
    public Income(String source, int amount) {super(source, amount);}
    public Income() {}
    public String getSource() {return getDescription();}
    public void setSource(String source) {setDescription(source);}
}