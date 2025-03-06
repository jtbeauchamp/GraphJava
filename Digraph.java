//
//      Name:           Beauchamp, Joshua
//      Project:        5
//      Due:            12/08/23
//      Course:         cs-2400-02-f23
//
//      Description:
//                  The Digraph class that contains the implementations of the GraphInterface. It has
//                  private helper methods that assist with the implementations and includes a private
//                  inner class for the entries to be added within the priority queue.
//

import java.util.Iterator;

public class Digraph<T extends Comparable <? super T>> implements GraphInterface<T>{

    private DictionaryInterface<T, VertexInterface<T>> vertices;
    private int edgeCount;

    //Default constructor
    public Digraph(){
        vertices = new HashedDictionary<>();
        edgeCount = 0;
    }   //end default constructor

    //Resets the vertices for the next traversal
    protected void resetVertices(){
        Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();
        while(vertexIterator.hasNext()){
            VertexInterface<T> nextVertex = vertexIterator.next();
            nextVertex.unvisit();
            nextVertex.setCost(0);
            nextVertex.setPredecessor(null);
        }
    }   //end resetVertices

    //Private class for the entries that will be enqueued into the priority queue later
    private class EntryPQ implements Comparable<EntryPQ>{
        private VertexInterface<T> vertex;
        private double cost;
        private VertexInterface<T> predecessor;

        //Creates an entry with a vertex, a cost, and its predecessor
        public EntryPQ(VertexInterface<T> vertex, double cost, VertexInterface<T> predecessor){
            this.vertex = vertex;
            this.cost = cost;
            this.predecessor = predecessor;
        }

        public VertexInterface<T> getVertex(){
            return vertex;
        }

        public double getCost(){
            return cost;
        }

        public VertexInterface<T> getPredecessor(){
            return predecessor;
        }

        public int compareTo(EntryPQ other){
            return Double.compare(this.cost, other.cost);
        }
    }

    /**Adds a given vertex to this graph.
     * @param vertexLabel An object that labels the new vertex and is distinct from the labels of current vertices
     * @return True if the vertex is added, else false */
    @Override
    public boolean addVertex(T vertexLabel) {
        VertexInterface<T> addOutcome = new Vertex(vertexLabel);
        if(vertices.getValue(vertexLabel) == null){
            addOutcome = vertices.add(vertexLabel, new Vertex<>(vertexLabel));
        }
        return addOutcome == null;
    }   //end addVertex

    /**Adds a weighted edge between two given distinct vertices that are currently in this graph. 
     * The desired edge must no already be in the graph.
     * @param begin An object that labels the origin vertex of the edge.
     * @param end An object, distinct from begin, that labels the end vertex of the edge
     * @param edgeWeight The real value of the edge's weight.
     * @return True if the edge is added, else false */
    @Override
    public boolean addEdge(T begin, T end, double edgeWeight) {
        boolean result = false;
        VertexInterface<T> beginVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        if((beginVertex != null) && (endVertex != null)){
            result = beginVertex.connect(endVertex, edgeWeight);
        }
        if(result){
            edgeCount++;
        }
        return result;
    }   //end addEdge

    /**Adds an unweighted edge between two given distinct vertices that are currently in this graph.
     * The desired edge must not already be in the graph
     * @param begin An object that labels the origin vertex of the edge
     * @param end An object, distinct from begin, that labels the end vertex of the edge
     * @return True if the edge is added, else false */
    @Override
    public boolean addEdge(T begin, T end) {
        return addEdge(begin, end);
    }   //end addEdge

    /**Sees whether an edge exists between two given vertices.
     * @param begin An object that labels the origin vertex of the edge.
     * @param end An object that labels the end vertex of the edge.
     * @return True if an edge exists */
    @Override
    public boolean hasEdge(T begin, T end) {
        boolean found = false;
        VertexInterface<T> beginVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        if((beginVertex != null) && (endVertex != null)){   //checks to make sure the edges exist
            Iterator<VertexInterface<T>> neighbors = beginVertex.getNeighborIterator();
            while(!found && neighbors.hasNext()){   //Iterates through the neighbors of the beginning vertex
                VertexInterface<T> nextNeighbor = neighbors.next();
                if(endVertex.equals(nextNeighbor))  //stops the operation if the next neighbor is the destination vertex
                    found = true;
            }
        }
        return found;
    }   //end hasEdge

    /**Sees whether this graph is empty
     * @return True if the graph is empty */
    @Override
    public boolean isEmpty() {
        return vertices.isEmpty();
    }   //end isEmpty

    /**Gets the number of vertices in this graph
     * @return The number of vertices in the graph */
    @Override
    public int getNumberOfVertices() {
        return vertices.getSize();
    }   //end getNumberOfVertices

