package main.java.models;

public class Edge {
    private final int target;
    private final int cost;

    public Edge(int trgID, int cost){
        this.target = trgID;
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }
    public int getTarget() {
        return target;
    }
}
