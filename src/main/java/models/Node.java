package main.java.models;

public class Node {
    int nodeID;
    int nodeID2;

    /*x-Achse*/
    double latitude;

    /*y-Achse*/
    double longitude;
    int elevation;

    public Node(int nodeID, int nodeID2, double latitude, double longitude, int elevation){
        this.nodeID=nodeID;
        this.nodeID2=nodeID2;
        this.latitude=latitude;
        this.longitude=longitude;
        this.elevation=elevation;
    }

}
