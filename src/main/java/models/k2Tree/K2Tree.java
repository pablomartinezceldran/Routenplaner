package main.java.models.k2Tree;

import main.java.models.map.Node;

import java.util.List;

public class K2Tree {
    public K2Node root;

    // This part is for the implementation where the K2Tree is created while reading the nodes for the first time
    // (more efficient)
    // --------------------------------------------------------
    public K2Tree() {
        this.root = null;
    }

    public void addNode(Node node) {
        this.root = addNodeRecursive(this.root, node, 0);
    }

    private K2Node addNodeRecursive(K2Node currentNode, Node newNode, int depth) {
        if (currentNode == null) {
            return new K2Node(newNode);
        }

        int axis = depth % 2;

        if (axis == 0) {
            if (newNode.getLongitude() < currentNode.node.getLongitude()) {
                currentNode.left = addNodeRecursive(currentNode.left, newNode, depth + 1);
            } else {
                currentNode.right = addNodeRecursive(currentNode.right, newNode, depth + 1);
            }
        } else {
            if (newNode.getLatitude() < currentNode.node.getLatitude()) {
                currentNode.left = addNodeRecursive(currentNode.left, newNode, depth + 1);
            } else {
                currentNode.right = addNodeRecursive(currentNode.right, newNode, depth + 1);
            }
        }

        return currentNode;
    }
    // --------------------------------------------------------

    public K2Tree(List<Node> nodes) {
        this.root = buildKDTree(nodes, 0);
    }

    private K2Node buildKDTree(List<Node> nodes, int depth) {
        // base case: no nodes
        if (nodes.isEmpty()) return null;

        // axis is based on current depth (two dimensions lat & lon)
        int axis = depth % 2;

        // sort nodes based on current axis (0 lon, 1 lat)
        nodes.sort((node1, node2) -> {
            if (axis == 0) {
                return Double.compare(node1.getLongitude(), node2.getLongitude());
            } else {
                return Double.compare(node1.getLatitude(), node2.getLatitude());
            }
        });

        // find median node in sorted nodes (optimum point for balancing the tree)
        int median = nodes.size() / 2;
        Node medianNode = nodes.get(median);

        // recursively build subtrees
        // left smaller ones and right bigger ones based on the axis
        // we increase depth to change sorting criteria
        K2Node node = new K2Node(medianNode);
        node.left = buildKDTree(nodes.subList(0, median), depth + 1);
        node.right = buildKDTree(nodes.subList(median + 1, nodes.size()), depth + 1);

        return node;
    }

    // calculates Euclidean distance between the nodes
    private double calculateDistance(Node node1, Node node2) {
        double dx = node1.getLongitude() - node2.getLongitude();
        double dy = node1.getLatitude() - node2.getLatitude();
        return Math.sqrt(dx * dx + dy * dy);
    }

    // recursively finds the nearest node to the target node
    private K2Node findNearestNodeRec(K2Node currentNode, Node target, K2Node bestNode, int depth) {
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
            if (target.getLongitude() < currentNode.node.getLongitude()) {
                // target on the left side of current node, explore left subtree first
                bestNode = findNearestNodeRec(currentNode.left, target, bestNode, depth + 1);

                // check if there is a potential better node on right subtree, explore it as well
                if (target.getLongitude() + bestNodeDistance >= currentNode.node.getLongitude()) {
                    bestNode = findNearestNodeRec(currentNode.right, target, bestNode, depth + 1);
                }
            } else {
                // target on the right side of current node, explore right subtree first
                bestNode = findNearestNodeRec(currentNode.right, target, bestNode, depth + 1);

                // check if there is a potential better node on left subtree, explore it as well
                if (target.getLongitude() - bestNodeDistance <= currentNode.node.getLongitude()) {
                    bestNode = findNearestNodeRec(currentNode.left, target, bestNode, depth + 1);
                }
            }
        } else {
            // splitting along latitude
            if (target.getLatitude() < currentNode.node.getLatitude()) {
                // target below the current node, explore the left subtree first
                bestNode = findNearestNodeRec(currentNode.left, target, bestNode, depth + 1);

                // check if there is a potential better node on the right subtree, explore it as well
                if (target.getLatitude() + bestNodeDistance >= currentNode.node.getLatitude()) {
                    bestNode = findNearestNodeRec(currentNode.right, target, bestNode, depth + 1);
                }
            } else {
                // target above the current node, explore the right subtree first
                bestNode = findNearestNodeRec(currentNode.right, target, bestNode, depth + 1);

                // check if there is a potential better bode on the left subtree, explore it as well
                if (target.getLatitude() - bestNodeDistance <= currentNode.node.getLatitude()) {
                    bestNode = findNearestNodeRec(currentNode.left, target, bestNode, depth + 1);
                }
            }
        }

        return bestNode;
    }

    // returns an array with the coordinates of the nearest node of a given longitude and latitude.
    // NOTE: double loses information and with exponential notation.
    public double[] findNearestNode(double latitude, double longitude) {
        long lat = Long.parseLong(String.valueOf(latitude).replace(".",""));
        long lon = Long.parseLong(String.valueOf(longitude).replace(".",""));
        lat = checkLatitude(lat);
        lon = checkLongitude(lon);
        K2Node nearestNode = findNearestNodeRec(root, new Node(-1, lat, lon), root, 0);
        return new double[]{nearestNode.node.getLatitude(), nearestNode.node.getLongitude()};
    }

    // as we use long type to save longitude, we have to ensure it has the same digits
    private long checkLongitude(long number) {
        String numberString = Long.toString(number);
        int digitCount = numberString.length();
        // there are longitude numbers with 18 and 19 digits
        int zerosToAdd = (numberString.startsWith("1")) ? 19 - digitCount : 18 - digitCount;

        if (zerosToAdd > 0) {
            for (int i = 0; i < zerosToAdd; i++) {
                number *= 10;
            }
        }
        return number;
    }

    // as we use long type to save latitude, we have to ensure it has the same digits
    private long checkLatitude(long number) {
        int digitCount = (int) Math.log10(Math.abs(number)) + 1;
        int zerosToAdd = 19 - digitCount;
        if (zerosToAdd > 0) {
            for (int i = 0; i < zerosToAdd; i++) {
                number *= 10;
            }
        }
        return number;
    }
}
