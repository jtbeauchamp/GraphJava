//
//      Name:           Beauchamp, Joshua
//      Project:        5
//      Due:            12/08/23
//      Course:         cs-2400-02-f23
//
//      Description:
//                  The vertex interface class that lists the methods that the vertex class will
//                  implement
//

import java.util.Iterator;

public interface VertexInterface <T> {
    
    /**Gets this vertex's label
     * @return The object that labels the vertex */
    public T getLabel();

    /**Marks the vertex as visited  */
    public void visit();

    /**Marks the vertex as unvisited */
    public void unvisit();
    
    /**Checks if the vertex is marked as visited
     * @returns True if marked visited, else false */
    public boolean isVisited();

    /**Connects this vertex to a given vertex with a weighted edge.
     * @param endVertex A vertex in the graph that ends the edge
     * @param edgeWeight A double that is teh value of the edge weight, if any
     * @return True if the edge is added, else false */
    public boolean connect(VertexInterface<T> endVertex, double edgeWeight);

    /**Connects this vertex with a given vertex with an unweighted edge
     * @param endVertex a vertex in the graph that ends the edge
     * @return True if the edge is added, else false */
    public boolean connect(VertexInterface<T> endVertex);

    /**Creates an iterator of this vertex's neighbors by following all the edges that begin with
     * this vertex
     * @return An iterator of the neighboring vertices of this vertex */
    public Iterator<VertexInterface<T>> getNeighborIterator();

    /** Creates an iterator of the weights of the edges to this vertex's neighbors
     * @return An iterator of the edges to neighbors of the vertex */
    public Iterator<Double> getWeightIterator();

    /** Sees whether this vertex has at least one neighbor
     * @return True if the vertex has a neighbor */
    public boolean hasNeighbor();

    /**Gets an unvisited neighbor, if any, of this vertex
     * @return Either a vertex that is an unvisited neighbor or null if no such neighbor exits
     */
    public VertexInterface<T> getUnvisitedNeighbor();

    /**Record the previous vertex on a path to this vertex
     * @param predecessor The vertex previous to this one along a path */
    public void setPredecessor(VertexInterface<T> predecessor);

    /**Gets the recorded predecessor of this vertex
     * @return Either this vertex's predecessor or null if no predecessor was recorded */
    public VertexInterface<T> getPredecessor();
    
    /**Sees whether a predecessor was recorded for this vertex
     * @return True if a predecessor was recorded */
    public boolean hasPredecessor();

    /**Records the cost of a path to this vertex
     * @param newCost The cost of the path */
    public void setCost(double newCost);

    /**Gets the recorded cost of the path to this vertex
     * @return The cost of the path */
    public double getCost();
}
