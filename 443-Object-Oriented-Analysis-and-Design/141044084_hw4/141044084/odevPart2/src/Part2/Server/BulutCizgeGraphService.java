package Part2.Server;

import Part2.Account;
import Part2.Edge;
import Part2.Graph;
import Part2.GraphService;

import java.util.*;

public class BulutCizgeGraphService implements GraphService {
    @Override
    public double[][] getIncidenceMatrix(Account a, Graph g) {
        double[][] result;
        Edge[] edges = g.getEdges();

        result = new double[g.getV()][edges.length];

        //init result
        for (double[] row : result)
            Arrays.fill(row, 0.0);

        for (int i = 0; i < edges.length; ++i) {
            double weight = Double.isInfinite(edges[i].getWeight()) ? 0 : edges[i].getWeight();
            result[edges[i].getTo()][i] += weight;
            result[edges[i].getFrom()][i] += weight;
        }

        return result;
    }

    // Kruskal's MST Algorithm
    @Override
    public Graph getMinimumSpanningTree(Account a, Graph g) {
        //get copy of edges
        Edge[] edges = Arrays.copyOf(g.getEdges(), g.getEdges().length);

        //sort edges increasing order of their weight
        Arrays.sort(edges, new Comparator<Edge>() {
                    @Override
                    public int compare(Edge o1, Edge o2) {
                        return Double.compare(o1.getWeight(), o2.getWeight());
                    }
                }
        );

        //visited nodes
        boolean[] visited = new boolean[g.getV()];
        Arrays.fill(visited, false);

        //edges of mst
        List<Edge> newEdges = new ArrayList<>();

        for (Edge e : edges) {
            newEdges.add(e);
            if (isCyclic(newEdges, g.getV()))
                newEdges.remove(e);
        }

        return buildGraph(a, newEdges.toArray(new Edge[newEdges.size()]), g.getV());
    }

    private boolean isCyclic(List<Edge> edges, int V) {
        int[] parent = new int[V];
        Arrays.fill(parent, -1);

        for (int i = 0; i < edges.size(); ++i) {
            int x = find(parent, edges.get(i).getFrom());
            int y = find(parent, edges.get(i).getTo());

            if (x == y)
                return true;

            union(parent, x, y);
        }

        return false;
    }

    private int find(int[] parent, int i) {
        if (parent[i] == -1)
            return i;
        return find(parent, parent[i]);
    }

    private void union(int[] parent, int x, int y) {
        int xset = find(parent, x);
        int yset = find(parent, y);
        parent[xset] = yset;
    }

    @Override
    public String graphToString(Account a, Graph g)  {
        return g.toString();
    }

    @Override
    public Graph buildGraph(Account a, Edge[] edges, int V) {
        return new GraphMatrix(edges, V);
    }


}