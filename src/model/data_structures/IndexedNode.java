package model.data_structures;

public class IndexedNode<K extends Comparable<K>, KInd extends Comparable<KInd>, V> extends NodoTS<K, V> {

	KInd indexedKey;
	
	public IndexedNode(K key, KInd indexedKey, V value) {
		super(key, value);
		this.indexedKey = indexedKey;
	}

	public KInd getIndexedKey() {
		return indexedKey;
	}
	public void setIndexedKey(KInd indexedKey) {
		this.indexedKey = indexedKey;
	}
}
