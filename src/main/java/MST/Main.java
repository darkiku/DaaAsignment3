package MST;

import MST.algorithms.KruskalAlgorithm;
import MST.algorithms.PrimAlgorithm;
import MST.models.Graph;
import MST.utils.JsonReader;
import MST.utils.JsonWriter;
import MST.utils.Metrics;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String inputFile = "data/ass_3_input.json";
        String outputFile = "data/ass_3_output.json";
        String csvFile = "data/results.csv";

        System.out.println("=== City Transportation Network MST Optimizer ===\n");

        System.out.println("Reading input data from: " + inputFile);
        List<JsonReader.GraphData> graphs = JsonReader.readGraphs(inputFile);
        System.out.println("Loaded " + graphs.size() + " graphs\n");

        List<JsonWriter.ResultData> results = new ArrayList<>();
        List<Metrics> allMetrics = new ArrayList<>();

        for (JsonReader.GraphData graphData : graphs) {
            System.out.println("Processing Graph #" + graphData.id);
            Graph graph = graphData.graph;

            System.out.println("  Vertices: " + graph.getNumVertices());
            System.out.println("  Edges: " + graph.getNumEdges());

            if (!graph.isConnected()) {
                System.out.println("  WARNING: Graph is not connected!");
                System.out.println("  Skipping this graph...\n");
                continue;
            }

            Metrics primMetrics = new Metrics("Prim", graphData.id,
                    graph.getNumVertices(), graph.getNumEdges());

            System.out.println("\n  Running Prim's Algorithm...");
            PrimAlgorithm prim = new PrimAlgorithm();
            prim.run(graph);

            primMetrics.setTotalCost(prim.getTotalCost());
            primMetrics.setOperationsCount(prim.getOperationsCount());
            primMetrics.setExecutionTimeMs(prim.getExecutionTimeMs());
            primMetrics.setMstEdgesCount(prim.getMstEdges().size());
            allMetrics.add(primMetrics);

            System.out.println("    " + primMetrics);

            Metrics kruskalMetrics = new Metrics("Kruskal", graphData.id,
                    graph.getNumVertices(), graph.getNumEdges());

            System.out.println("\n  Running Kruskal's Algorithm...");
            KruskalAlgorithm kruskal = new KruskalAlgorithm();
            kruskal.run(graph);

            kruskalMetrics.setTotalCost(kruskal.getTotalCost());
            kruskalMetrics.setOperationsCount(kruskal.getOperationsCount());
            kruskalMetrics.setExecutionTimeMs(kruskal.getExecutionTimeMs());
            kruskalMetrics.setMstEdgesCount(kruskal.getMstEdges().size());
            allMetrics.add(kruskalMetrics);

            System.out.println("    " + kruskalMetrics);

            if (Math.abs(prim.getTotalCost() - kruskal.getTotalCost()) < 0.01) {
                System.out.println("\n  ✓ Both algorithms found the same MST cost!");
            } else {
                System.out.println("\n  ✗ WARNING: MST costs differ!");
            }

            System.out.println("  " + "=".repeat(50) + "\n");

            JsonWriter.ResultData result = new JsonWriter.ResultData(
                    graphData.id,
                    graph.getNumVertices(),
                    graph.getNumEdges(),
                    prim.getMstEdges(),
                    prim.getTotalCost(),
                    prim.getOperationsCount(),
                    prim.getExecutionTimeMs(),
                    kruskal.getMstEdges(),
                    kruskal.getTotalCost(),
                    kruskal.getOperationsCount(),
                    kruskal.getExecutionTimeMs(),
                    graphData.indexToNodeName
            );
            results.add(result);
        }

        System.out.println("\nWriting results to: " + outputFile);
        JsonWriter.writeResults(outputFile, results);

        System.out.println("Writing metrics to: " + csvFile);
        writeMetricsToCsv(csvFile, allMetrics);

        System.out.println("\n=== Processing Complete ===");
        System.out.println("Total graphs processed: " + results.size());
        System.out.println("Check results in:");
        System.out.println("  - " + outputFile);
        System.out.println("  - " + csvFile);
    }

    private static void writeMetricsToCsv(String filename, List<Metrics> metrics) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(Metrics.csvHeader() + "\n");
            for (Metrics m : metrics) {
                writer.write(m.toCsvRow() + "\n");
            }
            System.out.println("CSV file created successfully!");
        } catch (IOException e) {
            System.err.println("Error writing CSV: " + e.getMessage());
        }
    }
}