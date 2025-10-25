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

        System.out.println("Generating graphs with fixed vertex counts:");
        System.out.println("  - Small: 5, 10, 15, 20, 25, 30 vertices (6 graphs)");
        System.out.println("  - Medium: 50, 75, 100, 125, 150, 175, 200, 225, 250, 275 vertices (10 graphs)");
        System.out.println("  - Large: 350, 400, ..., 1000 vertices (10 graphs)");
        System.out.println("  - Extra Large: 1300, 1600, 2000 vertices (3 graphs)");
        System.out.println("Total: 29 graphs\n");

        int[] smallSizes = {5, 10, 15, 20, 25, 30};
        for (int vertices : smallSizes) {
            int edges = vertices + random.nextInt(vertices / 2 + 1);
            configs.add(new GraphConfig(id++, vertices, edges, "Small"));
        }

        for (int vertices = 50; vertices <= 275; vertices += 25) {
            int edges = vertices + random.nextInt(Math.min(100, vertices / 2));
            configs.add(new GraphConfig(id++, vertices, edges, "Medium"));
        }

        for (int vertices = 350; vertices <= 1000; vertices += 72) {
            int edges = vertices + random.nextInt(Math.min(200, vertices / 3));
            configs.add(new GraphConfig(id++, vertices, edges, "Large"));
        }

        int[] extraLargeSizes = {1300, 1600, 2000};
        for (int vertices : extraLargeSizes) {
            int edges = vertices + random.nextInt(Math.min(300, vertices / 4));
            configs.add(new GraphConfig(id++, vertices, edges, "ExtraLarge"));
        }

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
            nodes.add("District_" + i);
        }
        graph.add("nodes", nodes);

        JsonArray edges = new JsonArray();
        Set<String> addedEdges = new HashSet<>();

        for (int i = 1; i < config.vertices; i++) {
            String from = "District_" + random.nextInt(i);
            String to = "District_" + i;
            int weight = 1 + random.nextInt(100);

            addEdge(edges, addedEdges, from, to, weight);
        }

        int extraEdges = config.edges - (config.vertices - 1);
        int attempts = 0;
        int maxAttempts = extraEdges * 3;

        while (extraEdges > 0 && attempts < maxAttempts) {
            attempts++;
            int u = random.nextInt(config.vertices);
            int v = random.nextInt(config.vertices);

            if (u == v) continue;

            String from = "District_" + u;
            String to = "District_" + v;
            int weight = 1 + random.nextInt(100);

            if (addEdge(edges, addedEdges, from, to, weight)) {
                extraEdges--;
            }
        }

        graph.add("edges", edges);
        return graph;
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