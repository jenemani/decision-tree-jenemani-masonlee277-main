package sol;

import src.Row;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node implements ITreeNode{
        public List<String> attList; //List of Attributes that can still be used
        public ArrayList<Edge> edgeList; // this is the children for a node
        public String defVal; // default value
        public static String TARGET_ATT;
        public Dataset curData; // the subset of data that this node implements
        public String attSplit; // if edge is null you're at the root node

//Take data set and random splitAtt, parse data into lists of each individual splitAtt,
    Node(List<String> attList, String targetAtt, Dataset curData) {
        this.attList = attList;
        TARGET_ATT = targetAtt;
        this.curData = curData;
    }

    Node(List<String> attList, Dataset curData) {
        this.defVal = null;
        this.attList = attList;
        this.edgeList = new ArrayList<>();
        this.curData = curData;
    }

    public ArrayList<String> getEdges(){
        return this.curData.getPosAttVals(this.attList.get(0));
    }

    public void removeSelf(){
        this.attList.remove(0);
    }

    public ITreeNode getTree(){
        ITreeNode nextNode;
        String nextAtt = this.attList.get(0);
        this.getDefaultVal();
        for (String s : this.getEdges()) { // iterate over the possible attVals in string form
            Dataset subsetData = this.curData.getSubset(nextAtt,s);
            if (subsetData.sameValue(TARGET_ATT)) { //if all target attribute values are the same we make a leaf
                nextNode = new Leaf(subsetData.getPosAttVals(TARGET_ATT).get(0));
                ((Node)nextNode).removeSelf();

            } else { //If all attribute values are not the same we make a node
                nextNode = new Node(this.attList,subsetData);
                ((Node)nextNode).removeSelf();
                ((Node) nextNode).getTree();
                //new Node off this Edge
            }
            Edge edge = new Edge(nextNode, s);
            this.edgeList.add(edge);

        }
        return this;
    }


    @Override
    public String getDecision(Row forDatum) {
        return  this.transverse(forDatum, this);
    } // end Decision

    public String transverse(Row forDatum, ITreeNode tree) {
        // we are at the root:
        // we see the edge list and
        for (Edge e : ((Node) tree).edgeList) {
            if (Objects.equals(e.name, forDatum.getAttributeValue(((Node) tree).attSplit))) { // if the attVal == attVal for same atttribute in Datum
                if (e.node instanceof Leaf) {return ((Leaf) e.node).finalDecision;}
                else { // if its a node -- we need to call getDecision on this smaller tree, as
                    this.transverse(forDatum, e.node); // call transverse with this new node (down the tree one step)
                }
            } // if there is not edge that represents the attValue then we know we are seeing a novel case,
              // in that instance we return the defaultValue -- we will have to exit the for loop tho
        } // end of for loop
        return this.defVal; // will break here if we iterate over for loop and find no valid edges to categorize our data
    }

    public void getDefaultVal() {
        // need data list and the target att.
        // we need to know the amount of values for the attribute
        // create a
        this.defVal = this.curData.getDefault(TARGET_ATT);
    }
}