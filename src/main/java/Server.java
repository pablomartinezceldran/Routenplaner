package main.java;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import main.java.models.k2Tree.K2Tree;
import main.java.models.map.MapGraph;
import main.java.services.Dijkstra;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class Server {

    private static K2Tree k2Tree;
    private static MapGraph map;

    public static void main(String[] args) throws IOException {
        initMap();
        initServer();
    }

    private static void initMap() {
        System.out.println("Reading map...");
        long readingTimeStart = System.currentTimeMillis();
        map = new MapGraph();
        map.fillMap("resources/germany.fmi");
        long readingTimeEnd = System.currentTimeMillis();
        System.out.println("Map read! Time: " + (readingTimeEnd - readingTimeStart) + " ms.");
        System.out.println("-----------------------------------------");

        System.out.println("Creating K2Tree...");
        long treeCreationStart = System.currentTimeMillis();
        k2Tree = new K2Tree(map.getNodesProperties());
        long treeCreationEnd = System.currentTimeMillis();
        System.out.println("k2tree build success. Time: " + (treeCreationEnd - treeCreationStart) + " ms.");
    }

    private static void initServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        HttpContext closestNodeContext = server.createContext("/closestNode");
        closestNodeContext.setHandler(Server::closestNodeRequestHandler);

        HttpContext routeCalculationContext = server.createContext("/routeCalculation");
        routeCalculationContext.setHandler(Server::routeCalculationRequestHandler);

        server.start();
    }

    private static void closestNodeRequestHandler(HttpExchange exchange) throws IOException {
        Map<String, String> query = queryToMap(exchange.getRequestURI().getQuery());

        double latitude = Double.parseDouble(query.get("lat"));
        double longitude = Double.parseDouble(query.get("lon"));

        double[] coords = k2Tree.findNearestNode(latitude, longitude);

        String response = "Input: lat: " + latitude + " & long: " + longitude + ". Result: " + coords[0] + ", " + coords[1];
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private static void routeCalculationRequestHandler(HttpExchange exchange) throws IOException {
        Map<String, String> query = queryToMap(exchange.getRequestURI().getQuery());

        int start = Integer.parseInt(query.get("start"));
        int end = Integer.parseInt(query.get("end"));

        int result = Dijkstra.oneToOne(map, start, end);

        String response = "Result: " + result;
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private static Map<String, String> queryToMap(String query) {
        if(query == null) {
            return null;
        }
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            } else{
                result.put(entry[0], "");
            }
        }
        return result;
    }
}
