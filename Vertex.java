//
//      Name:           Beauchamp, Joshua
//      Project:        5
//      Due:            12/08/23
//      Course:         cs-2400-02-f23
//
//      Description:
//                  The vertex class that contains the implementations for the vertex interface
//                  and the inner class for the edges within the graph. It utilizes a list for the
//                  the edges, and iterators for traversing the vertices and edges within the methods
//

import java.util.*;

public class Vertex<T> implements VertexInterface<T> {

    private T label;    //The label for the given vertex
    private List<Edge> edgeList;
    private boolean visited;    //Boolean that keeps track of whether the vertex has been visited
    private VertexInterface<T> previousVertex;
    private double cost;    //The weight of the edge between two vertices
    
    //Vertex constructor
    public Vertex(T vertexLabel){
        label = vertexLabel;
        edgeList = new ArrayList<>();   //Creates the edgeList
        visited = false;
        previousVertex = null;
        cost = 0;
    }   //end constructor

    /**Creates the class for the edge entries within the edge list */
    protected class Edge{
        private VertexInterface<T> vertex;  //Vertex at the end of the edge
        private double weight;

        //Edge constructor with a given vertex and edge weight
        protected Edge(VertexInterface<T> endVertex, double edgeWeight){
            vertex = endVertex;
            weight = edgeWeight;
        }   //end constructor

        //Edge constructor with just a given vertex
        protected Edge(VertexInterface<T> endVertex){
            vertex = endVertex;
            weight = 0;
        }   //end constructor

        /**Retrieves the end vertex
         * @return The end vertex */
        protected VertexInterface<T> getEndVertex(){
            return vertex;
        }

        /**Retrieves the edge weight
         * @return The weight of the edge */
        protected double getWeight(){
            return weight;
        }
    }

    //Helper class that creates an iterator for the neighbors of a vertex
    private class NeighborIterator implements Iterator<VertexInterface<T>> {
        private Iterator<Edge> edges;

        //Default constructor
        private NeighborIterator(){
            edges = edgeList.iterator();
        }   //end default constructor

        /**Checks if the edge has a neighbor 
         * @return true if there is a neighbor, else false*/
        public boolean hasNext(){
            return edges.hasNext();
        }   //end hasNext

        /**Retrieves the vertex's next neighbor
         * @return The next vertex that is the current vertex's neighbors */
        public VertexInterface<T> next(){
            VertexInterface<T> nextNeighbor = null;

            if(edges.hasNext()){
                Edge edgeToNextNeghbor = edges.next();
                nextNeighbor = edgeToNextNeghbor.getEndVertex();
            }
            else{
                throw new NoSuchElementException("Element does not exist");
            }
            return nextNeighbor;
        }   //end next

    }

    @Override
    /**Gets this vertex's label
     * @return The object that labels the vertex */
    public T getLabel() {
        return label;
    }

    @Override
    /**Marks the vertex as visited  */
    public void visit() {
       visited = true;
    }

    @Override
    /**Marks the vertex as unvisited */
    public void unvisit() {
        visited = false;
    }

    @Override
    /**Checks if the vertex is marked as visited
     * @returns True if marked visited, else false */
    public boolean isVisited() {
        return visited;
    }

    @Override
    /**Connects this vertex to a given vertex with a weighted edge.
     * @param endVertex A vertex in the graph that ends the edge
     * @param edgeWeight A double that is teh value of the edge weight, if any
     * @return True if the edge is added, else false */
    public boolean connect(VertexInterface<T> endVertex, double edgeWeight) {
        boolean result = false;

        if(!this.equals(endVertex)){
            Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
            boolean duplicateEdge = false;

            while(!duplicateEdge && neighbors.hasNext()){
                VertexInterface<T> nextNeighbor = neighbors.next();
                if(endVertex.equals(nextNeighbor))
                    duplicateEdge = true;
            }

            if(!duplicateEdge){
                edgeList.add(new Edge(endVertex, edgeWeight));
                result = true;
            }
        }
        return result;
    }

    @Override
    /**Connects this vertex with a given vertex with an unweighted edge
     * @param endVertex a vertex in the graph that ends the edge
     * @return True if the edge is added, else false */
    public boolean connect(VertexInterface<T> endVertex) {
        return connect(endVertex, 0);
    }

    @Override
    /**Creates an iterator of this vertex's neighbors by following all the edges that begin with
     * this vertex
     * @return An iterator of the neighboring vertices of this vertex */
    public Iterator<VertexInterface<T>> getNeighborIterator() {
        return new NeighborIterator();
    }

    /**Returns an iterator object for the weights of the edges within the list
     * @return An iterator object for doubles */
    @Override
    public Iterator<Double> getWeightIterator() {
        List<Double> weights = new ArrayList<>();
        for(Edge edge : edgeList){
            weights.add(edge.getWeight());
        }
        return weights.iterator();
    }

    @Override
    /** Sees whether this vertex has at least one neighbor
     * @return True if the vertex has a neighbor */
    public boolean hasNeighbor() {
        return !edgeList.isEmpty();
    }

    @Override
    /**Gets an unvisited neighbor, if any, of this vertex
     * @return Either a vertex that is an unvisited neighbor or null if no such neighbor exits */
    public VertexInterface<T> getUnvisitedNeighbor() {
        for(Edge edge: edgeList){
            VertexInterface<T> neighbor = edge.getEndVertex();
            if(!neighbor.isVisited()){
                return neighbor;
            }
        }
        return null;
    }

    @Override
    /**Record the previous vertex on a path to this vertex
     * @param predecessor The vertex previous to this one along a path */
    public void setPredecessor(VertexInterface<T> predecessor) {
        previousVertex = predecessor;
    }

    @Override
    /**Gets the recorded predecessor of this vertex
     * @return Either this vertex's predecessor or null if no predecessor was recorded */
    public VertexInterface<T> getPredecessor() {
        return previousVertex;
    }

    @Override
    /**Sees whether a predecessor was recorded for this vertex
     * @return True if a predecessor was recorded */
    public boolean hasPredecessor() {
        return previousVertex != null;
    }

    @Override
    /**Records the cost of a path to this vertex
     * @param newCost The cost of the path */
    public void setCost(double newCost) {
        cost = newCost;
    }

    @Override
    /**Gets the recorded cost of the path to this vertex
     * @return The cost of the path */
    public double getCost() {
        return cost;
    }
    
}
