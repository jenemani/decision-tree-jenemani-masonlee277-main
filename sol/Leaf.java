package sol;

import src.ITreeNode;
import src.Row;

public class Leaf implements ITreeNode {

    public String finalDecision;
    // we need to know how to represent edges
    // the edges are the attribute values that either lead to nodes or leafs
    //
    Leaf(String attribute ) {
        this.finalDecision = attribute;
    }

    @Override
    public String getDecision(Row forDatum) {
        return this.finalDecision;
    }
}
