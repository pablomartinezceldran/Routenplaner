package main.java.models.k2Tree;

// represents a node of the K2Tree
public class K2Node {
    int id;
    final double[] node;
    K2Node left;
    K2Node right;

    public K2Node(double[] node) {
        this.id = (int)node[2];
        this.node = node;
        this.left = null;
        this.right = null;
    }
}
