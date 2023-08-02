package main.java.services;

import main.java.models.map.MapGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Dijkstra {

    // returns the distance between two existing nodes on the map
    public static int oneToOne(MapGraph mapGraph, int sourceID, int targetID) {
        // initialization distances (set to *infinite*)
        int numNodes = mapGraph.getNumNodes();
        int[] distances = new int[numNodes];
        Arrays.fill(distances, Integer.MAX_VALUE);

        // initialization queue
        // Comparator is used to specify the comparison criteria between the elements in the queue.
        // In our case, it compares the distances of the distances array.
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(node -> node[1]));

        // add source node to queue and set its distance to 0
        queue.add(new int[]{sourceID, 0});
        distances[sourceID] = 0;

        while (!queue.isEmpty()) {
            int[] currentNode = queue.poll();
            int currentNodeID = currentNode[0];
            int currentDistance = currentNode[1];

            // target reached, then return current distance
            if (currentNodeID == targetID) return currentDistance;

            // current distance greater than stored distance, move on to next node
            if (currentDistance > distances[currentNodeID]) continue;

            // get neighbours of current node
            ArrayList<int[]> neighbors = mapGraph.getAdjacentEdges(currentNodeID);
            for (int[] neighbor : neighbors) {
                int neighborID = neighbor[0];
                int newDistance = currentDistance + neighbor[1];

                // update distance if shorter path to the neighbour is found
                if (newDistance < distances[neighborID]) {
                    distances[neighborID] = newDistance;
                    queue.add(new int[]{neighborID, newDistance});
                }
            }
        }
        return -1;
    }

    // returns an array with distances from an existing source node to all nodes in the graph
    public static int[] oneToAll(MapGraph mapGraph, int sourceID) {
        // initialization distances (set to *infinite*)
        int numNodes = mapGraph.getNumNodes();
        int[] distances = new int[numNodes];
        Arrays.fill(distances, Integer.MAX_VALUE);

        // initialization queue. Comparator same as above
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(node -> node[1]));

        // add source node to queue and set its distance to 0
        queue.add(new int[]{sourceID, 0});
        distances[sourceID] = 0;

        while (!queue.isEmpty()) {
            int[] currentNode = queue.poll();
            int currentNodeID = currentNode[0];
            int currentDistance = currentNode[1];

            // current distance greater than stored distance, move on to next node
            if (currentDistance > distances[currentNodeID]) continue;

            // get neighbours of current node
            ArrayList<int[]> neighbors = mapGraph.getAdjacentEdges(currentNodeID);
            for (int[] neighbor : neighbors) {
                int neighborID = neighbor[0];
                int newDistance = currentDistance + neighbor[1];

                // update distance if shorter path to the neighbour is found
                if (newDistance < distances[neighborID]) {
                    distances[neighborID] = newDistance;
                    queue.add(new int[]{neighborID, newDistance});
                }
            }
        }
        return distances;
    }

    public static double[][] oneToOneNew(MapGraph mapGraph, int sourceID, int targetID) {
        // initialization distances (set to *infinite*)
        int numNodes = mapGraph.getNumNodes();
        int[] distances = new int[numNodes];
        Arrays.fill(distances, Integer.MAX_VALUE);

        // initialization queue
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(node -> node[1]));

        // add source node to queue and set its distance to 0
        queue.add(new int[]{sourceID, 0});
        distances[sourceID] = 0;

        // parent array to store the previous node in the shortest path
        int[] parent = new int[numNodes];
        Arrays.fill(parent, -1);

        while (!queue.isEmpty()) {
            int[] currentNode = queue.poll();
            int currentNodeID = currentNode[0];
            int currentDistance = currentNode[1];

            // target reached, then reconstruct the path and return
            if (currentNodeID == targetID) {
                return reconstructPath(mapGraph, parent, sourceID, targetID);
            }

            // current distance greater than stored distance, move on to next node
            if (currentDistance > distances[currentNodeID]) continue;

            // get neighbours of current node
            ArrayList<int[]> neighbors = mapGraph.getAdjacentEdges(currentNodeID);
            for (int[] neighbor : neighbors) {
                int neighborID = neighbor[0];
                int newDistance = currentDistance + neighbor[1];

                // update distance if shorter path to the neighbour is found
                if (newDistance < distances[neighborID]) {
                    distances[neighborID] = newDistance;
                    parent[neighborID] = currentNodeID;
                    queue.add(new int[]{neighborID, newDistance});
                }
            }
        }

        // If no path is found, return null
        return null;
    }

    private static double[][] reconstructPath(MapGraph mapGraph, int[] parent, int sourceID, int targetID) {
        ArrayList<double[]> pathNodes = new ArrayList<>();
        int currentNodeID = targetID;
        while (currentNodeID != sourceID) {
            double[] nodeProperties = mapGraph.getNodeProperties(currentNodeID);
            pathNodes.add(0, nodeProperties);
            currentNodeID = parent[currentNodeID];
        }

        pathNodes.add(0, mapGraph.getNodeProperties(sourceID));

        double[][] path = new double[pathNodes.size()][2];
        for (int i = 0; i < pathNodes.size(); i++) {
            path[i] = pathNodes.get(i);
        }
        return path;
    }

}