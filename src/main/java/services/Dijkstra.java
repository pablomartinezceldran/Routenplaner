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
}