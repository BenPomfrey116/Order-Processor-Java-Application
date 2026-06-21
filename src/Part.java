/**
 * Record for a Part object in the database
 * @author Ben Pomfrey
 * @param partCode
 * @param type
 * @param manufacturer
 * @param description
 * @param price
 */
public record Part(int partCode, String type, String manufacturer, String description, double price) {
    /**
     * Return a formatted string containing the Part's fields.
     * @return formatted String
     */
    @Override
    public String toString() {
        return String.format("%s: %s, %s costs £%.2f", type, manufacturer, description, price);
    }
}