package mst;

import MST.algorithms.KruskalAlgorithm;
import MST.models.Edge;
import MST.models.Graph;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class KruskalTest {

    @Test
    public void testSimpleGraph() {
        Graph graph = new Graph(4);
        graph.addEdge(0, 1, 10);
        graph.addEdge(0, 2, 6);
        graph.addEdge(0, 3, 5);
        graph.addEdge(1, 3, 15);
        graph.addEdge(2, 3, 4);

        KruskalAlgorithm kruskal = new KruskalAlgorithm();
        kruskal.run(graph);

        assertEquals(19.0, kruskal.getTotalCost(), 0.01);
        assertEquals(3, kruskal.getMstEdges().size());
    }

    @Test
    public void testDisconnectedGraph() {
        Graph graph = new Graph(4);
        graph.addEdge(0, 1, 10);
        graph.addEdge(2, 3, 5);

        KruskalAlgorithm kruskal = new KruskalAlgorithm();
        kruskal.run(graph);

        assertEquals(2, kruskal.getMstEdges().size());
    }

    @Test
    public void testSingleVertex() {
        Graph graph = new Graph(1);

        KruskalAlgorithm kruskal = new KruskalAlgorithm();
        kruskal.run(graph);

        assertEquals(0.0, kruskal.getTotalCost(), 0.01);
        assertEquals(0, kruskal.getMstEdges().size());
    }

    @Test
    public void testEmptyGraph() {
        Graph graph = new Graph(0);

        KruskalAlgorithm kruskal = new KruskalAlgorithm();
        kruskal.run(graph);

        assertEquals(0.0, kruskal.getTotalCost(), 0.01);
        assertEquals(0, kruskal.getMstEdges().size());
    }

    @Test
    public void testTriangleGraph() {
        Graph graph = new Graph(3);
        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 2);
        graph.addEdge(2, 0, 3);

        KruskalAlgorithm kruskal = new KruskalAlgorithm();
        kruskal.run(graph);

        assertEquals(3.0, kruskal.getTotalCost(), 0.01);
        assertEquals(2, kruskal.getMstEdges().size());
    }

    @Test
    public void testCompleteGraph() {
        Graph graph = new Graph(4);
        graph.addEdge(0, 1, 1);
        graph.addEdge(0, 2, 2);
        graph.addEdge(0, 3, 3);
        graph.addEdge(1, 2, 4);
        graph.addEdge(1, 3, 5);
        graph.addEdge(2, 3, 6);

        KruskalAlgorithm kruskal = new KruskalAlgorithm();
        kruskal.run(graph);

        assertEquals(6.0, kruskal.getTotalCost(), 0.01);
        assertEquals(3, kruskal.getMstEdges().size());
    }

    @Test
    public void testExecutionTime() {
        Graph graph = new Graph(5);
        graph.addEdge(0, 1, 10);
        graph.addEdge(1, 2, 20);
        graph.addEdge(2, 3, 30);
        graph.addEdge(3, 4, 40);

        KruskalAlgorithm kruskal = new KruskalAlgorithm();
        kruskal.run(graph);

        assertTrue(kruskal.getExecutionTime() >= 0);
        assertTrue(kruskal.getOperationsCount() > 0);
    }

    @Test
    public void testCompareWithPrim() {
        Graph graph = new Graph(5);
        graph.addEdge(0, 1, 2);
        graph.addEdge(0, 3, 6);
        graph.addEdge(1, 2, 3);
        graph.addEdge(1, 3, 8);
        graph.addEdge(1, 4, 5);
        graph.addEdge(2, 4, 7);
        graph.addEdge(3, 4, 9);

        KruskalAlgorithm kruskal = new KruskalAlgorithm();
        kruskal.run(graph);

        assertEquals(16.0, kruskal.getTotalCost(), 0.01);
        assertEquals(4, kruskal.getMstEdges().size());
    }
}