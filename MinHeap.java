//
//      Name:           Beauchamp, Joshua
//      Project:        5
//      Due:            12/08/23
//      Course:         cs-2400-02-f23
//
//      Description:
//                  The MinHeap class that contains the implementations of the MinHeapInterface. 
//                  It is used as the priority queue to find the shortest path between two vertices
//                  

public class MinHeap<T extends Comparable<? super T>> implements MinHeapInterface<T> {

    private T[] heap;       //The MinHeap
    private int lastIndex;  //The last index in the heap
    private static final int DEFAULT_CAPACITY = 64; 
    
    /**Constructor that creates a MinHeap given a set capacity
     * @param initialCapacity The size the minHeap will be */
    public MinHeap(int initialCapacity){
        @SuppressWarnings("unchecked")
        T[] tempHeap = (T[])new Comparable[initialCapacity + 1];
        heap = tempHeap;
        lastIndex = 0;
    }

    /**MinHeap constructor that creates a minheap given an array of T objects
     * @param entries The array being turned into a minheap */
    public MinHeap(T[] entries){
        this(entries.length);
        lastIndex = entries.length;

        for(int i = 0; i < entries.length; i++){
            heap[i + 1] = entries[i];
        }

        for(int rootIndex = lastIndex / 2; rootIndex > 0; rootIndex--){
            reheap(rootIndex);
        }
    }

    //Dedault constructor for MinHeap
    public MinHeap(){
        this(DEFAULT_CAPACITY);
    }

    /**Reheaps a vertex throw the queue using its given index
     * @param rootIndex The given index for the vertex to be reheaped through the queue */
    private void reheap(int rootIndex){
        boolean done = false;
        T orphan = heap[rootIndex];
        int leftChildIndex = 2 * rootIndex;

        while(!done && (leftChildIndex <= lastIndex)){
            int smallerChildIndex = leftChildIndex;
            int rightChildIndex = leftChildIndex + 1;
            if((rightChildIndex <= lastIndex) && (heap[rightChildIndex].compareTo(heap[smallerChildIndex]) < 0)){
                smallerChildIndex = rightChildIndex;
            }
            if(orphan.compareTo(heap[smallerChildIndex]) > 0){
                heap[rootIndex] = heap[smallerChildIndex];
                rootIndex = smallerChildIndex;
                leftChildIndex = 2 * rootIndex;
            }
            done = true;
        }
        heap[rootIndex] = orphan;
    }
    
    /**Adds a new entry to this heap
     * @param newEntry An object to be added. */
    @Override
    public void add(T newEntry) {
        int newIndex = lastIndex + 1;
        int parentIndex = newIndex / 2;
        while((parentIndex > 0) && newEntry.compareTo(heap[parentIndex]) < 0){
            heap[newIndex] = heap[parentIndex];
            newIndex = parentIndex;
            parentIndex = newIndex/2;
        }
        heap[newIndex] = newEntry;
        lastIndex++;
    }

    /**Removes and returns the smallest item in this heap.
     * @return Either the smallest object in the heap or, if the heap
     *      is empty before the operation, null */
    @Override
    public T removeMin() {
        T root = null;

        if(!isEmpty()){
            root = heap[1];
            heap[1] = heap[lastIndex];
            lastIndex--;
            reheap(1);
        }
        return root;
    }

    /**Retrieves the smallest item in this heap
     * @return Either the smallest object in this heap or,
     *      if the heap is empty, null */
    @Override
    public T getMin() {
        T root = null;
        if(!isEmpty()){
            root = heap[1];
        }
        return root;

    }

    /**Gets the size of this heap.
     * @return The number of entries currently in the heap */
    @Override
    public int getSize() {
        return lastIndex;
    }

    /**Removes all entries from this heap. */
    @Override
    public void clear() {
        while(lastIndex > -1){
            heap[lastIndex] = null;
            lastIndex--;
        }
        lastIndex = 0;
    }

    /**Checks if the heap is empty
     * @return True if heap is empty, else false */
    @Override
    public boolean isEmpty() {
        return lastIndex < 1;
    }
    
}
