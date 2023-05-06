package main.java.models;

public class Edge {
    int scrIDX;
    int trgIDX;
    int cost;
    int type;
    int maxspeed;


    public Edge(int scrIDX, int trgIDX, int cost, int type, int maxspeed){
        this.scrIDX=scrIDX;
        this.trgIDX=trgIDX;
        this.cost=cost;
        this.type=type;
        this.maxspeed=maxspeed;
    }
}
