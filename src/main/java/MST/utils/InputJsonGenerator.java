package MST.utils;

import com.google.gson.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class InputJsonGenerator {

    private static final Random random = new Random(42);

    public static void main(String[] args) {
        String filename = "data/ass_3_input.json";

        List<GraphConfig> configs = new ArrayList<>();

        int id = 1;

        for (int i = 0; i < 10; i++) {
            int vertices = 4 + random.nextInt(3);
            int edges = vertices + random.nextInt(vertices);
            configs.add(new GraphConfig(id++, vertices, edges, "Small"));
        }

        for (int i = 0; i < 10; i++) {
            int vertices = 10 + random.nextInt(6);
            int maxEdges = vertices * (vertices - 1) / 2;
            int edges = vertices + random.nextInt(Math.min(20, maxEdges - vertices));
            configs.add(new GraphConfig(id++, vertices, edges, "Medium"));
        }

        for (int i = 0; i < 15; i++) {
            int vertices = 20 + random.nextInt(11);
            int maxEdges = vertices * (vertices - 1) / 2;
            int edges = vertices + random.nextInt(Math.min(50, maxEdges - vertices));
            configs.add(new GraphConfig(id++, vertices, edges, "Large"));
        }

        System.out.println("Generating " + configs.size() + " graphs:");
        System.out.println("  - Small (4-6 vertices): 10 graphs");
        System.out.println("  - Medium (10-15 vertices): 10 graphs");
        System.out.println("  - Large (20-30 vertices): 15 graphs");

        generateGraphs(filename, configs);
    }

    public static void generateGraphs(String filename, List<GraphConfig> configs) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject root = new JsonObject();
        JsonArray graphsArray = new JsonArray();

        for (GraphConfig config : configs) {
            System.out.println("Generating graph #" + config.id + " (" + config.size +
                    ", V=" + config.vertices + ", E=" + config.edges + ")");
            JsonObject graph = generateGraph(config);
            graphsArray.add(graph);
        }

        root.add("graphs", graphsArray);

        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(root, writer);
            System.out.println("\n✓ Successfully generated " + configs.size() +
                    " graphs in " + filename);
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }

    private static JsonObject generateGraph(GraphConfig config) {
        JsonObject graph = new JsonObject();
        graph.addProperty("id", config.id);

        JsonArray nodes = new JsonArray();
        for (int i = 0; i < config.vertices; i++) {
            if (config.vertices <= 26) {
                nodes.add("District_" + (char)('A' + i));
            } else {
                nodes.add("District_" + i);
            }
        }
        graph.add("nodes", nodes);

        JsonArray edges = new JsonArray();
        Set<String> addedEdges = new HashSet<>();

        for (int i = 1; i < config.vertices; i++) {
            String from = getNodeName(random.nextInt(i), config.vertices);
            String to = getNodeName(i, config.vertices);
            int weight = 1 + random.nextInt(100);

            addEdge(edges, addedEdges, from, to, weight);
        }

        int extraEdges = config.edges - (config.vertices - 1);
        int attempts = 0;
        while (extraEdges > 0 && attempts < config.edges * 3) {
            attempts++;
            int u = random.nextInt(config.vertices);
            int v = random.nextInt(config.vertices);

            if (u == v) continue;

            String from = getNodeName(u, config.vertices);
            String to = getNodeName(v, config.vertices);
            int weight = 1 + random.nextInt(100);

            if (addEdge(edges, addedEdges, from, to, weight)) {
                extraEdges--;
            }
        }

        graph.add("edges", edges);
        return graph;
    }

    private static String getNodeName(int index, int totalVertices) {
        if (totalVertices <= 26) {
            return "District_" + (char)('A' + index);
        } else {
            return "District_" + index;
        }
    }

    private static boolean addEdge(JsonArray edges, Set<String> addedEdges,
                                   String from, String to, int weight) {
        String edgeKey = from.compareTo(to) < 0 ? from + "-" + to : to + "-" + from;

        if (addedEdges.contains(edgeKey)) {
            return false;
        }

        JsonObject edge = new JsonObject();
        edge.addProperty("from", from);
        edge.addProperty("to", to);
        edge.addProperty("weight", weight);
        edges.add(edge);
        addedEdges.add(edgeKey);
        return true;
    }

    static class GraphConfig {
        int id;
        int vertices;
        int edges;
        String size;

        GraphConfig(int id, int vertices, int edges, String size) {
            this.id = id;
            this.vertices = vertices;
            this.edges = edges;
            this.size = size;
        }
    }
}