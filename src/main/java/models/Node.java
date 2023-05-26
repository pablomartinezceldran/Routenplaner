package main.java.models;

public class Node {
    int nodeID;

    /*x-Achse*/
    double latitude;

    /*y-Achse*/
    double longitude;


    public Node(int nodeID, double latitude, double longitude){
        this.nodeID=nodeID;
        this.latitude=latitude;
        this.longitude=longitude;
    }

}
/* I would implement it like this:

//auxiliary class for dijkstra algorithm

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

private class Node implements Comparable<Node> {
    int node;
    int distance;

    public Node(int node, int distance) {
        this.node = node;
        this.distance = distance;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.distance, other.distance);
    }
}

    public int findNearestNode(double lon, double lat) {
        double[] distances = new double[graph.length];
        for (int i = 0; i < graph.length; i++) {
            //Compute distance to node
            double distance = Math.sqrt(Math.pow(lon - graph[i][0], 2) + Math.pow(lat - graph[i][1], 2));
            distances[i] = distance;
        }

        // Find node with smallest distance
        int minIndex = 0;
        double minDistance = Double.MAX_VALUE;
        for (int i = 0; i < distances.length; i++) {
            if (distances[i] < minDistance) {
                minIndex = i;
                minDistance = distances[i];
            }
        }

        //Return node with smallest distance
        return minIndex;
    }
}

 */
