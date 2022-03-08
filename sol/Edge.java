package sol;

import src.ITreeNode;

public class Edge {
    ITreeNode node; // node that the edge leads to (not the parent node) this is a child
    String edge; // attribute value
    public Edge(ITreeNode node, String edge){
        this.node = node;
        this.edge = edge;
    }

}
