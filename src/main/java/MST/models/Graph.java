package MST.models;

import java.util.*;

public class Graph {
    private int numVertices;
    private List<List<Edge>> adjacencyList;
    private List<Edge> allEdges;

    public Graph(int vertices) {
        this.numVertices = vertices;
        adjacencyList = new ArrayList<>();
        allEdges = new ArrayList<>();

        for (int i = 0; i < vertices; i++) {
            adjacencyList.add(new ArrayList<>());
        }
    }

    public void addEdge(int source, int dest, double weight) {
        Edge edge = new Edge(source, dest, weight);

        adjacencyList.get(source).add(edge);
        adjacencyList.get(dest).add(new Edge(dest, source, weight));

        if (!allEdges.contains(edge)) {
            allEdges.add(edge);
        }
    }

    public int getNumVertices() {
        return numVertices;
    }

    public int getNumEdges() {
        return allEdges.size();
    }

    public List<Edge> getAllEdges() {
        return new ArrayList<>(allEdges);
    }

    public List<Edge> getAdjacentEdges(int vertex) {
        return adjacencyList.get(vertex);
    }

    public boolean isConnected() {
        if (numVertices == 0) return true;

        boolean[] visited = new boolean[numVertices];
        dfs(0, visited);

        for (boolean v : visited) {
            if (!v) return false;
        }
        return true;
    }

    private void dfs(int vertex, boolean[] visited) {
        visited[vertex] = true;
        for (Edge edge : adjacencyList.get(vertex)) {
            if (!visited[edge.getDestination()]) {
                dfs(edge.getDestination(), visited);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Graph with ").append(numVertices).append(" vertices and ")
                .append(allEdges.size()).append(" edges:\n");

        for (int i = 0; i < numVertices; i++) {
            sb.append("Vertex ").append(i).append(": ");
            for (Edge e : adjacencyList.get(i)) {
                sb.append("->").append(e.getDestination())
                        .append("(").append(e.getWeight()).append(") ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}