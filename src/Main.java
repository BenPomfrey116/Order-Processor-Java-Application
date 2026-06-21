import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.*;

/**
 * Main class for the A2 assignment.
 * The main method connects to the database and retrieves
 * objects that store all the data from the database.
 */
public class Main {
    // A random number generator.
    private final static Random rand = new Random();
    // Access to the database.
    private static DatabaseHandler databaseHandler = null;

    /**
     * Read tables from the database and support some operations on the data.
     *
     * @param args Not used.
     * @throws SQLException If there are problems accessing the database.
     */
    public static void main(String[] args) throws SQLException {
        setup();

        AllParts allParts = databaseHandler.readParts();
        //allParts.printParts();
        Warehouse theWarehouse = databaseHandler.readWarehouse();
        //theWarehouse.printOccupiedLocations();
        AllCustomers allCustomers = databaseHandler.readCustomers();
        //System.out.println("=== All Customers");
        //allCustomers.printCustomers();
        AllCustomerOrders allCustomerOrders = databaseHandler.readCustomerOrders();
        //allCustomerOrders.printOrders();
        AllPurchaseOrders allPurchaseOrders = databaseHandler.readPurchaseOrders();
        //allPurchaseOrders.printOrders();
        AllDeliveries allDeliveries = databaseHandler.readDeliveries();



        part1(theWarehouse);
        part2(databaseHandler);
        part3(databaseHandler, allParts, allCustomerOrders);
        part4(theWarehouse, allCustomerOrders);
        part5(databaseHandler, theWarehouse, allParts, allPurchaseOrders);
        part6(theWarehouse, allCustomerOrders, allPurchaseOrders);
        part7(theWarehouse, allCustomerOrders);
        part8(databaseHandler, theWarehouse, allDeliveries);
    }

    /**
     * Test completion of part 1 of the assignment.
     */
    public static void part1(Warehouse theWarehouse) {
        System.out.println("Part 1 tests");
        // TODO: Complete this part.
        // Print the locations that contain at least one part.
        List<Location> partLocations = theWarehouse.getPartLocations();
        System.out.println("Part1a: Results of running getPartLocations: " +
                partLocations);
        // Print details of the part at a random location.
        System.out.print("Part1b: Results of running getBatchAt: ");
        if (!partLocations.isEmpty()) {
            Location theLocation = partLocations.get(
                    rand.nextInt(partLocations.size()));
            Batch aBatch = theWarehouse.getBatchAt(theLocation);
            System.out.printf("At location %s is %s%n", theLocation, aBatch);
        } else {
            System.out.println("No parts available.");
        }
        // Print the count of a random part that is in the warehouse.
        List<Integer> availableParts = theWarehouse.getAvailablePartCodes();
        // Select a random part code.
        int index = rand.nextInt(availableParts.size());
        int partCode = availableParts.get(index);
        int count = theWarehouse.getPartCount(partCode);
        System.out.printf("Part1c: getPartCount: There are %d boxes of part %d%n",
                count, partCode);
        // Print the locations of a random part.
        index = rand.nextInt(availableParts.size());
        partCode = availableParts.get(index);
        List<Location> locationsOfPart = theWarehouse.findPart(partCode);
        System.out.printf("Part1d: findPart: Part %s is at location(s): %s%n",
                partCode, locationsOfPart);




        System.out.println("=== End of part 1");
        System.out.println();
    }

    /**
     * Get the contents of the partTypes table and print the pairs.
     * @throws SQLException If there is an error accessing the database.
     */
    private static void part2(DatabaseHandler databaseHandler)  throws SQLException {
        System.out.println("Part 2 tests");
        // TODO: Complete this part.
        Map<String, String> partTypes = databaseHandler.readPartTypes();
        System.out.println("Results of running readPartTypes:");
        for(String type : partTypes.keySet()) {
            System.out.printf("%s: %s%n", type, partTypes.get(type));
        }
        System.out.println("=== End of part 2");
        System.out.println();
    }

    /**
     * Calculate the cost of each customer order.
     */
    private static void part3(DatabaseHandler databaseHandler, AllParts allParts, AllCustomerOrders allCustomerOrders) throws SQLException  {
        System.out.println("Part 3 tests");
        // TODO: Complete this part.
        AllOrderItems customerOrderItems = databaseHandler.readOrderItems("customerOrderItems");
        for(OrderItem orderItem : customerOrderItems.getOrderItems()) {
            Batch batch = new Batch(orderItem.partCode(), orderItem.quantity());
            allCustomerOrders.getOrder(orderItem.orderNumber()).addBatch(batch);
        }
        for(CustomerOrder customerOrder : allCustomerOrders.getOrders()) {
            double cost = allParts.getCost(customerOrder);
            System.out.printf("Customer order %3d costs £%9.2f%n", customerOrder.getOrderNumber(), cost);
        }
        System.out.println("=== End of part 3");
        System.out.println();
    }

    /**
     * Check which unfilled orders can be filled.
     */
    public static void part4(Warehouse theWarehouse, AllCustomerOrders allCustomerOrders) {
        System.out.println("Part 4 tests");
        // TODO: Complete this part.
        for(CustomerOrder customerOrder : allCustomerOrders.getOrders()) {
            if(theWarehouse.canBeFilled(customerOrder)) {
                System.out.printf("Customer order %d: is in stock.%n", customerOrder.getOrderNumber());
            }
            else {
                System.out.printf("Customer order %d: needs a purchase order.%n", customerOrder.getOrderNumber());
            }
        }

        System.out.println("=== End of part 4");
        System.out.println();
    }

