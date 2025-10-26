# MST Algorithms - City Transportation Network

## What is this project about

This project implements two algorithms for finding minimum spanning tree in graphs - Prim's and Kruskal's algorithms. The idea is to connect all city districts with roads while minimizing total construction cost.

## How it works

The program reads graphs from JSON file, runs both algorithms on each graph, and compares their performance.

### Prim's Algorithm
Starts from one vertex and keeps adding the cheapest edge that connects to a new vertex. Uses priority queue to find minimum edge quickly.

### Kruskal's Algorithm  
Sorts all edges by weight and adds them one by one if they don't create a cycle. Uses Union-Find to check for cycles.

## Test Data

I tested the algorithms on 29 different graphs:
- 6 small graphs (5-30 vertices)
- 10 medium graphs (50-275 vertices)  
- 10 large graphs (350-1000 vertices)
- 3 extra large graphs (1300-2000 vertices)

## Results

After running both algorithms on all test graphs, here's what I found:

**Small graphs:** Both algorithms work fast, Kruskal is about 25% faster on average.

**Medium graphs:** Kruskal still faster by around 30%. The difference becomes more noticeable.

**Large graphs:** Kruskal maintains its advantage with 28% better performance.

**Extra large graphs:** Kruskal is about 27% faster even on really big graphs.

## Why Kruskal is faster

The main reason is that city transportation networks are sparse graphs - not every district connects to every other district. Kruskal's algorithm works better on sparse graphs because:
1. Sorting edges once is more efficient than multiple priority queue operations
2. Union-Find data structure is very fast with path compression
3. Algorithm stops early once MST is complete

## Comparison

| Aspect | Prim | Kruskal |
|--------|------|---------|
| Time complexity | O(E log V) | O(E log E) |
| Space complexity | O(V) | O(V) |
| Works better for | Dense graphs | Sparse graphs |
| Operations count | More | Less |
| Actual speed | Slower | Faster |

## Conclusions

1. Both algorithms always find the same minimum cost (which is correct)
2. Kruskal performs better for transportation networks
3. The performance difference is consistent across all graph sizes
4. For real city planning, I would recommend Kruskal's algorithm

## How to run

Generate test data:
```bash
mvn compile exec:java -Dexec.mainClass="MST.utils.InputJsonGenerator"
```

Run the program:
```bash
mvn exec:java
```

Run tests:
```bash
mvn test
```

## Graph Structure

The project uses custom Graph and Edge classes:

**Graph.java** - stores vertices and edges using adjacency list
**Edge.java** - represents a road between two districts with a cost

Example graph structure:
```
Vertex 0: connected to [1(10), 2(15)]
Vertex 1: connected to [0(10), 3(20)]
Vertex 2: connected to [0(15), 3(12)]
Vertex 3: connected to [1(20), 2(12)]
```

## Files

- `data/ass_3_input.json` - input graphs
- `data/ass_3_output.json` - detailed results  
- `data/results.csv` - performance metrics for analysis

## Testing

I wrote unit tests for both algorithms that check:
- MST cost is the same for both algorithms
- MST has exactly V-1 edges
- No cycles in the result
- All vertices are connected

All tests pass successfully.

## References

1. Introduction to Algorithms by Cormen 
2. W3school (https://www.w3schools.com/dsa/dsa_algo_mst_kruskal.php)
3. GeeksforGeeks articles on MST algorithms https://www.geeksforgeeks.org/dsa/prims-minimum-spanning-tree-mst-greedy-algo-5
