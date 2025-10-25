package MST.algorithms;

import MST.models.Edge;
import MST.models.Graph;
import java.util.*;

public class KruskalAlgorithm {

    private List<Edge> mstEdges;
    private double totalCost;
    private int operationsCount;
    private long executionTime;

    private int[] parent;
    private int[] rank;

    public KruskalAlgorithm() {
        this.mstEdges = new ArrayList<>();
        this.totalCost = 0.0;
        this.operationsCount = 0;
    }

    public void run(Graph graph) {
        long startTime = System.nanoTime();

        int vertices = graph.getNumVertices();
        if (vertices == 0) {
            executionTime = System.nanoTime() - startTime;
            return;
        }

        parent = new int[vertices];
        rank = new int[vertices];
        for (int i = 0; i < vertices; i++) {
            parent[i] = i;
            rank[i] = 0;
        }

        List<Edge> edges = new ArrayList<>(graph.getAllEdges());
        Collections.sort(edges);
        operationsCount += edges.size();

        for (Edge edge : edges) {
            int u = edge.getSource();
            int v = edge.getDestination();

            operationsCount++;
            int setU = find(u);
            int setV = find(v);

            if (setU != setV) {
                mstEdges.add(edge);
                totalCost += edge.getWeight();
                union(setU, setV);
                operationsCount++;

                if (mstEdges.size() == vertices - 1) {
                    break;
                }
            }
        }

        executionTime = System.nanoTime() - startTime;
    }

    private int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
            operationsCount++;
        }
        return parent[x];
    }

    private void union(int x, int y) {
        if (rank[x] < rank[y]) {
            parent[x] = y;
        } else if (rank[x] > rank[y]) {
            parent[y] = x;
        } else {
            parent[y] = x;
            rank[x]++;
        }
    }

    public List<Edge> getMstEdges() {
        return mstEdges;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public int getOperationsCount() {
        return operationsCount;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public double getExecutionTimeMs() {
        return executionTime / 1_000_000.0;
    }
}