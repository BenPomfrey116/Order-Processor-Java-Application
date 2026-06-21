import java.util.Collection;
import java.util.TreeMap;

/**
 * Superclass for AllCustomerOrders, AllPurchaseOrders, and AllDeliveries
 */
public class AllOrders {
    private TreeMap<Integer, Order> orders;

    /**
     * Constructor for AllOrders objects
     */
    public AllOrders() {
        orders = new TreeMap<>();
    }

    /**
     * Add an order to the TreeMap
     * @param order
     */
    public void addOrder(Order order) {
        orders.put(order.getOrderNumber(), order);
    }

    /**
     * Get an order from the TreeMap using its order number
     * @param orderNumber
     * @return order
     */
    public Order getOrder(int orderNumber) {
        return orders.get(orderNumber);
    }

    /**
     * Return the TreeMap of Orders
     * @return orders
     */
    public TreeMap<Integer, Order> getOrdersMap() {
        return orders;
    }

    /**
     * Print all orders in the map
     */
    public void printOrders() {
        for (Order order : orders.values()) {
            System.out.println(order);
        }
    }

}
