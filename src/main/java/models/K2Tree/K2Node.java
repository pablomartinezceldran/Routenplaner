package main.java.models.K2Tree;

import main.java.models.map.Node;

// represents a node of the K2Tree
public class K2Node {
    Node node;
    K2Node left;
    K2Node right;

    public K2Node(Node node) {
        this.node = node;
        this.left = null;
        this.right = null;
    }
}
