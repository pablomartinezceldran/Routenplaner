package main.java.models.k2Tree;

// represents a node of the K2Tree
public class K2Node {
    final double[] node;
    K2Node left;
    K2Node right;

    public K2Node(double[] node) {
        this.node = node;
        this.left = null;
        this.right = null;
    }
}
