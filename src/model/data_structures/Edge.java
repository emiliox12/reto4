package model.data_structures;

public class Edge<K extends Comparable<K>, V> implements Comparable<Edge<K, V>> {

	Vertex<K, V> origin;
	Vertex<K, V> destination;
	float weight;

	public Edge(Vertex<K, V> origin, Vertex<K, V> destination, float weigth) {
		this.origin = origin;
		this.destination = destination;
		this.weight = weigth;
	}

	public Vertex<K, V> getOrigin() {
		return origin;
	}

	public void setOrigin(Vertex<K, V> origin) {
		this.origin = origin;
	}

	public Vertex<K, V> getDestination() {
		return destination;
	}

	public void setDestination(Vertex<K, V> destination) {
		this.destination = destination;
	}

	public float getWeight() {
		return weight;
	}

	@Override
	public int compareTo(Edge<K, V> arg0) {
		return this.origin.compareTo(arg0.origin) + destination.compareTo(arg0.destination);
	}
	
	public void setWeight(float weight) {
		this.weight = weight;
	}
	
	@Override
	public String toString() {
		return origin.toString() + " <-> " + destination.toString();
	}

}
