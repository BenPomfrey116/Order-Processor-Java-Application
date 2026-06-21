/**
 * Record for a PickListItem
 * @param theLocation
 * @param theBatch
 * @author Ben Pomfrey
 */
public record PickListItem(Location theLocation, Batch theBatch) {
    /**
     * Return a formatted string containing the PickListItem's fields
     * @return formatted String
     */
    @Override
    public String toString() {
        return String.format("Location: %s, Batch: %s", theLocation, theBatch);
    }
}