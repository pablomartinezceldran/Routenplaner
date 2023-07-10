package main.java.models.map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MapGraph {

    private final ArrayList<ArrayList<int[]>> adjList = new ArrayList<>();
    private double[][] nodesProperties;

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

            nodesProperties = new double[numNodes][2];

            //read nodes information
            String[] values;
            int nodeId;
            for (int i = 0; i < numNodes; ++i) {
                adjList.add(new ArrayList<>());
                values = in.readLine().split(" ");
                nodeId = Integer.parseInt(values[0]);

                nodesProperties[nodeId][0] = Double.parseDouble(values[2]);
                nodesProperties[nodeId][1] = Double.parseDouble(values[3]);

                // add node to KDTree. it is more efficient to create it this way instead of creating it after,
                // but for the benchmark we need to create it after. Maybe we can change it fot next phase
//              nodeTree.addNode(node);
            }

            // read edges information
            int src;
            int trg;
            int cost;
            for (int i = 0; i < numEdges; ++i) {
                values = in.readLine().split(" ");
                src = Integer.parseInt(values[0]);
                trg = Integer.parseInt(values[1]);
                cost = Integer.parseInt(values[2]);

                int[] edgeProperties = new int[2];
                edgeProperties[0] = trg;
                edgeProperties[1] = cost;

                adjList.get(src).add(edgeProperties);
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
    public ArrayList<int[]> getAdjacentEdges(int node) {
        return adjList.get(node);
    }

    public double[][] getNodesProperties() {
        return nodesProperties;
    }
}