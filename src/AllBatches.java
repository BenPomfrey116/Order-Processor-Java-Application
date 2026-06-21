import java.util.*;

/**
 * A collection of batches.
 */
public class AllBatches
{
    private final Map<Integer, Batch> batches = new TreeMap<>();

    /**
     * Add a batch to the map
     * @param batch
     */
    public void addBatch(Batch batch) {
        batches.put(batch.getPartCode(), batch);
    }

    /**
     * Return all batches in the map
     * @return all batches - batches.values()
     */
    public Collection<Batch> getBatches() {
        return batches.values();
    }

    /**
     * Print the batches in the map
     */
    public void printBatches() {
        for (Batch batch : batches.values()) {
            System.out.println(batch);
        }
    }

    /**
     * Return a string representing the batches in the map
     * @return batches.values().toString()
     */
    @Override
    public String toString() {
        return batches.values().toString();
    }

}

