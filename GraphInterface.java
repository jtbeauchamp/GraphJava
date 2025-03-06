//
//      Name:           Beauchamp, Joshua
//      Project:        5
//      Due:            12/08/23
//      Course:         cs-2400-02-f23
//
//      Description:
//                  The GraphInterface extends both graph interfaces into one to be used by
//                  the Digraph class
//

public interface GraphInterface<T extends Comparable<? super T>> extends BasicGraphInterface<T>, GraphAlgorithmsInterface<T> {
    
}
