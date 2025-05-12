/**
 * The {@code SavingPlan} class represents a financial savings plan.
 * It tracks a target amount, a starting amount, and a recurring contribution.
 * @author Fares
 */
public class SavingPlan {
    /** The total savings target to be achieved. */
    private int target;
    /** The starting balance at the beginning of the plan. */
    private int startAmount;
    /** The recurring contribution amount added over time. */
    private int contribution;

    /**
     * Default constructor, that constructs a new SavingPlan with default values (0 for all fields).
     */
    public SavingPlan(){}

    /**
     * Constructs a new SavingPlan with specified values for target, starting amount, and contribution.
     *
     * @param target The total amount aimed to be saved
     * @param startAmount The current amount already saved
     * @param contribution The amount to be added regularly (e.g., monthly)
     */
    public SavingPlan(int target, int startAmount, int contribution) {
        this.target = target;
        this.startAmount = startAmount;
        this.contribution = contribution;
    }

    /**
     * Retrieves the total savings target.
     *
     * @return The target savings amount
     */
    public int getTarget() { return target; }

    /**
     * Retrieves the starting balance of the plan.
     *
     * @return The initial amount saved
     */
    public int getStartAmount() { return startAmount; }

    /**
     * Retrieves the contribution amount.
     *
     * @return The recurring contribution value
     */
    public int getContribution() { return contribution; }

    /**
     * Updates the savings target.
     *
     * @param target The new total amount to be saved
     */
    public void setTarget(int target) { this.target = target; }

    /**
     * Updates the starting balance of the plan.
     *
     * @param startAmount The new starting amount
     */
    public void setStartAmount(int startAmount) { this.startAmount = startAmount; }

    /**
     * Updates the recurring contribution amount.
     *
     * @param contribution The new contribution amount
     */
    public void setContribution(int contribution) { this.contribution = contribution; }
}
