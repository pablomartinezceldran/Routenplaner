package main.java.models.k2Tree;

import java.util.Arrays;

public class K2Tree {
    public K2Node root;

    // This part is for the implementation where the K2Tree is created while reading the nodes for the first time
    // (more efficient)
    // --------------------------------------------------------
    public K2Tree() {
        this.root = null;
    }

    public void addNode(double[] node) {
        this.root = addNodeRecursive(this.root, node, 0);
    }

    private K2Node addNodeRecursive(K2Node currentNode, double[] newNode, int depth) {
        if (currentNode == null) {
            return new K2Node(newNode);
        }

        int axis = depth % 2;

        if (axis == 0) {
            if (newNode[1] < currentNode.node[1]) {
                currentNode.left = addNodeRecursive(currentNode.left, newNode, depth + 1);
            } else {
                currentNode.right = addNodeRecursive(currentNode.right, newNode, depth + 1);
            }
        } else {
            if (newNode[0] < currentNode.node[0]) {
                currentNode.left = addNodeRecursive(currentNode.left, newNode, depth + 1);
            } else {
                currentNode.right = addNodeRecursive(currentNode.right, newNode, depth + 1);
            }
        }

        return currentNode;
    }
    // --------------------------------------------------------

    public K2Tree(double[][] nodes) {
        this.root = buildKDTree(nodes, 0);
    }

    private K2Node buildKDTree(double[][] nodes, int depth) {
        // base case: no nodes
        if (nodes.length == 0) return null;

        // axis is based on current depth (two dimensions lat & lon)
        int axis = depth % 2;

        // sort nodes based on current axis (0 lon, 1 lat)
        Arrays.sort(nodes, (node1, node2) -> {
            if (axis == 0) {
                return Double.compare(node1[1], node2[1]);
            } else {
                return Double.compare(node1[0], node2[0]);
            }
        });

        // find median node in sorted nodes (optimum point for balancing the tree)
        int median = nodes.length / 2;
        double[] medianNode = nodes[median];

        // recursively build subtrees
        // left smaller ones and right bigger ones based on the axis
        // we increase depth to change sorting criteria
        K2Node node = new K2Node(medianNode);
        node.left = buildKDTree(Arrays.copyOfRange(nodes, 0, median), depth + 1);
        node.right = buildKDTree(Arrays.copyOfRange(nodes, median + 1, nodes.length), depth + 1);

        return node;
    }

    // calculates Euclidean distance between the nodes
    private double calculateDistance(double[] node1, double[] node2) {
        double dx = node1[1] - node2[1];
        double dy = node1[0] - node2[0];
        return Math.sqrt(dx * dx + dy * dy);
    }

    // recursively finds the nearest node to the target node
    private K2Node findNearestNodeRec(K2Node currentNode, double[] target, K2Node bestNode, int depth) {
        // base case: current node is null, return best node found so far
        if (currentNode == null) {
            return bestNode;
        }

        // update the best node if distance to current node is smaller than to the best node
        double targetDistance = calculateDistance(target, currentNode.node);
        double bestNodeDistance = calculateDistance(target, bestNode.node);
        if (targetDistance < bestNodeDistance) bestNode = currentNode;

        // axis is based on current depth (two dimensions lat & lon)
        int axis = depth % 2;

        // recursively explore subtrees based on the axis (0 lon, 1 lat)
        if (axis == 0) {
            // splitting along longitude
            if (target[1] < currentNode.node[1]) {
                // target on the left side of current node, explore left subtree first
                bestNode = findNearestNodeRec(currentNode.left, target, bestNode, depth + 1);

                // check if there is a potential better node on right subtree, explore it as well
                if (target[1] + bestNodeDistance >= currentNode.node[1]) {
                    bestNode = findNearestNodeRec(currentNode.right, target, bestNode, depth + 1);
                }
            } else {
                // target on the right side of current node, explore right subtree first
                bestNode = findNearestNodeRec(currentNode.right, target, bestNode, depth + 1);

                // check if there is a potential better node on left subtree, explore it as well
                if (target[1] - bestNodeDistance <= currentNode.node[1]) {
                    bestNode = findNearestNodeRec(currentNode.left, target, bestNode, depth + 1);
                }
            }
        } else {
            // splitting along latitude
            if (target[0] < currentNode.node[0]) {
                // target below the current node, explore the left subtree first
                bestNode = findNearestNodeRec(currentNode.left, target, bestNode, depth + 1);

                // check if there is a potential better node on the right subtree, explore it as well
                if (target[0] + bestNodeDistance >= currentNode.node[0]) {
                    bestNode = findNearestNodeRec(currentNode.right, target, bestNode, depth + 1);
                }
            } else {
                // target above the current node, explore the right subtree first
                bestNode = findNearestNodeRec(currentNode.right, target, bestNode, depth + 1);

                // check if there is a potential better bode on the left subtree, explore it as well
                if (target[0] - bestNodeDistance <= currentNode.node[0]) {
                    bestNode = findNearestNodeRec(currentNode.left, target, bestNode, depth + 1);
                }
            }
        }

        return bestNode;
    }

    // returns an array with the coordinates of the nearest node of a given longitude and latitude.
    public double[] findNearestNode(double latitude, double longitude) {
        K2Node nearestNode = findNearestNodeRec(root, new double[]{latitude, longitude}, root, 0);
        return new double[]{nearestNode.node[0], nearestNode.node[1], nearestNode.id};
    }
}
