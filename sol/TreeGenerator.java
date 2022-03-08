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

    /**
     * This method generates our decision tree
     * @param trainingData    the dataset to train on
     * @param targetAttribute the attribute to predict
     */
    @Override
   public void generateTree(Dataset trainingData, String targetAttribute) { // this is just for the root node
        Dataset.setTargetAtt(targetAttribute); //This sets our target attribute for the entire call
        Dataset dataset = new Dataset(trainingData.getAttributeList(),trainingData.getDataObjects());
        dataset.getAttributeList().remove(targetAttribute);
        if(trainingData.size() == 0 || trainingData.getAttributeList().size()==0){
           throw new IndexOutOfBoundsException("You've got no data!");
       }else{
           if(dataset.isUniform()) { // if all samples in dataset have same target att - make a leaf
               this.tree = new Leaf(dataset.getDefault());
           } else{ // if the samples have different targetAtt values then lets make a node
               this.tree= new Node(dataset); // makes a new node with the fields used
               this.tree = ((Node) this.tree).getTree(); //this is the call to recur through the data
           }
       }
    }

    @Override //uses the tree node initialized in generateTree to get a decision
    public String getDecision(Row datum) {
        return this.tree.getDecision(datum);
    }

}
