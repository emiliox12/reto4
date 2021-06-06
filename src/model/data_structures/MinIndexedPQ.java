package model.data_structures;

public class MinIndexedPQ<K extends Comparable<K>, KInd extends Comparable<KInd>, V> extends MinPQ<K, V> {

	public MinIndexedPQ(int i) {
		super(i);
	}

	public void insert(K key, KInd indexedKey, V value) {
		nodes.addLast(new IndexedNode<K, KInd, V>(key, indexedKey, value));
		swim(nodes, size());
	}

	public void changePriority(KInd indexedKey, K newKey, V value) {
		int posicionBuscado = -1;
		for (int i = 1; i <= nodes.size() && posicionBuscado == -1; i++) {
			@SuppressWarnings("unchecked")
			IndexedNode<K, KInd, V> actual = (IndexedNode<K, KInd, V>) nodes.getElement(i);
			if (actual.indexedKey.compareTo(indexedKey) == 0) {
				posicionBuscado = i;
			}
		}
		if (posicionBuscado == -1) {
			return;
		}
		if (newKey.compareTo(nodes.getElement(posicionBuscado).getKey()) > 0) {
			nodes.getElement(posicionBuscado).setKey(newKey);
			nodes.getElement(posicionBuscado).setValue(value);
			sink(nodes, posicionBuscado);
		} else if (newKey.compareTo(nodes.getElement(posicionBuscado).getKey()) < 0) {
			nodes.getElement(posicionBuscado).setKey(newKey);
			nodes.getElement(posicionBuscado).setValue(value);
			swim(nodes, posicionBuscado);
		}
	}
}
