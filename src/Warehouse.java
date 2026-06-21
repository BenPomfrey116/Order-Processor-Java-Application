import java.util.*;

/**
 * The warehouse.
 * It is divided into a rectangular grid for storing batches.
 * Each batch consists of a part number and a quantity
 * Only one type of part is stored in any location of the grid.
 */
public class Warehouse {
    // The maximum quantity in any location of the grid.
    private static final int MAX_AMOUNT = 500;
    // The number of rows and columns.
    private final int numRows, numCols;
    // The grid.
    // Empty locations must be stored as null values.
    private final Batch[][] grid;

    /**
     * Create an empty warehouse of the given number of rows and columns.
     * @param numRows The number of rows.
     * @param numCols The number of columns.
     */
    public Warehouse(int numRows, int numCols){
        this.numRows = numRows;
        this.numCols = numCols;
        grid = new Batch[this.numRows][this.numCols];
    }

    /**
     * Add a batch to the warehouse at the given location
     * @param location
     * @param batch
     */
    public void addToWarehouse(Location location, Batch batch) {
        grid[location.row()][location.col()] = batch;
    }

    /**
     * Print all locations in the grid that contain batches
     */
    public void printOccupiedLocations() {
        for(int row = 0; row < numRows; row++) {
            for(int col = 0; col < numCols; col++) {
                Batch batch = grid[row][col];
                if(batch != null) {
                    System.out.printf("%d,%d: Part code: %d, quantity: %d%n", row, col, batch.getPartCode(), batch.getQuantity());
                }
            }
        }
    }

    /**
     * Return a list of all locations in the grid with batches
     * @return locations
     */
    public List<Location> getPartLocations() {
        List<Location> locations = new ArrayList<>();
        for(int row = 0; row < numRows; row++) {
            for(int col = 0; col < numCols; col++) {
                if(grid[row][col] != null) {
                    locations.add(new Location(row, col));
                }
            }
        }
        return locations;
    }

    /**
     * Find a batch at a given location in the grid
     * @param location
     * @return batch object at given location
     */
    public Batch getBatchAt(Location location) {
        return grid[location.row()][location.col()];
    }

    /**
     * Return a list of partCodes for parts which are currently available
     * @return partCodes
     */
    public List<Integer> getAvailablePartCodes() {
        List<Integer> partCodes = new ArrayList<>();
        for(int row = 0; row < numRows; row++) {
            for(int col = 0; col < numCols; col++) {
                Batch batch = grid[row][col];
                if(batch != null && !partCodes.contains(batch.getPartCode())) {
                    partCodes.add(batch.getPartCode());
                }
            }
        }
        return partCodes;
    }

    /**
     * Find the total quantity of a part in the warehouse
     * @param partCode
     * @return count
     */
    public int getPartCount(int partCode) {
        int count = 0;
        for(int row = 0; row < numRows; row++) {
            for(int col = 0; col < numCols; col++) {
                Batch batch = grid[row][col];
                if(batch != null && partCode == batch.getPartCode()) {
                    count += batch.getQuantity();
                }
            }
        }
        return count;
    }

    /**
     * Find all locations in the grid with a given part
     * @param partCode
     * @return partLocations
     */
    public List<Location> findPart(int partCode) {
        List<Location> partLocations = new ArrayList<>();
        for(int row = 0; row < numRows; row++) {
            for(int col = 0; col < numCols; col++) {
                Batch batch = grid[row][col];
                if(batch != null && partCode == batch.getPartCode()) {
                    partLocations.add(new Location(row, col));
                }
            }
        }
        return partLocations;
    }

    /**
     * Check if a given CustomerOrder can be filled
     * @param customerOrder
     * @return true or false
     */
    public boolean canBeFilled(CustomerOrder customerOrder) {
        boolean canBeFilled = true;
        for(Batch batch : customerOrder.getAllBatches().getBatches()) {
            if(batch.getQuantity() > getPartCount(batch.getPartCode())) {
                canBeFilled = false;
            }
        }
        return canBeFilled;
    }

