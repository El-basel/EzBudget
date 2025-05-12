public class Goal {
    private int target;
    private int saving_amount;
    private String description;

    Goal(int target, int saving_amount, String description){
        this.target = target;
        this.saving_amount = saving_amount;
        this.description = description;
    }

    //setters and getters
    public int getTarget() { return target; }
    public int getSaving_amount() { return saving_amount;}
    public void setSaving_amount(int saving_amount) {this.saving_amount = saving_amount;}
    public String getDescription() { return description; }
    public void setTarget(int target) { this.target = target; }
    public void setDescription(String description) { this.description = description; }
}
