/**
 * Record for a Customer object in the database
 * @param customerCode
 * @param businessName
 * @param vatNumber
 * @param email
 * @param phoneNumber
 * @param address
 * @author Ben Pomfrey
 */
public record Customer(int customerCode, String businessName, String vatNumber, String email, String phoneNumber, String address) implements Comparable<Customer> {
    /**
     * Return a formatted string containing the Customer's fields
     * @return formatted String
     */
    @Override
    public String toString() {
        return String.format("%s, %s", businessName, address);
    }

    /**
     * Compare two Customer records by their customerCode
     * @param other the object to be compared.
     * @return -1, 0, or 1 depending on the code comparison
     */
    @Override
    public int compareTo(Customer other) {
        return Integer.compare(this.customerCode, other.customerCode);
    }
}