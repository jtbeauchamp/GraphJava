//
//      Name:           Beauchamp, Joshua
//      Project:        5
//      Due:            12/08/23
//      Course:         cs-2400-02-f23
//
//      Description:
//                  The StackInterface describes the implementations to be used by the LinkedStack class
//

public interface StackInterface <T> {
    /**Adds an entry on the top of the stack
     * @param newEntry entry being added to the top of the stack */
    public void push(T newEntry);

    /**Removes the top entry of the stack
     * @return the entry being removed */
    public T pop();

    /**Retrieves the entry on the top of the stack
     * @return the entry at the top of the stack */
    public T peek();

    /**Checks if the stack is empty
     * @return true if the stack is empty, else false */
    public boolean isEmpty();

    /**Removes all entries in the stack */
    public void clear();
}
