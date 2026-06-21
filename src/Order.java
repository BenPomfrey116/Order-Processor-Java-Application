import java.util.Objects;

/**
 * Superclass for PurchaseOrder, CustomerOrder, and Delivery
 */
public class Order implements Comparable<Order> {
    private int orderNumber;
    private Date orderDate;
    private boolean fulfilled;
    private AllBatches allBatches;

    /**
     * Constructor for Order objects
     * @param orderNumber
     * @param orderDate
     * @param fulfilled
     */
    public Order(int orderNumber, Date orderDate, boolean fulfilled) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.fulfilled = fulfilled;
        this.allBatches = new AllBatches();
    }

    /**
     * Get the order number
     * @return orderNumber
     */
    public int getOrderNumber() {
        return orderNumber;
    }

    /**
     * Get the date
     * @return orderDate
     */
    public Date getDateOrdered() {
        return orderDate;
    }

    /**
     * Get if the order has been fulfilled
     * @return fulfilled
     */
    public boolean isFulfilled() {
        return fulfilled;
    }

    /**
     * Set the value of fulfilled to true
     */
    public void setFulfilled() {
        fulfilled = true;
    }

    /**
     * Get all the batches for this purchase order
     * @return allBatches
     */
    public AllBatches getAllBatches() {
        return allBatches;
    }

    /**
     * Add a batch to allBatches
     * @param batch
     */
    public void addBatch(Batch batch) {
        allBatches.addBatch(batch);
    }

    /**
     * Compare two Orders by their orderNumber
     *
     * @param other the object to be compared.
     * @return -1, 0, or 1 depending on the code comparison
     */
    @Override
    public int compareTo(Order other) {
        return Integer.compare(orderNumber, other.getOrderNumber());
    }

    /**
     * Create a hash code using just the orderNumber
     * @return integer hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(orderNumber);
    }

    /**
     * Check if two Order objects are equal
     * @param obj
     * @return equality
     */
    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PurchaseOrder other = (PurchaseOrder) obj;
        return this.getOrderNumber() == other.getPurchaseOrderNumber();
    }

}
