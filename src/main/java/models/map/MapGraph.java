package main.java.models.map;

import main.java.models.KDTree.KDTree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MapGraph {

    private final ArrayList<ArrayList<Edge>> adjList = new ArrayList<>();
    private final ArrayList<Node> nodeList = new ArrayList<>();

    private final KDTree nodeTree = new KDTree();

    private Integer numNodes;
    private Integer numEdges;

    public void fillMap() throws IOException {
        // select fmi file
//        BufferedReader in = new BufferedReader(new FileReader("resources/toy.fmi"));
//        BufferedReader in = new BufferedReader(new FileReader("resources/stgtregbz.fmi"));
        BufferedReader in = new BufferedReader(new FileReader("resources/germany.fmi"));

        // read first 4 metadata information and blank line
        for (int i = 0; i < 5; ++i) {
            in.readLine();
        }
        // read number of nodes and edges of the graph
        numNodes = Integer.parseInt(in.readLine());
        numEdges = Integer.parseInt(in.readLine());

        // check time to read nodes and edges. can be deleted
        long nodesReadTime = System.currentTimeMillis();

        //read nodes information. Variables are declared outside the loop to reduce memory usage
        String[] values;
        int nodeId;
        Node node;
        for (int i = 0; i < numNodes; ++i) {
            adjList.add(new ArrayList<>());
            values = in.readLine().split(" ");
            nodeId = Integer.parseInt(values[0]);

            node = new Node(
                    nodeId,
                    Long.parseLong(values[2].replace(".","")),
                    Long.parseLong(values[3].replace(".",""))
            );
            // add node to nodeList
            nodeList.add(node);

            // add node to KDTree
            nodeTree.addNode(node);
        }

        // check time to read nodes and edges. can be deleted
        long edgesReadTime = System.currentTimeMillis();

        // read edges information. Variables are declared outside the loop to reduce memory usage
        int src;
        int trg;
        int cost;
        Edge edge;
        for (int i = 0; i < numEdges; ++i) {
            values = in.readLine().split(" ");
            src = Integer.parseInt(values[0]);
            trg = Integer.parseInt(values[1]);
            cost = Integer.parseInt(values[2]);
            edge = new Edge(trg, cost);
            adjList.get(src).add(edge);
        }
        in.close();

        // check time to read nodes and edges. can be deleted
        long endTime = System.currentTimeMillis();
        long nodesTime = edgesReadTime - nodesReadTime;
        long edgesTime = endTime - edgesReadTime;
        System.out.println("Nodes time: " + nodesTime + " miliseconds");
        System.out.println("Edges time: " + edgesTime + " miliseconds");
    }

    // prints node list. do not use with big maps, too many prints
    public void printNodeList() {
        for (Node node : nodeList) {
            System.out.println(node.getNodeID() + " " + node.getLatitude() + " " + node.getLongitude());
        }
    }

    // prints adj list. do not use with big maps, too many prints
    public void printAdjList() {
        for (int i = 0; i < adjList.size(); ++i) {
            System.out.print(i + ": ");
            for (Edge edge : adjList.get(i)) {
                System.out.print(edge.getTarget() + "(" + edge.getCost() + ") ");
            }
            System.out.println();
        }
    }

    public ArrayList<ArrayList<Edge>> getAdjList() {
        return adjList;
    }

    public Integer getNumEdges() {
        return numEdges;
    }

    public Integer getNumNodes() {
        return numNodes;
    }

    public ArrayList<Edge> getAdjacentEdges(int node) {
        return adjList.get(node);
    }

    public ArrayList<Node> getNodeList() {
        return nodeList;
    }

    public KDTree getNodeTree() {
        return nodeTree;
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