    /**
     * Create a PurchaseOrder for parts which are out of stock
     * @param allParts
     * @return restockOrder
     */
    public PurchaseOrder createRestockOrder(AllParts allParts) {
        PurchaseOrder restockOrder = new PurchaseOrder();
        for(Part part : allParts.getParts()) {
            if(getPartCount(part.partCode()) == 0) {
                Batch batch = new Batch(part.partCode(), 50);
                restockOrder.addBatch(batch);
            }
        }
        if(restockOrder.getAllBatches().getBatches().size() == 0) {
            return null;
        }
        return restockOrder;
    }

    /**
     * Create a PurchaseOrder for parts which there aren't enough
     * of to complete a given CustomerOrder
     * @param customerOrder
     * @return purchaseOrder
     */
    public PurchaseOrder createPurchaseOrder(CustomerOrder customerOrder) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        for(Batch batch : customerOrder.getAllBatches().getBatches()) {
            int quantityOrdered = batch.getQuantity();
            int quantityInStock = getPartCount(batch.getPartCode());
            if(quantityOrdered > quantityInStock) {
                Batch newBatch = new Batch(batch.getPartCode(),quantityOrdered - quantityInStock);
                purchaseOrder.addBatch(newBatch);
            }
        }
        return purchaseOrder;
    }

    /**
     * Create a list of PickListItems for a given CustomerOrder
     * @param customerOrder
     * @return pickListItems
     */
    public List<PickListItem> createAPickList(CustomerOrder customerOrder) {
        List<PickListItem> pickListItems = new ArrayList<>();
        for(Batch batch : customerOrder.getAllBatches().getBatches()) {
            int quantityNeeded = batch.getQuantity();
            List<Location> locations = findPart(batch.getPartCode());
            int locationIndex = 0;
            while(locationIndex < locations.size() && quantityNeeded > 0) {
                Batch locationBatch = getBatchAt(locations.get(locationIndex));
                int locationQuantity = locationBatch.getQuantity();
                if(locationQuantity >= quantityNeeded) {
                    pickListItems.add(new PickListItem(locations.get(locationIndex), new Batch(batch.getPartCode(), quantityNeeded)));
                    locationBatch.reduceQuantity(quantityNeeded);
                    quantityNeeded = 0;
                }
                else {
                    pickListItems.add(new PickListItem(locations.get(locationIndex), new Batch(batch.getPartCode(), locationQuantity)));
                    locationBatch.reduceQuantity(locationQuantity);
                    quantityNeeded -= locationQuantity;
                }
                if(locationBatch.getQuantity() == 0) {
                    grid[locations.get(locationIndex).row()][locations.get(locationIndex).col()] = null;
                }
                locationIndex++;
            }
        }
        return pickListItems;
    }

    /**
     * Store a given delivery in the warehouse
     * @param delivery
     * @return
     */
    /**
     * Store a given delivery in the warehouse
     * @param delivery
     * @return
     */
    public List<Location> storeDelivery(Delivery delivery) {
        List<Location> locationsToStore = new ArrayList<>();
        for(Batch deliveryBatch : delivery.getAllBatches().getBatches()) {
            int quantityToStore = deliveryBatch.getQuantity();
            List<Location> partLocations = findPart(deliveryBatch.getPartCode());
            for(Location location : partLocations) {
                Batch locationBatch = getBatchAt(location);
                int spaceLeft = MAX_AMOUNT - locationBatch.getQuantity();
                if(spaceLeft > 0) {
                    int quantityToAdd = Math.min(spaceLeft, quantityToStore);
                    locationBatch.increaseQuantity(quantityToAdd);
                    quantityToStore -= quantityToAdd;
                    locationsToStore.add(location);
                    if(quantityToStore == 0) {
                        break;
                    }
                }
            }
            while(quantityToStore > 0) {
                boolean itemsAreStored = false;
                for(int row = 0; row < numRows; row++) {
                    for(int col = 0; col < numCols; col++) {
                        if(grid[row][col] == null) {
                            int quantityToAdd = Math.min(MAX_AMOUNT, quantityToStore);
                            grid[row][col] = new Batch(deliveryBatch.getPartCode(), quantityToAdd);
                            quantityToStore -= quantityToAdd;
                            locationsToStore.add(new Location(row, col));
                            itemsAreStored = true;
                            if(quantityToStore == 0) {
                                break;
                            }
                        }
                    }
                    if(itemsAreStored) {
                        break;
                    }
                }
            }
        }
        return locationsToStore;
    }

}
