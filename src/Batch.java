/**
 * A part and how many of them.
 */
public class Batch implements Comparable<Batch>
{
    // The part code.
    private final int partCode;
    // The number of this part.
    private int quantity;

    /**
     * A part and a number of items.
     * @param partCode The part code.
     * @param quantity The number of items of this part code.
     */
    public Batch(int partCode, int quantity)
    {
        this.partCode = partCode;
        this.quantity = quantity;
    }

    /**
     * Get the part code of the batch
     * @return The part code.
     */
    public int getPartCode() {
        return partCode;
    }

    /**
     * Get the number of parts in the batch
     * @return The quantity.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Increase the quantity of the batch by amount
     * @param amount
     */
    public void increaseQuantity(int amount) {
        quantity += amount;
    }

    /**
     * Reduce the quantity of the batch by amount
     * @param amount
     */
    public void reduceQuantity(int amount) {
        quantity -= amount;
    }

    /**
     * Return a formatted string containing the Batch's fields.
     * @return formatted String
     */
    @Override
    public String toString() {
        return String.format("Part code: %d, quantity: %d", partCode, quantity);
    }

    @Override
    public int compareTo(Batch other) {
        return partCode - other.partCode;
    }
}
