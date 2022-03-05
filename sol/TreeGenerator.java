package sol;

import src.DecisionTreeCSVParser;
import src.IDataset;
import src.ITreeGenerator;
import src.Row;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A class that implements the ITreeGenerator interface
 * used to generate a tree
 */
public class TreeGenerator implements ITreeGenerator<Dataset> {
    List<String> atts;
    String  randAtt;
    String targetAtt;
    List<String> attList;
    Dataset dataset;

    ITreeNode tree;

   public void generateTree(Dataset trainingData, String targetAttribute) { // this is just for the root node
       this.dataset = trainingData; // passed in
       this.attList = new ArrayList<>(); // initlizes to empty
       this.atts = this.dataset.getAttributeList(); //
       this.targetAtt =targetAttribute;
       this.atts.remove(this.targetAtt); // this removes the target attribute
       this.splitList(); // this should update the attList field with a random order
            // sameValue returning true because every value in dataSet is returning true
       if(this.dataset.sameValue(this.targetAtt)) { // if all samples in dataset have same target att - make a leaf
           this.tree = new Leaf(this.dataset.leafValue(this.targetAtt));
       } else{ // if the samples have different targetAtt values then lets make a node
           this.tree= new Node(this.attList,this.targetAtt,this.dataset); // makes a new node with the fields used
           ((Node) this.tree).getTree(); // happens within the node class
       }
    }


    @Override
    public String getDecision(Row datum) {
        return this.tree.getDecision(datum);
    }
    // TODO: Implement methods declared in ITreeGenerator interface!

    public String getRandomAtt(List<String> atts) {
       int min = 1;
       int max = atts.size();
       int random = (int) ((Math.random() * (max - min)) + min);
       return atts.get(random-1);
    }

    public void splitList(){
        while(this.atts.size() > 0){
            this.randAtt = this.getRandomAtt(this.atts);
            this.atts.remove(this.randAtt);
            this.attList.add(this.randAtt);
        }
    }
}
