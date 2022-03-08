package sol;

import src.ITreeNode;
import src.Row;

import java.util.ArrayList;
import java.util.List;

public class Node implements ITreeNode {
        public List<Edge> edgeList; //this is the children for a node
        public String defaultValue;
        public Dataset currentData; // the subset of data that this node implements
        public String nodeValue;

    Node(Dataset curData) {
        this.edgeList = new ArrayList<>();
        this.currentData = curData;
        this.defaultValue = this.currentData.getDefault();
    }

    public ITreeNode getTree() {
        ITreeNode nextNode;
        String nextAtt = this.currentData.getSplit();
        this.nodeValue = nextAtt;
        for (String s : this.currentData.getEdges(nextAtt)) { // iterate over the possible attVals in string form
            Dataset subsetData = this.currentData.getSubset(nextAtt, s);
            if (subsetData.size() > 0 && subsetData.attList.size() > 0) {
                if (subsetData.isUniform()) { //if all target attribute values are the same we make a leaf
                    nextNode = new Leaf(subsetData.getDefault());
                } else {
                    //If all attribute values are not the same we make a node
                    nextNode = new Node(subsetData);
                    ((Node) nextNode).getTree();
                    //new Node off this Edge
                }
                Edge edge = new Edge(nextNode, s);
                this.edgeList.add(edge);
            } else {
                nextNode = new Leaf(this.currentData.getDefault());
                Edge edge = new Edge(nextNode, "");
                this.edgeList.add(edge);
            }
        }
        return this;
    }

    @Override
    public String getDecision(Row forDatum) {
        for (Edge e : this.edgeList) {
            if(e.edge.equals(forDatum.getAttributeValue(this.nodeValue))){
                return e.node.getDecision(forDatum);
            }
        }
        return this.defaultValue;
    } // end Decision


}