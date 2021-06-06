package model.data_structures;

public class NoDirectedGraph<K extends Comparable<K>, V extends Comparable<V>> implements IGraph<K, V> {

	private ITablaSimbolos<K, Vertex<K, V>> vertices;
	private ITablaSimbolos<String, Edge<K, V>> edges;

	public NoDirectedGraph(int i) {
		vertices = new TablaHashLinearProbing<K, Vertex<K, V>>(i);
		edges = new TablaHashLinearProbing<String, Edge<K, V>>(i);
	}

	@Override
	public void incertVertex(K id, V value) {
		Vertex<K, V> vertex = new Vertex<>(id, value);
		vertices.put(id, vertex);
	}

	@Override
	public void addEdge(K id1, K id2, float w) {
		Vertex<K, V> origin = vertices.get(id1);
		Vertex<K, V> destination = vertices.get(id2);
		Edge<K, V> edge = new Edge<>(origin, destination, w);
		if (origin != null) {
			origin.addEdge(edge);
		}
		Edge<K, V> edge2 = new Edge<>(destination, origin, w);
		if (destination != null) {
			destination.addEdge(edge2);
		}
		edges.put("" + id1 + id2, edge);
		edges.put("" + id2 + id1, edge2);
	}

	public void dfs(K id) {
		Vertex<K, V> vertex = vertices.get(id);
		vertex.dfs(null);
	}

	boolean containsVertex(K id) {
		return vertices.get(id) != null;
	}

	public int numVertices() {
		return vertices.size();
	}

	int numEdges() {
		return edges.size();
	}

	public Vertex<K, V> getVertex(K id) {
		return vertices.get(id);
	}

	public Edge<K, V> getEdge(K idS, K idD) {
		return edges.get("" + idS + idD);
	}

	ILista<Edge<K, V>> adjacentEdges(K id) {
		Vertex<K, V> vertex = vertices.get(id);
		return vertex.edges;
	}

	ILista<Vertex<K, V>> adjacentVertex(K id) {
		Vertex<K, V> vertex = vertices.get(id);
		return vertex.vertices();
	}

	int indegree(K vertex) {
		Vertex<K, V> vtx = vertices.get(vertex);
		return vtx.indegree();
	}

	int outdegree(K vertex) {
		Vertex<K, V> vtx = vertices.get(vertex);
		return vtx.outdegree();
	}

	ILista<Edge<K, V>> edges() {
		return edges.valueSet();
	}

	public ILista<Vertex<K, V>> vertices() {
		return vertices.valueSet();
	}

	public ILista<Edge<K, V>> mstPrimLazy(K idOrigin) {
		Vertex<K, V> originVertex = getVertex(idOrigin);
		ILista<Edge<K, V>> retortno = originVertex.mstPrimLazy(edges.getMaxSize());
		unmark();
		return retortno;
	}

	void unmark() {
		ILista<Vertex<K, V>> vertices = vertices();
		for (int i = 0; i < vertices.size(); i++) {
			Vertex<K, V> vxt = vertices.getElement(i);
			vxt.unmark();
		}
	}

	public ITablaSimbolos<K, NodoTS<Float, Edge<K, V>>> minPathTree(K idOrigin) {
		Vertex<K, V> start = getVertex(idOrigin);
		ITablaSimbolos<K, NodoTS<Float, Edge<K, V>>> minPathTree = start.minPathTree(vertices.size());
		unmark();
		return minPathTree;
	}
	
	public NodoTS<Integer, ITablaSimbolos<K, Integer>> getSCC() {
		int componentCount = 0;
		ITablaSimbolos<K, Integer> SCC = new TablaHashLinearProbing<K, Integer>(vertices().size());
		ILista<Vertex<K,V>> vertices = vertices();
		for (int i = 0; i < vertices.size(); i++) {
			Vertex<K,V> vertex = vertices.getElement(i);
			if (!vertex.isMarked()) {
				componentCount++;
				vertex.addToScc(SCC, componentCount);
			}
		}
		return new NodoTS<Integer, ITablaSimbolos<K,Integer>>(componentCount, SCC);
	}

	public ILista<Edge<K, V>> minPath(K idOrigin, K idDestination) {
		Vertex<K, V> start = getVertex(idOrigin);
		if (start == null) {
			return null;
		}
		ITablaSimbolos<K, NodoTS<Float, Edge<K, V>>> tree = start.minPathTree(vertices.size());
		unmark();
		ILista<Edge<K, V>> path = new ListaEncadenada<Edge<K, V>>();
		K searchId = idDestination;
		NodoTS<Float, Edge<K, V>> curr;
		while ((curr = tree.get(searchId)) != null && curr.getValue() != null) {
			path.addLast(curr.getValue());
			searchId = curr.getValue().getOrigin().getKey();
		}
		return path;
	}
}
