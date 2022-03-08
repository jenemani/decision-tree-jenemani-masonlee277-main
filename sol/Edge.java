package sol;
import src.ITreeNode;

/**
 * This class handles the edges and serves to connect nodes to nodes
 * and nodes to leaves
 * these edges are stored within the passed node within our node class
 */

public class Edge {
    ITreeNode node; // node that the edge leads to (not the parent node) this is a child
    String edge; // attribute value

    public Edge(ITreeNode node, String edge){
        this.node = node;
        this.edge = edge;
    }
}
