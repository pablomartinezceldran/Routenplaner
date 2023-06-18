package main.java;

import main.java.models.map.MapGraph;
import main.java.models.map.Node;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {

        // can be deleted
        System.out.println("-----------------------------------------");
        long memoryBefore = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        MapGraph map = new MapGraph();
        map.fillMap("resources/germany.fmi");
        //feel free to use the 3 functions below
        //map.createGraph("resources/toy.fmi");
        //map.createGraph("resources/stgtregbz.fmi");
        //map.createGraph("resources/germany.fmi");


        System.out.println("-----------------------------------------");

//        System.out.println("Dijkstra algorithm:");
//        long startDijkstra = System.currentTimeMillis();
//        int sourceNode = 1;
//
//
//        List<Integer> path = new DijkstraAlgorithm().findShortestPath(map, 1, 2);
//
//        long endDijkstra = System.currentTimeMillis();
//
//        for (int i = 0; i < path.size(); ++i) {
//            if (i % 1000000 == 0) {
//                System.out.println("Distance from node: " + sourceNode + " to node: " + i + " is " + path.get(i));
//            }
//        }
//
//        System.out.println("-----------------------------------------");
//        System.out.println("Dijkstra time: " + (endDijkstra - startDijkstra) + " miliseconds");

        System.out.println("ClosestNode algorithm:");
        long startFindClosestNode = System.currentTimeMillis();

//        KDTree kdTree = new KDTree(map.getNodeList());
        long treeTime = System.currentTimeMillis();
        Node nearestNode = map.getNodeTree().findNearestNode(5024428900000000198L, 700798490000000029L);
//        Node nearestNode = kdTree.findNearestNode(4864209470000000124L, 901481120000000047L);

        long endFindClosestNode = System.currentTimeMillis();
        System.out.println("Tree creation time: " + (treeTime - startFindClosestNode) + " miliseconds");
        System.out.println("ClosestNode time: " + (endFindClosestNode - treeTime) + " miliseconds");
        System.out.println("Closest node: " + nearestNode.getNodeID());

        // can be deleted
        System.out.println("-----------------------------------------");
        long memoryAfter = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long memoryUsed = memoryAfter - memoryBefore;
        System.out.println("Memory consumption: " + memoryUsed + " bytes");
    }
}