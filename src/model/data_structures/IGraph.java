package model.data_structures;

public interface IGraph <K extends Comparable<K>, V extends Comparable<V>>{
	
	public void incertVertex(K id, V value);

	void addEdge(K id1, K id2, float w);

}
