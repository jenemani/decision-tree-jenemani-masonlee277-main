package sol;

import src.Row;

/**
 * Interface for nodes and leaves of tree.
 *
 * Feel free to add more methods to this interface as you see fit!
 */
public interface ITreeNode {

    /**
     * Recursively traverses decision tree to return tree's decision for a row.
     *
     * @param forDatum the datum to lookup a decision for
     * @return the decision tree's decision
     */
    String getDecision(Row forDatum);
}
