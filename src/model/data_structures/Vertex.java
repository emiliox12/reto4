package model.data_structures;

public class Vertex<K extends Comparable<K>, V> implements Comparable<Vertex<K, V>> {

	private K key;
	private V value;
	private boolean marked;
	public ILista<Edge<K, V>> edges;

	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}

	public void mark() {
		marked = true;
	}

	public void unmark() {
		marked = false;
	}

	public boolean isMarked() {
		return marked;
	}

	public void addEdge(Edge<K, V> edge) {
		edges.addLast(edge);
	}

	public Vertex(K key, V value) {
		this.key = key;
		this.value = value;
		edges = new ArregloDinamico<Edge<K, V>>(10);
	}
	
	public ILista<Vertex<K, V>> getNeighbourVertices() {
		ILista<Vertex<K, V>> neighbourVertices = new ArregloDinamico<Vertex<K,V>>(edges.size());
		for (int i = 0; i < edges.size(); i++) {
			Edge<K, V> edge = edges.getElement(i);
			neighbourVertices.addLast(edge.destination);
		}
		return neighbourVertices;
	}

	@Override
	public int compareTo(Vertex<K, V> arg0) {
		return this.key.compareTo(arg0.key);
	}

	public void dfs(Edge<K, V> edge) {
		mark();
		for (int i = 0; i < edges.size(); i++) {
			Vertex<K, V> dest = edges.getElement(i).getDestination();
			if (!dest.isMarked()) {
				dest.dfs(edge);
			}
		}
	}
	
	public void addToScc(ITablaSimbolos<K, Integer> SCC, int componentInt) {
		mark();
		SCC.put(key, componentInt);
		for (int i = 0; i < edges.size(); i++) {
			Vertex<K, V> dest = edges.getElement(i).getDestination();
			if (!dest.isMarked()) {
				dest.addToScc(SCC, componentInt);
			}
		}
	}

	public void bfs(Edge<K, V> edge) {
		ArregloDinamico<Vertex<K, V>> queue = new ArregloDinamico<Vertex<K, V>>(10);
		mark();
		queue.addLast(this);
		while (queue.lastElement() != null) {
			Vertex<K, V> current = queue.removeLast();
			for (int i = 0; i < edges.size(); i++) {
				Vertex<K, V> dest = current.edges.getElement(i).getDestination();
				if (!dest.isMarked()) {
					dest.mark();
					queue.addLast(dest);
				}
			}
		}
	}

	public int outdegree() {
		return edges.size();
	}

	public int indegree() {
		return edges.size();
	}

	public Edge<K, V> getEdge(K vertex) {
		for (int i = 0; i < edges.size(); i++) {
			Edge<K, V> curr = edges.getElement(i);
			if (curr.destination.key.compareTo(vertex) == 0) {
				return curr;
			}
		}
		return null;
	}

	public ILista<Vertex<K, V>> vertices() {
		ILista<Vertex<K, V>> vertexes = new ArregloDinamico<Vertex<K, V>>(edges.size());
		for (int i = 0; i < edges.size(); i++) {
			Edge<K, V> curr = edges.getElement(i);
			vertexes.addLast(curr.destination);
		}
		return vertexes;
	}

	public ILista<Edge<K, V>> mstPrimLazy(int i) {
		ILista<Edge<K, V>> mst = new ArregloDinamico<Edge<K, V>>(i);
		MinPQ<Float, Edge<K, V>> qeue = new MinPQ<>(i);
		addEdgesToMinPq(qeue, this);
		while (!qeue.isEmpty()) {
			Edge<K, V> curr = qeue.delMin().getValue();
			Vertex<K, V> dest = curr.getDestination();
			if (!dest.isMarked()) {
				mst.addLast(curr);
				;
				addEdgesToMinPq(qeue, dest);
			}
		}
		return mst;
	}

	private void addEdgesToMinPq(MinPQ<Float, Edge<K, V>> qeue, Vertex<K, V> origin) {
		origin.mark();
		for (int i = 0; i < origin.edges.size(); i++) {
			Edge<K, V> cur = origin.edges.getElement(i);
			qeue.insert(cur.weight, cur);
		}
	}

	@Override
	public String toString() {
		return key.toString();
	}

	public ITablaSimbolos<K, NodoTS<Float, Edge<K, V>>> minPathTree(int i) {
		ITablaSimbolos<K, NodoTS<Float, Edge<K, V>>> resultTable = new TablaHashLinearProbing<>(i);
		MinIndexedPQ<Float, K, Edge<K, V>> indexedQueue = new MinIndexedPQ<>(i);
		resultTable.put(this.key, new NodoTS<Float, Edge<K, V>>(0f, null));
		relaxDijksta(resultTable, indexedQueue, this, 0f);
		while (!indexedQueue.isEmpty()) {
			NodoTS<Float, Edge<K, V>> current = indexedQueue.delMin();
			Edge<K, V> curredge = current.getValue();
			float currWeight = current.getKey();
			relaxDijksta(resultTable, indexedQueue, curredge.getDestination(), currWeight);
		}
		return resultTable;
	}

	private void relaxDijksta(ITablaSimbolos<K, NodoTS<Float, Edge<K, V>>> resultTable,
			MinIndexedPQ<Float, K, Edge<K, V>> indexedQeue, Vertex<K, V> vertex, Float accWeight) {
		vertex.mark();
		for (int i = 0; i < vertex.edges.size(); i++) {
			Edge<K, V> currEdge = vertex.edges.getElement(i);
			Vertex<K, V> destination = currEdge.getDestination();
			float weight = currEdge.getWeight();
			if (!destination.isMarked()) {
				NodoTS<Float, Edge<K, V>> destinationArrival = resultTable.get(destination.key);
				if (destinationArrival == null) {
					resultTable.put(destination.key, new NodoTS<>(accWeight + weight, currEdge));
					indexedQeue.insert(accWeight + weight, destination.key, currEdge);
				} else if (destinationArrival.getKey() > (accWeight + weight)) {
					destinationArrival.setKey(accWeight + weight);
					destinationArrival.setValue(currEdge);
					indexedQeue.changePriority(destination.getKey(), accWeight + weight, currEdge);
				}
			}
		}
	}
}
