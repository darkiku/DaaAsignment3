package MST.utils;

import com.google.gson.*;
import MST.models.Edge;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonWriter {

    public static void writeResults(String filename, List<ResultData> results) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject root = new JsonObject();
        JsonArray resultsArray = new JsonArray();

        for (ResultData result : results) {
            JsonObject resultObj = new JsonObject();
            resultObj.addProperty("graph_id", result.graphId);

            JsonObject inputStats = new JsonObject();
            inputStats.addProperty("vertices", result.vertices);
            inputStats.addProperty("edges", result.edges);
            resultObj.add("input_stats", inputStats);

            JsonObject primObj = createAlgorithmResult(result.primEdges, result.primCost,
                    result.primOperations, result.primTime, result.indexToName);
            resultObj.add("prim", primObj);

            JsonObject kruskalObj = createAlgorithmResult(result.kruskalEdges, result.kruskalCost,
                    result.kruskalOperations, result.kruskalTime, result.indexToName);
            resultObj.add("kruskal", kruskalObj);

            resultsArray.add(resultObj);
        }

        root.add("results", resultsArray);

        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(root, writer);
            System.out.println("Results written to " + filename);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    private static JsonObject createAlgorithmResult(List<Edge> edges, double cost,
                                                    int operations, double time,
                                                    Map<String, String> indexToName) {
        JsonObject obj = new JsonObject();

        JsonArray edgesArray = new JsonArray();
        for (Edge edge : edges) {
            JsonObject edgeObj = new JsonObject();
            String fromName = indexToName.get(String.valueOf(edge.getSource()));
            String toName = indexToName.get(String.valueOf(edge.getDestination()));

            edgeObj.addProperty("from", fromName);
            edgeObj.addProperty("to", toName);
            edgeObj.addProperty("weight", edge.getWeight());
            edgesArray.add(edgeObj);
        }
        obj.add("mst_edges", edgesArray);

        obj.addProperty("total_cost", Math.round(cost));
        obj.addProperty("operations_count", operations);
        obj.addProperty("execution_time_ms", Math.round(time * 100.0) / 100.0);

        return obj;
    }

    public static class ResultData {
        public int graphId;
        public int vertices;
        public int edges;

        public List<Edge> primEdges;
        public double primCost;
        public int primOperations;
        public double primTime;

        public List<Edge> kruskalEdges;
        public double kruskalCost;
        public int kruskalOperations;
        public double kruskalTime;

        public Map<String, String> indexToName;

        public ResultData(int graphId, int vertices, int edges,
                          List<Edge> primEdges, double primCost, int primOps, double primTime,
                          List<Edge> kruskalEdges, double kruskalCost, int kruskalOps, double kruskalTime,
                          Map<String, String> indexToName) {
            this.graphId = graphId;
            this.vertices = vertices;
            this.edges = edges;
            this.primEdges = primEdges;
            this.primCost = primCost;
            this.primOperations = primOps;
            this.primTime = primTime;
            this.kruskalEdges = kruskalEdges;
            this.kruskalCost = kruskalCost;
            this.kruskalOperations = kruskalOps;
            this.kruskalTime = kruskalTime;
            this.indexToName = indexToName;
        }
    }
}