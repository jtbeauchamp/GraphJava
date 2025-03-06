//
//      Name:           Beauchamp, Joshua
//      Project:        5
//      Due:            12/08/23
//      Course:         cs-2400-02-f23
//
//      Description:
//                  The BasicGraphInterface describes the basic tasks of a graph, and will be used
//                  by the Digraph class
//

public interface BasicGraphInterface<T>{
    
    /**Adds a given vertex to this graph.
     * @param vertexLabel An object that labels the new vertex and is distinct from the labels of current vertices
     * @return True if the vertex is added, else false */
    public boolean addVertex(T vertexLabel);

    /**Adds a weighted edge between two given distinct vertices that are currently in this graph. 
     * The desired edge must no already be in the graph.
     * @param begin An object that labels the origin vertex of the edge.
     * @param end An object, distinct from begin, that labels the end vertex of the edge
     * @param edgeWeight The real value of the edge's weight.
     * @return True if the edge is added, else false */
    public boolean addEdge(T begin, T end, double edgeWeight);

    /**Adds an unweighted edge between two given distinct vertices that are currently in this graph.
     * The desired edge must not already be in the graph
     * @param begin An object that labels the origin vertex of the edge
     * @param end An object, distinct from begin, that labels the end vertex of the edge
     * @return True if the edge is added, else false */
    public boolean addEdge(T begin, T end);

    /**Sees whether an edge exists between two given vertices.
     * @param begin An object that labels the origin vertex of the edge.
     * @param end An object that labels the end vertex of the edge.
     * @return True if an edge exists */
    public boolean hasEdge(T begin, T end);

    /**Sees whether this graph is empty
     * @return True if the graph is empty */
    public boolean isEmpty();

    /**Gets the number of vertices in this graph
     * @return The number of vertices in the graph */
    public int getNumberOfVertices();

    /**Gets the number of edges in this graph
     * @return The number of edges in the graph */
    public int getNumberOfEdges();

    /**Removes all vertices and edges from this graph, resulting in an empty graph */
    public void clear();
}   //end BasicGraphInterface
