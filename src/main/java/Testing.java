package main.java;

import main.java.models.map.MapGraph;
import main.java.services.Dijkstra;


public class Testing {
    public static void main(String[] args) {

        System.out.println("Reading map...");
        MapGraph map = new MapGraph();
        map.fillMap("resources/germany.fmi");
        System.out.println("Map read!");

        double[][] result = Dijkstra.oneToOneNew(map, 15105083, 12241570);

        assert result != null;
        for (double[] doubles : result) { // Recorre las filas
            for (double elemento : doubles) { // Recorre las columnas
                System.out.print(elemento + " ");
            }
            System.out.println(); // Nueva línea para separar las filas
        }

        System.out.println("Creating K2Tree...");
        System.out.println("k2tree build success!");
        double[] coords1 = map.getNodeTree().findNearestNode(9.098, 48.746);
        double[] coords2 = map.getNodeTree().findNearestNode(48.746, 9.098);
        System.out.println("Input: lat: 9.098 & long: 48.746. Result: " + coords1[0] + ", " + coords1[1] + ". Node ID: " + coords1[2]);
        System.out.println("Input: lat: 48.746 & long: 9.098. Result: " + coords2[0] + ", " + coords2[1] + ". Node ID: " + coords2[2]);

        result = Dijkstra.oneToOneNew(map, 15105083, 12241570);

        assert result != null;
        for (double[] doubles : result) { // Recorre las filas
            for (double elemento : doubles) { // Recorre las columnas
                System.out.print(elemento + " ");
            }
            System.out.println(); // Nueva línea para separar las filas
        }



//        System.out.println("Dijkstra one to all:");
//        long dijkstra1 = System.currentTimeMillis();
//        Dijkstra.oneToAll(map, 16743660);
//        long dijkstra2 = System.currentTimeMillis();
//        System.out.println("Time one to all: " + (dijkstra2 - dijkstra1) + " ms");
//
//        System.out.println("Dijkstra one to one:");
//        dijkstra1 = System.currentTimeMillis();
//        System.out.println("One to one: " + Dijkstra.oneToOne(map, 8371834, 16743660));
//        dijkstra2 = System.currentTimeMillis();
//        System.out.println("Time one to one: " + (dijkstra2 - dijkstra1) + " ms");


//        double[][] nodes = map.getNodeList();
//
//        int i = 1;
//        for (double[] values : nodes) {
//            System.out.println(i + ": " + values[0] + ", " + values[1]);
//            ++i;
//        }

//        long t1;
//        long t2;
//        int sum = 0;
//        int numNodes = 10;
//        for (int i = 0; i < numNodes; ++i) {
//            t1 = System.currentTimeMillis();
//            Dijkstra.oneToAll(map, i);
//            t2 = System.currentTimeMillis();
//            System.out.println("Time node: " + i + " is: " + (t2 - t1) + "ms.");
//            sum += (t2 - t1);
//        }
//        System.out.println("Average time: " + sum / numNodes + " ms");

//        System.out.println("Dijkstra one to one:");
//        long dijkstra1 = System.currentTimeMillis();
//        System.out.println("One to one: " + Dijkstra.oneToOne(map, 8371834, 16743660));
//        long dijkstra2 = System.currentTimeMillis();
//        System.out.println("Time one to one: " + (dijkstra2 - dijkstra1) + " ms");
//
//        System.out.println("-----------------------------------------");
//        dijkstra1 = System.currentTimeMillis();
//        int[] distances = Dijkstra.oneToAll(map, 8371834);
//        dijkstra2 = System.currentTimeMillis();
//        System.out.println("Time one to all prep: " + (dijkstra2 - dijkstra1) + " ms");
//        dijkstra1 = System.currentTimeMillis();
//        int d = distances[16743660];
//        dijkstra2 = System.currentTimeMillis();
//        System.out.println("Time query one to all array: " + (dijkstra2 - dijkstra1) + " ms. Value: " + d);
    }
}