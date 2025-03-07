//
//      Name:           Beauchamp, Joshua
//      Project:        5
//      Due:            12/08/23
//      Course:         cs-2400-02-f23
//
//      Description:
//                  The GraphAlgorithmsInterface describes all the implementations that the
//                  Digraph class will use alongside the BasicGraphInterface
//

public interface GraphAlgorithmsInterface<T extends Comparable<? super T>> {
    
    /**Performs a breadth-first traversal of this graph.
     * @param origin An object that labels the origin vertex of the traversal.
     * @return A queue of labels of the vertices in the traversal, with 
     *          the label of the origin vertex at the queue's front. */
    public PriorityQueueInterface<T> getBreadthFirstTraversal(T origin);

    /**Performs a depth-first traversal of this graph.
     * @param origin An object that labels the origin vertex of the traversal.
     * @return A queue of labels of the vertices in the traversal, with the
     *          label of the origin vertex at the queue's front */
    public PriorityQueueInterface<T> getDepthFIrstTraversal(T origin);

    /**Performs a topologocial sort of the vertices in this graph without cycles.
     * @return A stack of vertex labels in opological order, begininning with the stack's top*/
    public StackInterface<T> getTopologicalOrder();

    /**Finds the shortest-length path between two given vertcies in this graph.
     * @param begin An object that labels the path's origin vertex.
     * @param end An object that labels the path's destination vertex
     * @param path A stack of labels that is empty initially;
     *      at the completion of the method, this stack contains the labels
     *      of the vertices along the shortest path;
     *      the label of the origin vertex is at the top, and the label
     *      of the destination vertex is at the bottom
     * @return The length of the shortest path */
    public int getShortestPath(T begin, T end, StackInterface<T> path);

    /**Finds the least-cost path between two given vertices in this graph.
     * @param begin An object that labels the path's origin vertex.
     * @param end An object that labels the path's destination vertex.
     * @param path A stack of labels that is empty initially;
     *      at the completion of tghe method, this stack contains
     *      the labels of the vertices along the cheapest path;
     *      the label of the origin vertex is at the top, and
     *      the label of the destination vertex is at the bottom
     * @return The cost of the cheapest path. */
    public double getCheapestPath(T begin, T end, StackInterface<T> path);
}
