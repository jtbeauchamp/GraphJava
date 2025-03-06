//
//      Name:           Beauchamp, Joshua
//      Project:        5
//      Due:            12/08/23
//      Course:         cs-2400-02-f23
//
//      Description:
//                  The MinHeapInterface describes the methods that the MinHeap class will
//                  implement.
//

public interface MinHeapInterface<T extends Comparable<? super T>> {
    
    /**Adds a new entry to this heap
     * @param newEntry An object to be added. */
    public void add(T newEntry);

    /**Removes and returns the smallest item in this heap.
     * @return Either the smallest object in the heap or, if the heap
     *      is empty before the operation, null */
    public T removeMin();

    /**Retrieves the smallest item in this heap
     * @return Either the smallest object in this heap or,
     *      if the heap is empty, null */
    public T getMin();

    /**Gets the size of this heap.
     * @return The number of entries currently in the heap */
    public int getSize();

    /**Removes all entries from this heap. */
    public void clear();

    /**Checks if the heap is empty
     * @return True if heap is empty, else false */
    public boolean isEmpty();
}
