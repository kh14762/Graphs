package graphs;

import java.util.*;

public class WeightedGraph<V> extends UnweightedGraph<V> {
	
	// No-arg constructor
	public WeightedGraph() {
		
	}
	
	// Construct a WeightedGraph from a 1D vertice array and a 2D edge array
	public WeightedGraph(V[] vertices, int[][] edges, boolean isDirected) {
		this.isDirected = isDirected;
		createWeightedGraph(Arrays.asList(vertices), edges, isDirected);
	}
	
	// Construct a WeightedGraph from number of vertices and a 2D edge array
	public WeightedGraph(int[][] edges, int numberOfVertices, boolean isDirected) {
		List<V> vertices = new ArrayList<>();
		for (int i = 0; i < numberOfVertices; i++) {
			vertices.add((V)(Integer.valueOf(i)));
		}
		createWeightedGraph(vertices, edges, isDirected);
	}
	
	// Construct a WeightedGraph from a vertice list and an edge list 
	public WeightedGraph(List<V> vertices, List<WeightedEdge> edges, boolean isDirected) {
		this.isDirected = isDirected;
		
		createWeightedGraph(vertices, edges, isDirected);
	}
	
	// Construct a WeightedGraph from num of vertices and an edge list
	public WeightedGraph(List<WeightedEdge> edges, int numberOfVertices, boolean isDirected) {
		this.isDirected = isDirected;
		
		List<V> vertices = new ArrayList<>();
		for (int i = 0; i < numberOfVertices; i++) {
			vertices.add((V)(Integer.valueOf(i)));
		}
		createWeightedGraph(vertices, edges, isDirected);
	}
	
	// Create adjacency lists from edge arrays
	private void createWeightedGraph(List<V> vertices, int[][] edges, boolean isDirected) {
		this.vertices = vertices;
		this.isDirected = isDirected;
		
		for (int i = 0; i < vertices.size(); i++) {
			neighbors.add(new ArrayList<Edge>()); // Create a list for vertices
		}
		
		for (int i = 0; i < edges.length; i++) {
			neighbors.get(edges[i][0]).add(new WeightedEdge(edges[i][0], edges[i][1], edges[i][2]));
			if (isDirected != true) {
				neighbors.get(edges[i][1]).add(new WeightedEdge(edges[i][1], edges[i][0], edges[i][2]));
			}
		}
	}
	
	// Create adjacency lists from edge lists
	private void createWeightedGraph(List<V> vertices, List<WeightedEdge> edges, boolean isDirected) {
		this.vertices = vertices;
		this.isDirected = isDirected;
		
		for (int i = 0; i < vertices.size(); i++) {
			neighbors.add(new ArrayList<Edge>()); // Create a list for vertices
		}
		
		for (WeightedEdge edge : edges) {
			neighbors.get(edge.u).add(edge); // Add an edge into the list
			if (isDirected != true) {
				neighbors.get(edge.v).add(new WeightedEdge(edge.v, edge.u, edge.weight));
			}
		}
	}
	
	// Return weight on the edge (u, v)
	public double getWeight(int u, int v) throws Exception {
		for (Edge edge: neighbors.get(u)) {
			if (edge.v == v) {
				return ((WeightedEdge)edge).weight;
			}
		}
		throw new Exception("Edge does not exist");
	}
	
	// Display edges with weights
	public void printWeightedEdges() {
		for (int i = 0; i < getSize(); i++) {
			System.out.print(getVertex(i) + " (" + i + "): ");
			for (Edge edge: neighbors.get(i)) {
				System.out.print("(" + edge.u + ", " + edge.v + ", " + ((WeightedEdge)edge).weight + ") ");
			}
			System.out.println();
		}
	}
	
	
	// Add edges to the weighted graph
	public boolean addEdge(int u, int v, double weight) {
		return addEdge(new WeightedEdge(u, v, weight));
	}
	
	// Get MST rooted at vertex 0
	public MST getMinimumSpanningTree() {
		return getMinimumSpanningTree(0);
	}
	
