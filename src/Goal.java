/**
 * Represents a financial goal with a target amount, current savings, and description.
 * Tracks progress towards a specific financial objective.
 * @author Fares
 */
class Goal {
    /** The total monetary target for the goal. */
    private int target = 0;
    /** The current monetary amount achieved from the goal. */
    private int current_amount = 0;
    /** The current amount saved towards the goal. */
    private int saving_amount;

    /** A descriptive text explaining the purpose of the goal. */
    private String description = 0;

    /**
     * Constructs a new Goal with specified target, current savings, and description.
     * @param target The total monetary amount to be achieved
     * @param saving_amount The current amount already saved
     * @param description A detailed explanation of the goal's purpose
     */
    Goal(int target, int saving_amount, String description){
        this.target = target;
        this.saving_amount = saving_amount;
        this.description = description;
    }

    /**
     * Retrieves the target amount for the goal.
     * @return The total monetary target
     */
    public int getTarget() { return target; }

    /**
     * Retrieves the current amount saved towards the goal.
     * @return The current savings amount
     */
    public int getSaving_amount() { return saving_amount;}

    /**
     * Updates the current amount saved towards the goal.
     * @param saving_amount The new amount to be set as current savings
     */
    public void setSaving_amount(int saving_amount) {this.saving_amount = saving_amount;}

    /**
     * Retrieves the description of the goal.
     * @return The goal's descriptive text
     */
    public String getDescription() { return description; }

    /**
     * Updates the target amount for the goal.
     * @param target The new monetary target to be set
     */
    public void setTarget(int target) { this.target = target; }

    /**
     * Updates the description of the goal.
     * @param description The new descriptive text for the goal
     */
    public void setDescription(String description) { this.description = description; }

    /**
     * Updates the current amount by the saving amount
     */
    public void contribute() {
        this.current_amount += this.saving_amount;
    }
    /**
     * Calculates the percentage of the goal that has been completed.
     * @return The percentage of savings relative to the target (0-100)
     */
    public double getCompletionPercentage() {
        if (target <= 0) return 0;
        return (double) saving_amount / target * 100;
    }

    /**
     * Determines the remaining amount needed to achieve the goal.
     * @return The difference between the target and current savings
     */
    public int getRemainingAmount() {
        return Math.max(0, target - saving_amount);
    }
}