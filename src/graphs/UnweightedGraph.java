package graphs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class UnweightedGraph<V> implements Graph<V> {
	protected List<V> vertices = new ArrayList<>(); // Store vertices
	protected List<List<Edge>> neighbors = new ArrayList<>(); // Adjacency Edge lists
	protected boolean isDirected;
	// no-arg constructor
	protected UnweightedGraph() {
		
	}
	
	protected UnweightedGraph(V[] vertices, int[][] edges, boolean isDirected) {
		this.isDirected = isDirected;
		for (int i = 0; i < vertices.length; i++) {
			addVertex(vertices[i]);
		}
		createAdjacencyLists(edges, vertices.length, isDirected);
	}
	
	// Construct a graph from vertices and edges stored in List
	protected UnweightedGraph(List<V> vertices, List<Edge> edges, boolean isDirected) {
		this.isDirected = isDirected;
		for (int i = 0; i < vertices.size(); i++) {
			addVertex(vertices.get(i));
		}
		createAdjacencyLists(edges, vertices.size(), isDirected);
	}
	
	// Construct a graph from integer vertices(0, 1, 2, ...) and edge list
	protected UnweightedGraph(List<Edge> edges, int numberOfVertices, boolean isDirected) {
		this.isDirected = isDirected;
		for (int i = 0; i < numberOfVertices; i++) {
			addVertex((V)(Integer.valueOf(i)));
		}
		createAdjacencyLists(edges, numberOfVertices, isDirected);
	}
	
	protected UnweightedGraph(int[][] edges, int numberOfVertices, boolean isDirected) {
		this.isDirected = isDirected;
		for (int i = 0; i < numberOfVertices; i++) {
			addVertex((V)(Integer.valueOf(i)));
		}
		createAdjacencyLists(edges, numberOfVertices, isDirected);
	}
	
	// Create adjacency lists for each vertex
	public void createAdjacencyLists(int[][] edges, int numberOfVertices, boolean isDirected) {
		for (int i = 0; i < edges.length; i++) {
			addEdge(edges[i][0], edges[i][1]);
			if (isDirected != true) {
				addEdge(edges[i][1], edges[i][0]);
			}
		}
	}
	
	// Create adjacency lists for each vertex
	public void createAdjacencyLists(List<Edge> edges, int numberOfVertices, boolean isDirected) {
		for (Edge edge: edges) {
			addEdge(edge.u, edge.v);
			if (isDirected != true) {
				addEdge(edge.v, edge.u);
			}
		}
	}
	@Override
	public int getSize() {
		return vertices.size();
	}

	@Override
	public List<V> getVertices() {
		return vertices;
	}

	@Override
	public V getVertex(int index) {
		return vertices.get(index);
	}

	@Override
	public int getIndex(V v) {
		return vertices.indexOf(v);
	}

	@Override
	public List<Integer> getNeighbors(int index) {
		List<Integer> result = new ArrayList<>();
		for (Edge e: neighbors.get(index)) {
			result.add(e.v);
		}
		return result;
	}

	@Override
	public int getDegree(int v) {
		return neighbors.get(v).size();
	}

	@Override
	public void printEdges() {
		for (int u = 0; u < neighbors.size(); u++) {
			System.out.print(getVertex(u) + " (" + u + "): ");
			for (Edge e: neighbors.get(u)) {
				System.out.print("(" + getVertex(e.u) + ", " + getVertex(e.v) + ") ");
			}
			System.out.println();
		}	
	}

	@Override // Clear the graph
	public void clear() {
		vertices.clear();
		neighbors.clear();
		
	}

	@Override // Add a vertex to the graph
	public boolean addVertex(V vertex) {
		if (!vertices.contains(vertex)) {
			vertices.add(vertex);
			neighbors.add(new ArrayList<Edge>());
			return true;
		} else {
			return false;
		}
	}

	@Override // Add an edge to the graph
	public boolean addEdge(int u, int v) {
		return addEdge(new Edge(u, v));
	}

	@Override
	public boolean addEdge(Edge e) {
		if (e.u < 0 || e.u > getSize() - 1) {
			throw new IllegalArgumentException("No such index: " + e.u);
		}
		if (e.v < 0 || e.v > getSize() - 1) {
			throw new IllegalArgumentException("No such index: " + e.v);
		}
		if (!neighbors.get(e.u).contains(e)) {
			neighbors.get(e.u).add(e);
			if (isDirected != true) {
				neighbors.get(e.v).add(e);
			}
			return true;
		} else {
			return false;
		}
	}

	// Obtain a DFS tree starting from vertex v
	@Override
	public SearchTree dfs(int v) {
		List<Integer> searchOrder = new ArrayList<>();
		int[] parent = new int[vertices.size()];
		for (int i = 0; i < parent.length; i++) {
			parent[i] = -1;
		}
		
		// Mark visited vertices
		boolean[] isVisited = new boolean[vertices.size()];
		
		// Recursively search
		dfs(v, parent, searchOrder, isVisited);
		
		return new SearchTree(v, parent, searchOrder);
	}
	
	private void dfs(int v, int[] parent, List<Integer> searchOrder, boolean[] isVisited) {
		// Store the visited vertex
		searchOrder.add(v);
		isVisited[v] = true; // Vertex v is visited
		
		for (Edge e : neighbors.get(v)) {
			// e.u is v
			int w = e.v; // e.v is w
			if (!isVisited[w]) {
				parent[w] = v; // The parent of vertex w is v
				dfs(w, parent, searchOrder, isVisited); // Recursive Search
			}
		}
	}
	
	@Override // Starting bfs search from vertex v
	public SearchTree bfs(int v) {
		List<Integer> searchOrder = new ArrayList<>();
		int[] parent = new int[vertices.size()];
		for (int i = 0; i < parent.length; i++) {
			parent[i] = -1; // Initialize parent[i] to -1
		}
			
			LinkedList<Integer> queue = new LinkedList<>(); // List used as a queue
			boolean[] isVisited = new boolean[vertices.size()];
			queue.offer(v); // Enqueue v
			isVisited[v] = true; // Mark as visited
			
			while(!queue.isEmpty()) {
				int u = queue.poll(); // Dequeue to u
				searchOrder.add(u); // u searched
				for (Edge e : neighbors.get(u)) {
					int w = e.v;
					if (!isVisited[w]) {
						queue.offer(w); // Enqueue w
						parent[w] = u; // The parent of w is u
						isVisited[w] = true; // Mark it visited
					}
				}
			}
			return new SearchTree(v, parent, searchOrder);
	}
	
	public class SearchTree {
		private int root; // root of the tree
		private int[] parent; // Store the parent of each vertex
		private List<Integer> searchOrder; // Store the search order
		
		// Construct a tree w/ a root, parent, and searchOrder
		public SearchTree(int root, int[] parent, List<Integer> searchOrder) {
			this.root = root;
			this.parent = parent;
			this.searchOrder = searchOrder;
		}
		
		// Return root of the tree
		public int getRoot() {
			return root;
		}
		
		// Return the parent of vertex v
		public int getParent(int v) {
			return parent[v];
		}
		
		// Return an array representing search order
		public List<Integer> getSearchOrder() {
			return searchOrder;
		}
		
		// Return number of vertices found
		public int getNumberOfVerticesFound() {
			return searchOrder.size();
		}
		
		// Return the path of vertices from a vertex to the root
		public List<V> getPath(int index) {
			ArrayList<V> path = new ArrayList<>();
			
			do {
				path.add(vertices.get(index));
				index = parent[index];
			} while (index != -1);
			return path;
		}
		
		// Print a path from the root to vertex
		public void printPath(int index) {
			List<V> path = getPath(index);
			System.out.print("A path from " + vertices.get(root) + " to " + 
			vertices.get(index) + ": ");
			for (int i = path.size() - 1; i >= 0; i--) {
				System.out.print(path.get(i) + " ");
			}
		}
		
		// Print the whole tree
		public void printTree() {
			System.out.println("Root is: " + vertices.get(root));
			System.out.print("Edges: ");
			for (int i = 0; i < parent.length; i++) {
				if (parent[i] != -1) {
					// Display an Edge
					System.out.print("(" + vertices.get(parent[i]) + ", " + vertices.get(i) + ") ");
				}
			}
			System.out.println();
		}
	}
	
	@Override
	public boolean remove(V v) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(int u, int v) {
		// TODO Auto-generated method stub
		return false;
	}

}
