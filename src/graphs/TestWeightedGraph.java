package graphs;

import java.util.*;

/* 
 * A degree-constrained minimum spanning tree is a minimum spanning tree (MST) 
 * where the maximum vertex degree is limited to a certain constant k.
 * 
 *  Test your adaptation with randomly generated graphs of 100 nodes and 
 *  varying number of edges and degree constraint between 4 and 6.
 * */
public class TestWeightedGraph {

	public static void main(String[] args) {
		int[][] edges = {
				{0, 1, 3}, {0, 3, 2}, {0, 4, 6},
				{1, 2, 4}, 
				{2, 3, 7},
				{3, 4, 5}
				};
		
		Integer[] vertices = {0, 1, 2, 3, 4};

		WeightedGraph<Integer> graph = new WeightedGraph<>(vertices, edges, false);
		
		System.out.println("Edges for graph is: ");
		graph.printWeightedEdges();
	}

}
