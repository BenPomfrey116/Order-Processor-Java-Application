import java.util.TreeMap;

/**
 * Class for storing all customers.
 * Stores all the customers in a TreeMap with the customer code as the key
 * @author Ben Pomfrey
 */
public class AllCustomers {
    private TreeMap<Integer, Customer> customers;

    /**
     * Constructor for AllCustomers objects
     */
    public AllCustomers() {
        customers = new TreeMap<>();
    }

    /**
     * Add a customer to the TreeMap
     * @param customer
     */
    public void addCustomer(Customer customer) {
        customers.put(customer.customerCode(), customer);
    }

    /**
     * Print all customers in the TreeMap
     */
    public void printCustomers() {
        for (Customer customer : customers.values()) {
            System.out.println(customer);
        }
    }
}