	// Get MST rooted at specific vertex
	public MST getMinimumSpanningTree(int startingVertex) {
		// cost[v] stores the cost by adding v to the tree
		double[] cost = new double[getSize()]; // cost[numberOfVertices]
		for (int i = 0; i < cost.length; i++) {
			cost[i] = Double.POSITIVE_INFINITY; // Initial cost
		}
		cost[startingVertex] = 0; // Cost of source is 0;
		
		int[] parent = new int[getSize()]; // Parent of a vertex
		parent[startingVertex] = -1; //starting vertex is the root
		double totalWeight = 0; // Total weight of the tree thus far
		
		List<Integer> T = new ArrayList<>(); // T is a set that contains vertices in spanning tree
		
		// Expand T
		while(T.size() < getSize()) {
			// Find the smallest cost u in V - T
			int u = -1; // Vertex to be determined
			double currentMinCost = Double.POSITIVE_INFINITY;
			for (int i = 0 ; i < getSize(); i++) {
				if (!T.contains(i) && cost[i] < currentMinCost) {
					currentMinCost = cost[i];
					u = i;
				}
			}
			if (u == -1) break; else T.add(u); // Add a new vertex to T
			totalWeight += cost[u]; // Add cost[u] to the tree
			
			// Adjust cost[v] for v that is adjacent to u and v in V - T
			for (Edge e : neighbors.get(u)) {
				if (!T.contains(e.v) && cost[e.v] > ((WeightedEdge)e).weight) {
					cost[e.v] = ((WeightedEdge)e).weight;
					parent[e.v] = u;
				}
			}
 		} // End of while loop
		return new MST(startingVertex, parent, T, totalWeight);
	}
	
	// MST is an inner class in WeightedGraph
	public class MST extends SearchTree {
		private double totalWeight; // Total weight of all edges in the tree
		
		public MST(int root, int[] parent, List<Integer> searchOrder, double totalWeight) {
			super(root, parent, searchOrder);
			this.totalWeight = totalWeight;
		}
		
		public double getTotalWeight() {
			return totalWeight;
		}
	}
	
	//Find single-shortest paths
	public ShortestPathTree getShortestPath(int sourceVertex) {
		// cost[v] stores the cost of the path from v to the source
		double[] cost = new double[getSize()];
		for (int i = 0; i < cost.length; i++) {
			cost[i] = Double.POSITIVE_INFINITY; // Initial cost set to infinity
		}
		cost[sourceVertex] = 0; // Cost of source is 0
		
		// parent[v] stores the previous vertex of v in the path
		int[] parent = new int[getSize()];
		parent[sourceVertex] = -1; // The parent of source is set to -1
		
		// T stores the vertices whose path found so far
		List<Integer> T = new ArrayList<>();
		
		// Expand T
		while(T.size() < getSize()) { // While T size < num of vertices in graph
			// Find the smalled cost u in V - T
			int u = -1; // Vertex to be determined
			double currentMinCost = Double.POSITIVE_INFINITY;
			for (int i = 0; i < getSize(); i++) {
				if (!T.contains(i) && cost[i] < currentMinCost) {
					currentMinCost = cost[i];
					u = i;
				}
			}
			
			if (u == -1) break; else T.add(u); //Add a new vertex to T
			
			// Adjust cost[v] for v that is adjacent to u and v in V - T
			for (Edge e : neighbors.get(u)) {
				if (!T.contains(e.v) && cost[e.v] > cost[u] + ((WeightedEdge)e).weight) {
					cost[e.v] = cost[u] + ((WeightedEdge)e).weight;
					parent[e.v] = u;
				}
			}
		} // End of while Loop
		
		// Create shortedPathTree
		return new ShortestPathTree(sourceVertex, parent, T, cost);
 	}
	
	//ShortestPathTree is an inner class of WeightedGraph
	public class ShortestPathTree extends SearchTree {
		private double[] cost;
		
		public ShortestPathTree(int root, int[] parent, List<Integer> searchOrder, double[] cost) {
			super(root, parent, searchOrder);
			this.cost = cost;
		}
		
		// Return the cost for a path from the root to vertex v
		public double getCost(int v) {
			return cost[v];
		}
		
		//Print paths from ALL vertices to the source
		public void printAllPaths() {
			System.out.println("All shortest paths from " + vertices.get(getRoot()) + " are:");
			for (int i = 0; i < cost.length; i++) {
				printPath(i); // Print a path from i to the source
				System.out.println("(cost: " + cost[i] + ")"); // Path cost
			}
		}
	}
}


