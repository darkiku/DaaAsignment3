package MST.utils;

public class Metrics {
    private String algorithmName;
    private int graphId;
    private int vertices;
    private int edges;
    private double totalCost;
    private int operationsCount;
    private double executionTimeMs;
    private int mstEdgesCount;

    public Metrics(String algorithmName, int graphId, int vertices, int edges) {
        this.algorithmName = algorithmName;
        this.graphId = graphId;
        this.vertices = vertices;
        this.edges = edges;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public void setOperationsCount(int operationsCount) {
        this.operationsCount = operationsCount;
    }

    public void setExecutionTimeMs(double executionTimeMs) {
        this.executionTimeMs = executionTimeMs;
    }

    public void setMstEdgesCount(int mstEdgesCount) {
        this.mstEdgesCount = mstEdgesCount;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public int getGraphId() {
        return graphId;
    }

    public int getVertices() {
        return vertices;
    }

    public int getEdges() {
        return edges;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public int getOperationsCount() {
        return operationsCount;
    }

    public double getExecutionTimeMs() {
        return executionTimeMs;
    }

    public int getMstEdgesCount() {
        return mstEdgesCount;
    }

    @Override
    public String toString() {
        return String.format("%s | Graph %d | V=%d E=%d | Cost=%.2f | Ops=%d | Time=%.2fms | MST edges=%d",
                algorithmName, graphId, vertices, edges, totalCost, operationsCount, executionTimeMs, mstEdgesCount);
    }

    public String toCsvRow() {
        return String.format("%s,%d,%d,%d,%.2f,%d,%.2f,%d",
                algorithmName, graphId, vertices, edges, totalCost, operationsCount, executionTimeMs, mstEdgesCount);
    }

    public static String csvHeader() {
        return "Algorithm,GraphID,Vertices,Edges,TotalCost,Operations,TimeMs,MSTEdges";
    }
}