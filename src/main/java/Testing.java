package main.java;

import main.java.models.map.MapGraph;
import main.java.services.Dijkstra;

import java.io.IOException;

public class Testing {
    public static void main(String[] args) {

        System.out.println("-----------------------------------------");
        MapGraph map = new MapGraph();
        map.fillMap("resources/germany.fmi");
        System.out.println("Map read!");
        System.out.println("-----------------------------------------");

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

        System.out.println("Dijkstra one to one:");
        long dijkstra1 = System.currentTimeMillis();
        System.out.println("One to one: " + Dijkstra.oneToOne(map, 8371834, 16743660));
        long dijkstra2 = System.currentTimeMillis();
        System.out.println("Time one to one: " + (dijkstra2 - dijkstra1) + " ms");

        System.out.println("-----------------------------------------");
        dijkstra1 = System.currentTimeMillis();
        int[] distances = Dijkstra.oneToAll(map, 8371834);
        dijkstra2 = System.currentTimeMillis();
        System.out.println("Time one to all prep: " + (dijkstra2 - dijkstra1) + " ms");
        dijkstra1 = System.currentTimeMillis();
        int d = distances[16743660];
        dijkstra2 = System.currentTimeMillis();
        System.out.println("Time query one to all array: " + (dijkstra2 - dijkstra1) + " ms. Value: " + d);
    }
}