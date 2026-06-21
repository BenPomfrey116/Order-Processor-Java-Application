import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

/**
 * Class for AllCustomerOrders
 * Stores all the customer orders in a TreeMap with the order number as the key
 * @author Ben Pomfrey
 */
public class AllCustomerOrders extends AllOrders {

    /**
     * Constructor for AllCustomerOrders objects
     */
    public AllCustomerOrders() {
        super();
    }

    /**
     * Get a list of all customer order numbers
     * @return all order numbers
     */
    public List<Integer> getOrderNumbers() {
        return new ArrayList<>(getOrdersMap().keySet());
    }

    /**
     * Get a list of all CustomerOrder objects
     * @return all CustomerOrder objects
     */
    public Collection<CustomerOrder> getOrders() {
        Collection<Order> orders = getOrdersMap().values();
        List<CustomerOrder> customerOrders = new ArrayList<>();
        for(Order order : orders) {
            if(order instanceof CustomerOrder customerOrder) {
                customerOrders.add(customerOrder);
            }
        }
        return customerOrders;
    }

}
