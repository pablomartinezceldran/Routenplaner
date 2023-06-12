package main.java.models;
import java.io.*;
import java.util.ArrayList;

public class MapGraph {
    /*unvollständig*/
    //ArrayList<ArrayList<Double>> adjList = new ArrayList<ArrayList<Double>>();
    double[] OffsetMap = new double[0];
    double[][] EdgeMap = new double[0][0];
    double nodeID1Map[] = new double[0];

    public void createGraphs(String datName) {
        /*File file = new File(datName);*/
        try {
            BufferedReader in = new BufferedReader(new FileReader(datName));
            for(int i=0;i<5;i++) in.readLine();
            int numNodes= in.read();
            int numEdges= in.read();
            for(int i=0;i<numNodes;i++){
                in.readLine();
                }
            //Fülle die Kanten liste (siehe Intro Phase I)
            for(int i=0;i<numEdges;i++) {
                double offset = 0;
                String edge = in.readLine();
                String[] split = edge.split(" "); // [srcIDX, trgIDX, cost, type, maxspeed]
                double nodeID1 = Double.parseDouble(split[0]);
                double nodeID2 = Double.parseDouble(split[0]);
                double cost = Double.parseDouble(split[0]);
                EdgeMap[i][0] = nodeID1;
                EdgeMap[i][1] = nodeID2;
                EdgeMap[i][2] = cost;
                nodeID1Map[i] = nodeID1;
            }
            //Fülle die Offset liste (siehe Intro Phase I)
            double maxID=0;
            for(double nodeID: nodeID1Map){
                if(nodeID>maxID) maxID++;
                else OffsetMap[(int) maxID]+=1;
            }

        }catch(FileNotFoundException exc){
            System.out.println("Invalid File");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}

/* I would implement it like this:

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoutePlanner {
    private int[][] graph;
    public RoutePlanner(int[][] graph) {
        this.graph = graph;
    }

    //dijkstra algorithm
    public int[] dijkstra(int startNode, int endNode) {
        int[] distances = new int[graph.length];
        boolean[] visited = new boolean[graph.length];
        PriorityQueue<Node> queue = new PriorityQueue<>();

        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[startNode] = 0;

        queue.add(new Node(startNode, 0));

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            if (visited[currentNode.node]) {
                continue;
            }
            visited[currentNode.node] = true;

            for (int i = 0; i < graph[currentNode.node].length; i++) {
                if (visited[i]) {
                    continue;
                }

                int distance = currentNode.distance + graph[currentNode.node][i];

                if (distance < distances[i]) {
                    distances[i] = distance;
                    queue.add(new Node(i, distance));
                }
            }
        }
        return distances[endNode];
    }
 */