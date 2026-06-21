import java.util.ArrayList;
import java.util.List;

/**
 * Class for storing all order items.
 * @author Ben Pomfrey
 */
public class AllOrderItems {
    private List<OrderItem> orderItems;

    /**
     * Constructor for AllOrderItems objects
     */
    public AllOrderItems() {
        orderItems = new ArrayList<>();
    }

    /**
     * Add an OrderItem to the list
     * @param orderItem
     */
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
    }

    /**
     * Return the list of OrderItems
     * @return orderItems
     */
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

}
