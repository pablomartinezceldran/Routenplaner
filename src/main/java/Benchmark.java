package main.java;

import main.java.models.k2Tree.K2Tree;
import main.java.models.map.MapGraph;
import main.java.services.Dijkstra;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class Benchmark {

	public static void main(String[] args) {
		// read parameters (parameters are expected in exactly this order)
		String graphPath = args[1];
		double lon = Double.parseDouble(args[3]);
		double lat = Double.parseDouble(args[5]);
		String quePath = args[7];
		int sourceNodeId = Integer.parseInt(args[9]);

		// run benchmarks
		System.out.println("Reading graph file and creating graph data structure (" + graphPath + ")");
		long graphReadStart = System.currentTimeMillis();

		// TODO: read graph here
		MapGraph map = new MapGraph();
		map.fillMap(graphPath);

		long graphReadEnd = System.currentTimeMillis();
		System.out.println("\tgraph read took " + (graphReadEnd - graphReadStart) + "ms");

		System.out.println("Setting up closest node data structure...");

		// TODO: set up closest node data structure here
		K2Tree k2Tree = new K2Tree(map.getNodesProperties());

		System.out.println("Finding closest node to coordinates " + lon + " " + lat);
		long nodeFindStart = System.currentTimeMillis();
		double[] coords = {0.0, 0.0};

		// TODO: find closest node here and write coordinates into coords
		coords = k2Tree.findNearestNode(lat, lon);

		long nodeFindEnd = System.currentTimeMillis();
		System.out.println("\tfinding node took " + (nodeFindEnd - nodeFindStart) + "ms: " + coords[0] + ", " + coords[1]);

		System.out.println("Running one-to-one Dijkstras for queries in .que file " + quePath);
		long queStart = System.currentTimeMillis();
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(quePath))) {
			String currLine;
			while ((currLine = bufferedReader.readLine()) != null) {
				int oneToOneSourceNodeId = Integer.parseInt(currLine.substring(0, currLine.indexOf(" ")));
				int oneToOneTargetNodeId = Integer.parseInt(currLine.substring(currLine.indexOf(" ") + 1));
				int oneToOneDistance = -42;

				// TODO set oneToOneDistance to the distance from
				// oneToOneSourceNodeId to oneToOneSourceNodeId as computed by
				// the one-to-one Dijkstra
				oneToOneDistance = Dijkstra.oneToOne(map, oneToOneSourceNodeId, oneToOneTargetNodeId);

				System.out.println(oneToOneDistance);
			}
		} catch (Exception e) {
			System.out.println("Exception...");
			e.printStackTrace();
		}
		long queEnd = System.currentTimeMillis();
		System.out.println("\tprocessing .que file took " + (queEnd - queStart) + "ms");

		System.out.println("Computing one-to-all Dijkstra from node id " + sourceNodeId);
		long oneToAllStart = System.currentTimeMillis();

		// TODO: run one-to-all Dijkstra here
		int[] distances= Dijkstra.oneToAll(map, sourceNodeId);

		long oneToAllEnd = System.currentTimeMillis();
		System.out.println("\tone-to-all Dijkstra took " + (oneToAllEnd - oneToAllStart) + "ms");

		// ask user for a target node id
		System.out.print("Enter target node id... ");
		int targetNodeId = (new Scanner(System.in)).nextInt();
		int oneToAllDistance = -42;

		// TODO set oneToAllDistance to the distance from sourceNodeId to
		// targetNodeId as computed by the one-to-all Dijkstra
		oneToAllDistance = distances[targetNodeId];

		System.out.println("Distance from " + sourceNodeId + " to " + targetNodeId + " is " + oneToAllDistance);
	}

}
