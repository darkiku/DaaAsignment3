package MST.algorithms;

import MST.models.Edge;
import MST.models.Graph;
import java.util.*;

public class PrimAlgorithm {

    private List<Edge> mstEdges;
    private double totalCost;
    private int operationsCount;
    private long executionTime;

    public PrimAlgorithm() {
        this.mstEdges = new ArrayList<>();
        this.totalCost = 0.0;
        this.operationsCount = 0;
    }

    public void run(Graph graph) {
        long startTime = System.nanoTime();

        int vertices = graph.getNumVertices();
        if (vertices == 0) {
            executionTime = (System.nanoTime() - startTime) / 1_000_000;
            return;
        }

        boolean[] visited = new boolean[vertices];
        double[] minCost = new double[vertices];
        int[] parent = new int[vertices];

        Arrays.fill(minCost, Double.MAX_VALUE);
        Arrays.fill(parent, -1);

        minCost[0] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) ->
                Double.compare(minCost[a[0]], minCost[b[0]]));
        pq.offer(new int[]{0, 0});

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int u = current[0];
            operationsCount++;

            if (visited[u]) continue;

            visited[u] = true;

            if (parent[u] != -1) {
                Edge edge = new Edge(parent[u], u, minCost[u]);
                mstEdges.add(edge);
                totalCost += minCost[u];
            }

            for (Edge edge : graph.getAdjacentEdges(u)) {
                int v = edge.getDestination();
                double weight = edge.getWeight();
                operationsCount++;

                if (!visited[v] && weight < minCost[v]) {
                    minCost[v] = weight;
                    parent[v] = u;
                    pq.offer(new int[]{v, 0});
                    operationsCount++;
                }
            }
        }

        executionTime = (System.nanoTime() - startTime) / 1_000_000;
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
        return executionTime / 1000.0;
    }
}