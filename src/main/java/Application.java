package main.java;

import main.java.models.MapGraph;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {

        // can be deleted
        System.out.println("-----------------------------------------");
        long memoryBefore = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        MapGraph map = new MapGraph();
        map.fillMap();
        //feel free to use the 3 functions below
        //map.createGraph("resources/toy.fmi");
        //map.createGraph("resources/stgtregbz.fmi");
        //map.createGraph("resources/germany.fmi");


        // can be deleted
        long memoryAfter = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long memoryUsed = memoryAfter - memoryBefore;
        System.out.println("Memory consumption: " + memoryUsed + " bytes");
        System.out.println("-----------------------------------------");
    }
}