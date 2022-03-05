package sol;

public class Edge {
    ITreeNode node; // node that the edge leads to (not the parent node) this is a child
    String name; // attribute vale
    public Edge(ITreeNode node, String edge){
        this.node = node;
        this.name = edge;

    }

}
