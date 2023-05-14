package main.java.models;

public class Node {
    int nodeID;

    /*x-Achse*/
    double latitude;

    /*y-Achse*/
    double longitude;


    public Node(int nodeID, double latitude, double longitude){
        this.nodeID=nodeID;
        this.latitude=latitude;
        this.longitude=longitude;
    }

}
