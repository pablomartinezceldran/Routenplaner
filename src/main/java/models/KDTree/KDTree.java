package main.java.models.KDTree;

import main.java.models.map.Node;

import java.util.List;

public class KDTree {
    public KDNode root;

    public KDTree() {
        this.root = null;
    }

    public void addNode(Node node) {
        this.root = addNodeRecursive(this.root, node, 0);
    }

    private KDNode addNodeRecursive(KDNode currentNode, Node newNode, int depth) {
        if (currentNode == null) {
            return new KDNode(newNode);
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

    public KDTree(List<Node> nodes) {
        this.root = buildKDTree(nodes, 0);
    }

    private KDNode buildKDTree(List<Node> nodes, int depth) {
        if (nodes.isEmpty()) return null;

        int axis = depth % 2;
        nodes.sort((node1, node2) -> {
            if (axis == 0) {
                return Double.compare(node1.getLongitude(), node2.getLongitude());
            } else {
                return Double.compare(node1.getLatitude(), node2.getLatitude());
            }
        });

        int median = nodes.size() / 2;
        Node medianNode = nodes.get(median);

        KDNode node = new KDNode(medianNode);
        node.left = buildKDTree(nodes.subList(0, median), depth + 1);
        node.right = buildKDTree(nodes.subList(median + 1, nodes.size()), depth + 1);

        return node;
    }

    private double calculateDistance(Node node1, Node node2) {
        double dx = node1.getLongitude() - node2.getLongitude();
        double dy = node1.getLatitude() - node2.getLatitude();
        return Math.sqrt(dx * dx + dy * dy);
    }

    private KDNode findNearestNode(KDNode currentNode, Node target, KDNode bestNode, int depth) {
        if (currentNode == null) {
            return bestNode;
        }

        double targetDistance = calculateDistance(target, currentNode.node);
        double bestNodeDistance = calculateDistance(target, bestNode.node);

        if (targetDistance < bestNodeDistance) {
            bestNode = currentNode;
        }

        int axis = depth % 2;

        if (axis == 0) {
            if (target.getLongitude() < currentNode.node.getLongitude()) {
                bestNode = findNearestNode(currentNode.left, target, bestNode, depth + 1);

                if (target.getLongitude() + bestNodeDistance >= currentNode.node.getLongitude()) {
                    bestNode = findNearestNode(currentNode.right, target, bestNode, depth + 1);
                }
            } else {
                bestNode = findNearestNode(currentNode.right, target, bestNode, depth + 1);

                if (target.getLongitude() - bestNodeDistance <= currentNode.node.getLongitude()) {
                    bestNode = findNearestNode(currentNode.left, target, bestNode, depth + 1);
                }
            }
        } else {
            if (target.getLatitude() < currentNode.node.getLatitude()) {
                bestNode = findNearestNode(currentNode.left, target, bestNode, depth + 1);

                if (target.getLatitude() + bestNodeDistance >= currentNode.node.getLatitude()) {
                    bestNode = findNearestNode(currentNode.right, target, bestNode, depth + 1);
                }
            } else {
                bestNode = findNearestNode(currentNode.right, target, bestNode, depth + 1);

                if (target.getLatitude() - bestNodeDistance <= currentNode.node.getLatitude()) {
                    bestNode = findNearestNode(currentNode.left, target, bestNode, depth + 1);
                }
            }
        }

        return bestNode;
    }

    public Node findNearestNode(long latitude, long longitude) {
        KDNode nearestNode = findNearestNode(root, new Node(0, latitude, longitude), root, 0);
        return nearestNode.node;
    }
}
