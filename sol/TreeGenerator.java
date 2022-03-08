package sol;

import src.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A class that implements the ITreeGenerator interface
 * used to generate a tree
 */
public class TreeGenerator implements ITreeGenerator<Dataset> {
    ITreeNode tree;

   public void generateTree(Dataset trainingData, String targetAttribute) { // this is just for the root node
       Dataset.setTargetAtt(targetAttribute);
       Dataset dataset = new Dataset(trainingData.getAttributeList(),trainingData.getDataObjects());
       dataset.getAttributeList().remove(targetAttribute);

       if(dataset.isUniform()) { // if all samples in dataset have same target att - make a leaf
           this.tree = new Leaf(dataset.getDefault());
       } else{ // if the samples have different targetAtt values then lets make a node
           this.tree= new Node(dataset); // makes a new node with the fields used
           this.tree = ((Node) this.tree).getTree(); // happens within the node class
       }
    }

    @Override
    public String getDecision(Row datum) {
        return this.tree.getDecision(datum);
    }

}
