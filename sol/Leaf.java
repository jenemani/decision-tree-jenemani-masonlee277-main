package sol;

import src.ITreeNode;
import src.Row;
/**
 * This class is used as the base case in recursive calls
 * when we generate the tree the finalDecisions are stored
 * in the fields of leaf which can then be accessed later for
 * prediction
 */

public class Leaf implements ITreeNode {
    public String finalDecision;
    /** @param attribute the targetAttribute value for this leaf */
    Leaf(String attribute ) {
        this.finalDecision = attribute;
    }

    /**
     * @param forDatum the datum to lookup a decision for
     * @return the stored finalDecision which is found using getDecision within Node
     */
    @Override
    public String getDecision(Row forDatum) {
        return this.finalDecision;
    }
}
