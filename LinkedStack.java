//
//      Name:           Beauchamp, Joshua
//      Project:        5
//      Due:            12/08/23
//      Course:         cs-2400-02-f23
//
//      Description:
//                  The LinkedStack contaisn the implementations from the StackInterface.
//                  It has an private inner class for nodes that will be used within the stack.
//

import java.util.EmptyStackException;

public final class LinkedStack<T> implements StackInterface<T> {
    private Node topNode;   //entry on the top of the stack

    //Creation of stack
    public LinkedStack(){
        topNode = null;
    }   //end LinkedStack
    
    //private inner class for nodes
    private class Node{
        private T data;
        private Node next;

        private Node(T dataPortion){
            this(dataPortion, null);
        }   //end constructor

        private Node(T dataPortion, Node nextNode){
            data = dataPortion;
            next = nextNode;
        }   //end constructor
    }   // end Node

    /**Adds an entry on the top of the stack
     * @param newEntry entry being added to the top of the stack */
    public void push(T newEntry){
        Node newNode = new Node(newEntry,topNode);
        topNode = newNode;
    }   //end push

    /**Removes the top entry of the stack
     * @return the entry being removed */
    public T pop(){
        T top = peek();
        topNode = topNode.next;
        return top;
    }   //end pop

    /**Retrieves the entry on the top of the stack
     * @return the entry at the top of the stack */
    public T peek(){
        if(isEmpty()){
            throw new EmptyStackException();
        }
        else{
            return topNode.data;
        }
    }   //end peek

    /**Checks if the stack is empty
     * @return true if the stack is empty, else false */
    public boolean isEmpty(){
        return topNode == null;
    }   //end isEmpty

    /**Removes all entries in the stack */
    public void clear(){
        topNode = null;
    }   //end clear
}   //end LinkedStack implementation
