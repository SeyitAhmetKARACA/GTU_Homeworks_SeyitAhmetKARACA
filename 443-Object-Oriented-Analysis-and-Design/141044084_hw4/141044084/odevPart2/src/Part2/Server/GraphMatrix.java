package Part2.Server;

import Part2.Edge;
import Part2.Graph;

import java.util.Arrays;

public class GraphMatrix implements Graph {

    private final double[][] graph;
    private final Edge[] edges;
    private final int V;

    public GraphMatrix(Edge[] edges, int V) {
        this.edges = edges;
        this.V = V;
        graph = new double[V][V];

        for (double[] row : graph)
            Arrays.fill(row, Double.POSITIVE_INFINITY);

        for (Edge e : edges) {
            graph[e.getFrom()][e.getTo()] = e.getWeight();
            graph[e.getTo()][e.getFrom()] = e.getWeight();
        }
    }

    @Override
    public int getV() {
        return V;
    }

    @Override
    public Edge[] getEdges() {
        return edges;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < graph.length; ++i)
            for (int j = i; j < graph[i].length; ++j)
                if (!Double.isInfinite(graph[i][j]))
                    result.append(i + " - " + j + " weight: " + String.format("%5.2f\n", graph[i][j]));

        return result.toString();
    }
}