    /**
     * Create a purchase order for any parts not currently in stock.
     * @param databaseHandler The database handler.
     */
    private static void part5(DatabaseHandler databaseHandler, Warehouse theWarehouse, AllParts allParts, AllPurchaseOrders allPurchaseOrders)
            throws SQLException {
        System.out.println("Part 5 tests");
        // TODO: Complete this part.
        AllOrderItems purchaseOrderItems = databaseHandler.readOrderItems("purchaseOrderItems");
        for(OrderItem orderItem : purchaseOrderItems.getOrderItems()) {
            Batch batch = new Batch(orderItem.partCode(), orderItem.quantity());
            allPurchaseOrders.getOrder(orderItem.orderNumber()).addBatch(batch);
        }
        PurchaseOrder restockOrder = theWarehouse.createRestockOrder(allParts);
        if(restockOrder != null) {
            System.out.println(restockOrder);
        }
        System.out.println("=== End of part 5");
        System.out.println();
    }

    /**
     * Generate a purchase order for an unfilled order that cannot be filled.
     * Only order parts from the customer order for which there are not
     * enough in the warehouse.
     */
    public static void part6(Warehouse theWarehouse, AllCustomerOrders allCustomerOrders, AllPurchaseOrders allPurchaseOrders) {
        System.out.println("Part 6 tests");
        // TODO: Complete this part.
        ArrayList<CustomerOrder> unfilledOrders = new ArrayList<>();
        for(CustomerOrder customerOrder : allCustomerOrders.getOrders()) {
            if(!theWarehouse.canBeFilled(customerOrder)) {
                unfilledOrders.add(customerOrder);
            }
        }
        Random rand = new Random();
        CustomerOrder unfilledCustomerOrder = unfilledOrders.get(rand.nextInt(unfilledOrders.size()));
        PurchaseOrder purchaseOrder = theWarehouse.createPurchaseOrder(unfilledCustomerOrder);
        allPurchaseOrders.addOrder(purchaseOrder);
        System.out.printf("Customer order %d triggered %s%n", unfilledCustomerOrder.getOrderNumber(), purchaseOrder);
        System.out.println("=== End of part 6");
        System.out.println();
    }

    /**
     * Generate a pick list for at least one customer order that
     * can be fulfilled.
     */
    public static void part7(Warehouse theWarehouse, AllCustomerOrders allCustomerOrders) {
        System.out.println("Part 7 tests");
        // TODO: Complete this part.
        ArrayList<CustomerOrder> filteredOrders = new ArrayList<>();
        for(CustomerOrder customerOrder : allCustomerOrders.getOrders()) {
            if(theWarehouse.canBeFilled(customerOrder) && !customerOrder.isFulfilled()) {
                filteredOrders.add(customerOrder);
            }
        }
        Random rand = new Random();
        CustomerOrder customerOrder = filteredOrders.get(rand.nextInt(filteredOrders.size()));
        List<PickListItem> pickListItems = theWarehouse.createAPickList(customerOrder);
        customerOrder.setFulfilled();
        System.out.println("Results of running createAPickList:");
        System.out.printf("Customer Order %d fulfilled for customer %d%n", customerOrder.getOrderNumber(), customerOrder.getCustomerCode());
        System.out.printf("Pick list is %s%n", pickListItems);

        System.out.println("=== End of part 7");
        System.out.println();
    }

    /**
     * Store the contents of a random delivery in the warehouse.
     */
    public static void part8(DatabaseHandler databaseHandler, Warehouse theWarehouse, AllDeliveries allDeliveries)
            throws SQLException {
        System.out.println("Part 8 tests");
        // TODO: Complete this part.
        AllOrderItems deliveryItems = databaseHandler.readOrderItems("deliveryItems");
        for(OrderItem orderItem : deliveryItems.getOrderItems()) {
            Batch batch = new Batch(orderItem.partCode(), orderItem.quantity());
            allDeliveries.getDelivery(orderItem.orderNumber()).addBatch(batch);
        }
        List<Delivery> outstandingDeliveries = new ArrayList<>();
        List<Integer> outstandingDeliveryNumbers = allDeliveries.getOutstandingDeliveryNumbers();
        for(int deliveryNumber : outstandingDeliveryNumbers) {
            outstandingDeliveries.add(allDeliveries.getDelivery(deliveryNumber));
        }
        Random rand = new Random();
        Delivery delivery = outstandingDeliveries.get(rand.nextInt(outstandingDeliveries.size()));
        List<Location> locations = theWarehouse.storeDelivery(delivery);
        delivery.setFulfilled();
        System.out.println(delivery);
        for(Location location : locations) {
            Batch locationBatch = theWarehouse.getBatchAt(location);
            System.out.printf("Part %d was stored at: %s which now contains %d boxes.%n", locationBatch.getPartCode(), location, locationBatch.getQuantity());
        }



        System.out.println("=== End of part 8");
        System.out.println();
    }

    /**
     * Set up the connection to the database.
     * If the connection fails, the program will exit.
     */
    private static void setup() {
        try {
            databaseHandler = new DatabaseHandler();
        } catch (SQLTimeoutException e) {
            System.err.println("Failed to connect to the database.");
            System.exit(1);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Failed to access the database: " + e);
            System.exit(1);
        }
    }

}
