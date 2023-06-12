package main.java.models;

public class Edge {
    int scrIDX;
    int trgIDX;
    int cost;


    public Edge(int scrIDX, int trgIDX, int cost){
        this.scrIDX=scrIDX;
        this.trgIDX=trgIDX;
        this.cost=cost;
    }
}
