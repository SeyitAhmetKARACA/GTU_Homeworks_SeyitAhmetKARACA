package Part2;

import java.io.Serializable;

public class Edge implements Serializable {
    private int from;
    private int to;
    private double weight;

    public Edge() { }

    /**
     * Creating new edges
     * @param from where
     * @param to where
     * @param weight distance
     */
    public Edge(int from, int to, double weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public double getWeight() {
        return weight;
    }
}