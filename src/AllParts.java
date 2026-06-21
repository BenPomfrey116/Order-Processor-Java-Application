import java.util.Collection;
import java.util.TreeMap;

/**
 * Class for storing all the parts in the database.
 * Stores all parts in a tree map and allows for adding parts and access to all the parts
 * @author Ben Pomfrey
 */
public class AllParts {
    private TreeMap<Integer, Part> parts;

    /**
     * Constructor for AllParts objects
     */
    public AllParts() {
        parts = new TreeMap<>();
    }

    /**
     * Get all the parts in the database
     * @return all parts - parts.values()
     */
    public Collection<Part> getParts() {
        return parts.values();
    }

    /**
     * Add a part to the database
     * @param part
     */
    public void addPart(Part part) {
        parts.put(part.partCode(), part);
    }

    /**
     * Print all the parts in the database
     */
    public void printParts() {
        for(Part part : parts.values()) {
            System.out.println(part);
        }
    }

    /**
     * Get the total cost of a given CustomerOrder
     * @param customerOrder
     * @return totalCost
     */
    public double getCost(CustomerOrder customerOrder) {
        AllBatches allBatches = customerOrder.getAllBatches();
        double totalCost = 0;
        for(Batch batch : allBatches.getBatches()) {
            double price = parts.get(batch.getPartCode()).price();
            totalCost += price * batch.getQuantity();
        }
        return totalCost;
    }
}