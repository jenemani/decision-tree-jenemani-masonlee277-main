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

    /**
     * @param curData the current iteration of the dataset used to both get the tree when generating
     * and transverse it when getting decision this class interacts mainly with the dataset class
     */
    Node(Dataset curData) {
        this.edgeList = new ArrayList<>();
        this.currentData = curData;
        this.defaultValue = this.currentData.getDefault();
    }

    /**
     * Where the magic happens, this is where the recursion takes place to build the tree. A new node is created,
     * a new split attribute decided, and a new subset of data partitioned. We then store it within a new edge in
     * our edgelist and call getTree on this new node.
     * If the data is ever uniform or empty
     * a new leaf is created and the recursion within the branch ends.
     */
    public ITreeNode getTree() {
        ITreeNode nextNode; //Next node within our tree can be either node or leaf
        String nextAtt = this.currentData.getSplit(); //This is the attribute whose value we'll partition our data on
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
                Edge edge = new Edge(nextNode, s); //Regardless leaf or node we add it to our edge list
                this.edgeList.add(edge);
            } else { //If either attlist or datalist are empty we make a leaf with the data from the previous subset
                nextNode = new Leaf(this.currentData.getDefault());
                Edge edge = new Edge(nextNode, "");
                this.edgeList.add(edge);
            }
        }
        return this;
    }

    /**
     * @param forDatum the datum to lookup a decision for
     * @return the final decision
     *
     * getDecision uses recursion to check the edge of every node within the list if it matches with the test data
     * then we getDecision of the node within that edge, if we ever get to a point where there is no edge value equal
     * to the attribute value of the test data than the defaultvalue of the current node is returned.
     */
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