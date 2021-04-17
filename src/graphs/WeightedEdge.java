package graphs;

public class WeightedEdge extends Edge implements Comparable<WeightedEdge> {
	public double weight;
	
	public WeightedEdge(int u, int v, double weight) {
		super(u, v);
		this.weight = weight;
	}

	@Override
	public int compareTo(WeightedEdge edge) {
		if (weight > edge.weight) {
			return 1;
		} else if (weight == edge.weight) {
			return 0;
		} else {
			return -1;
		}
	}

}
