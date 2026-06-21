import java.util.Objects;

/**
 * Class for CustomerOrders
 * @author Ben Pomfrey
 */
public class CustomerOrder extends Order {
    private int customerCode;

    /**
     * Constructor for CustomerOrder objects
     * @param orderNumber
     * @param customerCode
     * @param dateOrdered
     * @param fulfilled
     */
    public CustomerOrder(int orderNumber, int customerCode, Date dateOrdered, boolean fulfilled) {
        super(orderNumber, dateOrdered, fulfilled);
        this.customerCode = customerCode;
    }

    /**
     * Get the customer code of the CustomerOrder
     * @return customerCode
     */
    public int getCustomerCode() {
        return customerCode;
    }

    /**
     * Return a formatted string containing the CustomerOrder's fields and details
     *
     * @return formatted String
     */
    @Override
    public String toString() {
        String fulfilledString = "not fulfilled";
        if (isFulfilled()) {
            fulfilledString = "fulfilled";
        }
        return String.format("For customer %d, order %d, %s, ordered on %s for batches %s",
                customerCode, getOrderNumber(), fulfilledString, getDateOrdered(), getAllBatches());
    }

}