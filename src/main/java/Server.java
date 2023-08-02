package main.java;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import main.java.models.k2Tree.K2Tree;
import main.java.models.map.MapGraph;
import main.java.services.Dijkstra;

import java.io.*;
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
        System.out.println("Reading map and creating K2Tree...");
        long readingTimeStart = System.currentTimeMillis();
        map = new MapGraph();
        map.fillMap("resources/germany.fmi");
        k2Tree = map.getNodeTree();
        long readingTimeEnd = System.currentTimeMillis();
        System.out.println("Done! Time: " + (readingTimeEnd - readingTimeStart) + " ms.");
    }

    private static void initServer() throws IOException {
        System.out.println("Starting server...");
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/closestNode", new ClosestNodeHandler());
        server.createContext("/routeCalculation", new RouteCalculationHandler());

        server.start();
        System.out.println("Server started on port: 8080");
    }

    static class ClosestNodeHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            Map<String, String> query = queryToMap(httpExchange.getRequestURI().getQuery());

            double latitude = Double.parseDouble(query.get("lat"));
            double longitude = Double.parseDouble(query.get("lon"));

            double[] data = k2Tree.findNearestNode(latitude, longitude);
            String response = "{\"latitude\": " + data[0] + ", \"longitude\": " + data[1] + ", \"id\": " + data[2] + "}";

            httpExchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            httpExchange.getResponseHeaders().set("Content-Type", "application/json");
            httpExchange.sendResponseHeaders(200, response.getBytes().length);

            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class RouteCalculationHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            Map<String, String> query = queryToMap(httpExchange.getRequestURI().getQuery());

            int start = Integer.parseInt(query.get("start"));
            int end = Integer.parseInt(query.get("end"));

            double[][] result = Dijkstra.oneToOneNew(map, start, end);

            StringBuilder response = new StringBuilder("[");
            assert result != null;
            for(double[] coordinate : result) {
                response.append("{ \"latitude\": ").append(coordinate[0]).append(", \"longitude\": ").append(coordinate[1]).append(" }, ");
            }
            if (result.length > 0) {
                response = new StringBuilder(response.substring(0, response.length() - 2));
            }
            response.append("]");

            httpExchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            httpExchange.getResponseHeaders().set("Content-Type", "application/json");
            httpExchange.sendResponseHeaders(200, response.toString().getBytes().length);

            OutputStream os = httpExchange.getResponseBody();
            os.write(response.toString().getBytes());
            os.close();
        }
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
