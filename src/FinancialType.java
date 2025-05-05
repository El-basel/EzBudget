public abstract class FinancialType {
    protected int typeID;
    protected String description;
    protected int amount;
    public FinancialType(int typeID, String description, int amount) {
        this.typeID = typeID;
        this.description = description;
        this.amount = amount;
    }
    public FinancialType() {}
    public int getTypeID() {
        return typeID;
    }
    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }
    protected String getDescription() {
        return description;
    }
    protected void setDescription(String description) {
        this.description = description;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public void changeAmount(int change) {
        this.amount += change;
    }
}

class Expense extends FinancialType {
    public Expense(int typeID, String item, int amount) {
        super(typeID, item, amount);
    }
    public Expense() {}
    public String getItem() {
        return getDescription();
    }
    public void setItem(String item) {
        setDescription(item);
    }
}

class Budget extends FinancialType {
    public Budget(int typeID, String source, int amount) {
        super(typeID, source, amount);
    }
    public Budget() {}
    public String getSource() {
        return getDescription();
    }
    public void setSource(String source) {
        setDescription(source);
    }
}

class Income extends FinancialType {
    public Income(int typeID, String source, int amount) {
        super(typeID, source, amount);
    }
    public Income() {}
    public String getSource() {
        return getDescription();
    }
    public void setSource(String source) {
        setDescription(source);
    }
}