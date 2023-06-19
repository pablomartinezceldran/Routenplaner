package main.java.models.map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MapGraph {

    private final ArrayList<ArrayList<Edge>> adjList = new ArrayList<>();
    private final ArrayList<Node> nodeList = new ArrayList<>();

    private Integer numNodes;
    private Integer numEdges;

    public void fillMap(String graphPath) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(graphPath));

            // read first 4 metadata information and blank line
            for (int i = 0; i < 5; ++i) {
                in.readLine();
            }
            // read number of nodes and edges of the graph
            numNodes = Integer.parseInt(in.readLine());
            numEdges = Integer.parseInt(in.readLine());

            //read nodes information
            String[] values;
            int nodeId;
            Node node;
            for (int i = 0; i < numNodes; ++i) {
                adjList.add(new ArrayList<>());
                values = in.readLine().split(" ");
                nodeId = Integer.parseInt(values[0]);

                // in order to store latitude and longitude without losing information
                // and consuming as little memory as possible, we have opted for a long
                node = new Node(
                        nodeId,
                        Long.parseLong(values[2].replace(".","")),
                        Long.parseLong(values[3].replace(".",""))
                );
                // add node to nodeList
                nodeList.add(node);

                // add node to KDTree. it is more efficient to create it this way instead of creating it after,
                // but for the benchmark we need to create it after. Maybe we can change it fot next phase
//              nodeTree.addNode(node);
            }

            // read edges information
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Integer getNumNodes() {
        return numNodes;
    }

    //returns the edges of a node
    public ArrayList<Edge> getAdjacentEdges(int node) {
        return adjList.get(node);
    }

    public ArrayList<Node> getNodeList() {
        return nodeList;
    }
}