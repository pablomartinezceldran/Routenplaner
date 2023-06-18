package main.java.models.KDTree;

import main.java.models.map.Node;

public class KDNode {
    Node node;
    KDNode left;
    KDNode right;

    public KDNode(Node node) {
        this.node = node;
        this.left = null;
        this.right = null;
    }
}
