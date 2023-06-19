package main.java.services;

import main.java.models.map.Edge;
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
        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.comparingInt(nodeID -> distances[nodeID]));

        // add source node to queue and set its distance to 0
        queue.add(sourceID);
        distances[sourceID] = 0;

        while (!queue.isEmpty()) {
            int currentNodeID = queue.poll();
            int currentDistance = distances[currentNodeID];

            // target reached, then return current distance
            if (currentNodeID == targetID) return currentDistance;

            // current distance greater than stored distance, move on to next node
            if (currentDistance > distances[currentNodeID]) continue;

            // get neighbours of current node
            ArrayList<Edge> neighbors = mapGraph.getAdjacentEdges(currentNodeID);
            for (Edge neighbor : neighbors) {
                int neighborID = neighbor.getTarget();
                int newDistance = currentDistance + neighbor.getCost();

                // update distance if shorter path to the neighbour is found
                if (newDistance < distances[neighborID]) {
                    distances[neighborID] = newDistance;
                    queue.add(neighborID);
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
        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.comparingInt(nodeID -> distances[nodeID]));

        // add source node to queue and set its distance to 0
        queue.add(sourceID);
        distances[sourceID] = 0;

        while (!queue.isEmpty()) {
            int currentNodeID = queue.poll();
            int currentDistance = distances[currentNodeID];

            // current distance greater than stored distance, move on to next node
            if (currentDistance > distances[currentNodeID]) continue;

            // get neighbours of current node
            ArrayList<Edge> neighbors = mapGraph.getAdjacentEdges(currentNodeID);
            for (Edge neighbor : neighbors) {
                int neighborID = neighbor.getTarget();
                int newDistance = currentDistance + neighbor.getCost();

                // update distance if shorter path to the neighbour is found
                if (newDistance < distances[neighborID]) {
                    distances[neighborID] = newDistance;
                    queue.add(neighborID);
                }
            }
        }
        return distances;
    }
}