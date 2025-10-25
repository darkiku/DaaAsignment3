package mst;

import MST.algorithms.PrimAlgorithm;
import MST.models.Edge;
import MST.models.Graph;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PrimTest {

    @Test
    public void testSimpleGraph() {
        Graph graph = new Graph(4);
        graph.addEdge(0, 1, 10);
        graph.addEdge(0, 2, 6);
        graph.addEdge(0, 3, 5);
        graph.addEdge(1, 3, 15);
        graph.addEdge(2, 3, 4);

        PrimAlgorithm prim = new PrimAlgorithm();
        prim.run(graph);

        assertEquals(19.0, prim.getTotalCost(), 0.01);
        assertEquals(3, prim.getMstEdges().size());
    }

    @Test
    public void testDisconnectedGraph() {
        Graph graph = new Graph(4);
        graph.addEdge(0, 1, 10);
        graph.addEdge(2, 3, 5);

        PrimAlgorithm prim = new PrimAlgorithm();
        prim.run(graph);

        assertEquals(1, prim.getMstEdges().size());
    }

    @Test
    public void testSingleVertex() {
        Graph graph = new Graph(1);

        PrimAlgorithm prim = new PrimAlgorithm();
        prim.run(graph);

        assertEquals(0.0, prim.getTotalCost(), 0.01);
        assertEquals(0, prim.getMstEdges().size());
    }

    @Test
    public void testEmptyGraph() {
        Graph graph = new Graph(0);

        PrimAlgorithm prim = new PrimAlgorithm();
        prim.run(graph);

        assertEquals(0.0, prim.getTotalCost(), 0.01);
        assertEquals(0, prim.getMstEdges().size());
    }

    @Test
    public void testTriangleGraph() {
        Graph graph = new Graph(3);
        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 2);
        graph.addEdge(2, 0, 3);

        PrimAlgorithm prim = new PrimAlgorithm();
        prim.run(graph);

        assertEquals(3.0, prim.getTotalCost(), 0.01);
        assertEquals(2, prim.getMstEdges().size());
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

        PrimAlgorithm prim = new PrimAlgorithm();
        prim.run(graph);

        assertEquals(6.0, prim.getTotalCost(), 0.01);
        assertEquals(3, prim.getMstEdges().size());
    }

    @Test
    public void testExecutionTime() {
        Graph graph = new Graph(5);
        graph.addEdge(0, 1, 10);
        graph.addEdge(1, 2, 20);
        graph.addEdge(2, 3, 30);
        graph.addEdge(3, 4, 40);

        PrimAlgorithm prim = new PrimAlgorithm();
        prim.run(graph);

        assertTrue(prim.getExecutionTime() >= 0);
        assertTrue(prim.getOperationsCount() > 0);
    }
}