    /**Gets the number of edges in this graph
     * @return The number of edges in the graph */
    @Override
    public int getNumberOfEdges() {
        return edgeCount;
    }   //end getNumberOfEdges

    /**Removes all vertices and edges from this graph, resulting in an empty graph */
    @Override
    public void clear() {
        vertices.clear();
        edgeCount = 0;
    }   //end clear

    /**Finds the least-cost path between two given vertices in this graph.
     * @param begin An object that labels the path's origin vertex.
     * @param end An object that labels the path's destination vertex.
     * @param path A stack of labels that is empty initially;
     *      at the completion of tghe method, this stack contains
     *      the labels of the vertices along the cheapest path;
     *      the label of the origin vertex is at the top, and
     *      the label of the destination vertex is at the bottom
     * @return The cost of the cheapest path. */
    @Override
    public double getCheapestPath(T begin, T end, StackInterface<T> path) {
        resetVertices();
        boolean done = false;
        PriorityQueue<EntryPQ> priorityQueue = new PriorityQueue<>();   //Creates a priority queue
        VertexInterface<T> originVertex = vertices.getValue(begin);     //Creates the starting vertex
        VertexInterface<T> endVertex = vertices.getValue(end);      //Creates the destination vertez
        priorityQueue.add(new EntryPQ(originVertex, 0, null));  //Creates new vertex entry for queue

        while(!done && !priorityQueue.isEmpty()){
            EntryPQ frontEntry = priorityQueue.remove();    
            VertexInterface<T> frontVertex = frontEntry.getVertex();

            if(!frontVertex.isVisited()){   //Instantiates the vertex if it has not been visited
                frontVertex.visit();
                frontVertex.setCost(frontEntry.getCost());
                frontVertex.setPredecessor(frontEntry.getPredecessor());

                if(frontVertex.equals(endVertex)){  //ends the operation if the current vertex is the destination vertex
                    done = true;
                }
                else{   //Iterates through edges and vertexes to create the next vertex with an edge weight
                    Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();
                    Iterator<Double> edgeWeights = frontVertex.getWeightIterator();

                    while(neighbors.hasNext()){
                        VertexInterface<T> nextNeighbor = neighbors.next();
                        Double weightOfEdgeToNeighbor = edgeWeights.next();

                        if(!nextNeighbor.isVisited()){
                            double nextCost = weightOfEdgeToNeighbor + frontVertex.getCost();   //increases the cost of the path
                            priorityQueue.add(new EntryPQ(nextNeighbor, nextCost, frontVertex));    //enques the next vertex to the current front vertex
                        }
                    }
                }
            }
        }
        double pathCost = endVertex.getCost();
        path.push(endVertex.getLabel());    //Pushes the path to take onto the stack
        VertexInterface<T> vertex = endVertex;
        while(vertex.hasPredecessor()){     //Pushes vertex labels onto stack until the path is complete
            vertex = vertex.getPredecessor();
            path.push(vertex.getLabel());
        }
        return pathCost;
    }   //end getCheapestPath

    /**Performs a breadth-first traversal of this graph.
     * @param origin An object that labels the origin vertex of the traversal.
     * @return A queue of labels of the vertices in the traversal, with 
     *          the label of the origin vertex at the queue's front. */
    @Override
    public PriorityQueueInterface<T> getBreadthFirstTraversal(T origin) {
        throw new UnsupportedOperationException("Unimplemented method 'getDepthFIrstTraversal'");
    }

    /**Performs a depth-first traversal of this graph.
     * @param origin An object that labels the origin vertex of the traversal.
     * @return A queue of labels of the vertices in the traversal, with the
     *          label of the origin vertex at the queue's front */
    @Override
    public PriorityQueueInterface<T> getDepthFIrstTraversal(T origin) {
        throw new UnsupportedOperationException("Unimplemented method 'getDepthFIrstTraversal'");
    }

    /**Performs a topologocial sort of the vertices in this graph without cycles.
     * @return A stack of vertex labels in opological order, begininning with the stack's top*/
    @Override
    public StackInterface<T> getTopologicalOrder() {
        throw new UnsupportedOperationException("Unimplemented method 'getTopologicalOrder'");
    }

    /**Finds the shortest-length path between two given vertcies in this graph.
     * @param begin An object that labels the path's origin vertex.
     * @param end An object that labels the path's destination vertex
     * @param path A stack of labels that is empty initially;
     *      at the completion of the method, this stack contains the labels
     *      of the vertices along the shortest path;
     *      the label of the origin vertex is at the top, and the label
     *      of the destination vertex is at the bottom
     * @return The length of the shortest path */
    @Override
    public int getShortestPath(T begin, T end, StackInterface<T> path) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCheapesPath'");
    } 
}