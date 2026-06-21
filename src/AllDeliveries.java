import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Class for managing all deliveries
 * @author Ben Pomfrey
 */
public class AllDeliveries {
    private TreeMap<Integer, Delivery> deliveries;

    /**
     * Constructor for AllDeliveries Objects
     */
    public AllDeliveries() {
        deliveries = new TreeMap<>();
    }

    /**
     * Add a delivery to the TreeMap
     *
     * @param delivery
     */
    public void addDelivery(Delivery delivery) {
        deliveries.put(delivery.getDeliveryNumber(), delivery);
    }

    /**
     * Get a given Delivery from the map using its order number
     *
     * @param deliveryNumber
     * @return delivery
     */
    public Delivery getDelivery(int deliveryNumber) {
        return deliveries.get(deliveryNumber);
    }

    /**
     * Print all Deliveries in the map
     */
    public void printDeliveries() {
        for (Delivery delivery : deliveries.values()) {
            System.out.println(delivery);
        }
    }

    public List<Integer> getOutstandingDeliveryNumbers() {
        List<Integer> outstandingDeliveryNumbers = new ArrayList<>();
        for(Delivery delivery : deliveries.values()) {
            if (!delivery.isFulfilled()) {
                outstandingDeliveryNumbers.add(delivery.getDeliveryNumber());
            }
        }
        return outstandingDeliveryNumbers;
    }

}
