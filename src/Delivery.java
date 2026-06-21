import java.util.Objects;

/**
 * Class for a Delivery
 * @author Ben Pomfrey
 */

public class Delivery extends Order {

    /**
     * Constructor for Delivery objects
     * @param deliveryNumber
     * @param dateDelivered
     * @param fulfilled
     */
    public Delivery(int deliveryNumber, Date dateDelivered, boolean fulfilled) {
        super(deliveryNumber, dateDelivered, fulfilled);
    }

    /**
     * Get the delivery number
     * @return deliveryNumber
     */
    public int getDeliveryNumber() {
        return getOrderNumber();
    }

    /**
     * Get the date the delivery was made
     * @return dateDelivered
     */
    public Date getDateDelivered() {
        return getDateOrdered();
    }

    /**
     * Return a formatted string containing the Delivery's details
     * @return formatted string
     */
    @Override
    public String toString() {
        return String.format("Delivery: %d to be unloaded ordered on %s for items %s%n", getDeliveryNumber(), getDateDelivered(), getAllBatches());
    }

}