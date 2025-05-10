public class Goal {
    private int target;
    private String startDate, endDate, discription;

    Goal(int target, String startDate, String endDate, String discription){
        this.target = target;
        this.startDate = startDate;
        this.endDate = endDate;
        this.discription = discription;
    }

    //setters and getters
    public int getTarget() { return target; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
    public String getDiscription() { return discription; }
    public void setTarget(int target) { this.target = target; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }
    public void setDiscription(String discription) { this.discription = discription; }
}
