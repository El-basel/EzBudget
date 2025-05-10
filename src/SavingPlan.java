public class SavingPlan {
    private int target, startAmount, contribution;

    public SavingPlan(){}
    public SavingPlan(int target, int startAmount, int contribution) {
        this.target = target;
        this.startAmount = startAmount;
        this.contribution = contribution;
    }

    //setters and getters
    public int getTarget() { return target; }
    public int getStartAmount() { return startAmount; }
    public int getContribution() { return contribution; }
    public void setTarget(int target) { this.target = target; }
    public void setStartAmount(int startAmount) { this.startAmount = startAmount; }
    public void setContribution(int contribution) { this.contribution = contribution; }
}
