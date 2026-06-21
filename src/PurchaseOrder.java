import java.util.Objects;

/**
 * Class for PurchaseOrders
 * @author Ben Pomfrey
 */
public class PurchaseOrder extends Order {
    private static int nextOrderNumber = 0;

    /**
     * Constructor for PurchaseOrder objects
     * @param purchaseOrderNumber
     * @param dateOrdered
     * @param fulfilled
     */
    public PurchaseOrder(int purchaseOrderNumber, Date dateOrdered, boolean fulfilled) {
        super(purchaseOrderNumber, dateOrdered, fulfilled);
        if(purchaseOrderNumber >= nextOrderNumber) {
            nextOrderNumber = purchaseOrderNumber+1;
        }
    }

    public PurchaseOrder() {
        this(nextOrderNumber, Date.getNow(), false);
    }

    /**
     * Get the next order number
     * @return nextOrderNumber
     */
    public static int getNextOrderNumber() {
        return nextOrderNumber;
    }

    /**
     * Get the purchase order number
     * @return purchaseOrderNumber
     */
    public int getPurchaseOrderNumber() {
        return getOrderNumber();
    }

    /**
     * Return a formatted string containing the PurchaseOrder's details
     * @return formatted string
     */
    @Override
    public String toString() {
        String fulfilledString = "not fulfilled";
        if(isFulfilled()) {
            fulfilledString = "fulfilled";
        }
        return String.format("Purchase order %d, %s, ordered on %s for batches %s", getOrderNumber(), fulfilledString, getDateOrdered(), getAllBatches());
    }



}