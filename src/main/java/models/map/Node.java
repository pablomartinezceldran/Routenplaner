package main.java.models.map;

public class Node {

    private final int nodeID;
    private final long latitude;
    private final long longitude;


    public Node(int nodeID, long latitude, long longitude){
        this.nodeID = nodeID;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getNodeID() {
        return nodeID;
    }

    public long getLatitude() {
        return latitude;
    }

    public long getLongitude() {
        return longitude;
    }
}