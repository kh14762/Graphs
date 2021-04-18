package graphs;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import graphs.WeightedGraph.MST;

/* 
 * A degree-constrained minimum spanning tree is a minimum spanning tree (MST) 
 * where the maximum vertex degree is limited to a certain constant k.
 * 
 *  Test your adaptation with randomly generated graphs of 100 nodes and 
 *  varying number of edges and degree constraint between 4 and 6.
 * */
public class TestWeightedGraph {

	public static void main(String[] args) {
		
		List<WeightedEdge> edges = new ArrayList<>();
		edges.add(new WeightedEdge(0, 1, 5));
		edges.add(new WeightedEdge(0, 3, 4));
		edges.add(new WeightedEdge(0, 4, 3));
		edges.add(new WeightedEdge(1, 2, 3));
		edges.add(new WeightedEdge(2, 3, 2));
		edges.add(new WeightedEdge(3, 4, 6));
		
		List<Integer> vertices = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4));
		
		System.out.println("Random Graph: ");
		WeightedGraph<Integer> graphRandom = new WeightedGraph<>(vertices, edges, false);
		graphRandom.printWeightedEdges();
		graphRandom.remove(3);
		System.out.println();
		graphRandom.printWeightedEdges();
		
//		MST mst = graphRandom.getMinimumSpanningTree();
//		System.out.println();
//		System.out.println("The mst cost is: " + mst.getTotalWeight());
//		System.out.println();
//		mst.printTree(); 
	}
	
	
	public static WeightedGraph<Integer> RNGgraph(int numVertices, int numEdges) {
		List<WeightedEdge> edges = new ArrayList<>();
		
		for (int i = 0; i < numVertices; i++) {			
				int randVertice = ThreadLocalRandom.current().nextInt(0, numVertices);
				int randWeight = ThreadLocalRandom.current().nextInt(1, 11);
				edges.add(new WeightedEdge(i, randVertice, randWeight));							
		}
			
		return new WeightedGraph<Integer>(edges, numVertices, false);
	}
	
	
	
	
	
//	create method that generates a random graph
//	accepts: number of vertices, degree constraint min & max
//	public static WeightedGraph<Integer> RNGgraph(int numVertices, int minK, int maxK) {
//		List<WeightedEdge> edges = new ArrayList<>();
//		// Iterate to the number of Vertices
//		for (int i = 0; i < numVertices; i++) {
//			// iterate up to the random constraint (between 4 and 6)
//			// Random constraint k (between 4 - 6)
//			int randK = ThreadLocalRandom.current().nextInt(minK, maxK + 1); 
//			int verticeCount = 0;
//			for (int j = 0; j < randK; j++) {
//				// Gives a random vertice between 0 & numVertices
//				int randVertice = ThreadLocalRandom.current().nextInt(0, numVertices);
//				// Gives a random weight between 0 and 10
//				int randWeight = ThreadLocalRandom.current().nextInt(0, 11);
//				if (j > 0 && (edges.get(i).v == numVertices)) {
//					verticeCount++;
//				}
//				if (verticeCount < randK) {
//					edges.add(new WeightedEdge(i, randVertice, randWeight));
//				}
//			}
//		}
//
//		return new WeightedGraph<Integer>(edges, numVertices, false);
//	}

}
