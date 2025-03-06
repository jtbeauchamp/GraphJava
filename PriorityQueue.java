//
//      Name:           Beauchamp, Joshua
//      Project:        5
//      Due:            12/08/23
//      Course:         cs-2400-02-f23
//
//      Description:
//                  The PriorityQueue class contains the implementations of the PriorityQueueInterface, and uses a MinHeap
//                  as the "queue" itself for its operations. All the implementations are mapped from the MinHeap class
//

public class PriorityQueue<T extends Comparable<? super T>> implements PriorityQueueInterface<T>{

    //Creates the priority queue that uses minheap
    private MinHeapInterface<T> priorityQueue;

    public PriorityQueue(){
        priorityQueue = new MinHeap<>();
    }

    /**Adds a new entry to this priority queue.
     * @param newEntry An object to be added. */
    @Override
    public void add(T newEntry) {
        priorityQueue.add(newEntry);
    }

    /**Removes and returns the entry having the highest priority.
     * @return Either the object having the highest priority or, if
     *      the priority queue is empty before the operation, null */
    @Override
    public T remove() {
        return priorityQueue.removeMin();
    }

    /**Retireves the entry having the highest prioirty.
     * @return Either the object having the highest priority or, if the
     *      priority queue is empty, null */
    @Override
    public T peek() {
        return priorityQueue.getMin();
    }

    /**Detects whether this priority queue is empty. 
     * @return True if the priority queue is empty, or false otherwise  */
    @Override
    public boolean isEmpty() {
        return priorityQueue.isEmpty();
    }

    /**Gets the size of this priority queue.
     * @return The number of entries currently in the priority queue. */
    @Override
    public int getSize() {
        return priorityQueue.getSize();
    }

    /**Removes all entries from this priority queue. */
    @Override
    public void clear() {
        priorityQueue.clear();
    }
}
