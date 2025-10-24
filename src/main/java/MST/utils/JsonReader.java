package MST.utils;

import com.google.gson.*;
import MST.models.Graph;
import java.io.FileReader;
import java.util.*;

public class JsonReader {

    public static List<GraphData> readGraphs(String filename) {
        List<GraphData> graphs = new ArrayList<>();

        try (FileReader reader = new FileReader(filename)) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            JsonArray graphsArray = json.getAsJsonArray("graphs");

            for (JsonElement graphElement : graphsArray) {
                JsonObject graphObj = graphElement.getAsJsonObject();

                int id = graphObj.get("id").getAsInt();
                JsonArray nodesArray = graphObj.getAsJsonArray("nodes");
                JsonArray edgesArray = graphObj.getAsJsonArray("edges");

                Map<String, Integer> nodeMap = new HashMap<>();
                int idx = 0;
                for (JsonElement nodeElement : nodesArray) {
                    nodeMap.put(nodeElement.getAsString(), idx++);
                }

                Graph graph = new Graph(nodesArray.size());
                Map<String, String> indexToName = new HashMap<>();
                for (Map.Entry<String, Integer> entry : nodeMap.entrySet()) {
                    indexToName.put(String.valueOf(entry.getValue()), entry.getKey());
                }

                for (JsonElement edgeElement : edgesArray) {
                    JsonObject edgeObj = edgeElement.getAsJsonObject();
                    String from = edgeObj.get("from").getAsString();
                    String to = edgeObj.get("to").getAsString();
                    double weight = edgeObj.get("weight").getAsDouble();

                    int fromIdx = nodeMap.get(from);
                    int toIdx = nodeMap.get(to);

                    graph.addEdge(fromIdx, toIdx, weight);
                }

                graphs.add(new GraphData(id, graph, nodeMap, indexToName));
            }

        } catch (Exception e) {
            System.err.println("Error reading JSON file: " + e.getMessage());
            e.printStackTrace();
        }

        return graphs;
    }

    public static class GraphData {
        public int id;
        public Graph graph;
        public Map<String, Integer> nodeNameToIndex;
        public Map<String, String> indexToNodeName;

        public GraphData(int id, Graph graph, Map<String, Integer> nodeNameToIndex,
                         Map<String, String> indexToNodeName) {
            this.id = id;
            this.graph = graph;
            this.nodeNameToIndex = nodeNameToIndex;
            this.indexToNodeName = indexToNodeName;
        }
    }
}