import main.java.models.MapGraph1;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {

        // can be deleted
        System.out.println("-----------------------------------------");
        long memoryBefore = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        MapGraph1 map = new MapGraph1();
        map.fillMap();

        // can be deleted
        long memoryAfter = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long memoryUsed = memoryAfter - memoryBefore;
        System.out.println("Memory consumption: " + memoryUsed + " bytes");
        System.out.println("-----------------------------------------");
    }
}