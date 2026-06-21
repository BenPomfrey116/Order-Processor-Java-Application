/**
 * Record for a Location object in the database
 * @author Ben Pomfrey
 * @param row
 * @param col
 */
public record Location(int row, int col) {
    /**
     * Return a formatted string containing the Location's fields
     * @return formatted String
     */
    @Override
    public String toString() {
        return String.format("(%d,%d)", row, col);
    }
}