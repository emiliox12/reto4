package model.data_structures;

public class MinPQ<K extends Comparable<K>, V> {

	ILista<NodoTS<K, V>> nodes;

	public MinPQ(int i) {
		nodes = new ArregloDinamico<NodoTS<K, V>>(i);
		nodes.addFirst(null);
	}

	public void insert(K key, V value) {
		NodoTS<K, V> elem = new NodoTS<K, V>(key, value);
		nodes.addLast(elem);
		swim(nodes, nodes.size());
	}

	protected void swim(ILista<NodoTS<K, V>> list, int pos) {
		boolean finished = false;
		while (pos > 1 && !finished) {
			NodoTS<K, V> parent = list.getElement(pos / 2);
			NodoTS<K, V> curr = list.getElement(pos);
			if (parent.getKey().compareTo(curr.getKey()) > 0) {
				list.exchange(pos / 2, pos);
			} else {
				finished = true;
			}
			pos = pos / 2;
		}

	}

	protected void sink(ILista<NodoTS<K, V>> list, int pos) {
		int size = size();
		boolean finished = false;
		while (2 * pos < size && !finished) {
			int left = 2 * pos;
			int right = left + 1;
			int minPos = pos;
			if (list.getElement(minPos).compareTo(list.getElement(left)) > 0) {
				minPos = left;
			}
			if (right <= size && list.getElement(minPos).compareTo(list.getElement(right)) > 0) {
				minPos = right;
			}
			if (pos == minPos) {
				finished = true;
			} else {
				list.exchange(pos, minPos);
				pos = minPos;
			}
		}

	}

	public boolean isEmpty() {
		return nodes.size() > 1;
	}

	public NodoTS<K, V> delMin() {
		NodoTS<K, V> toReturn = null;
		if (size() > 1) {
			nodes.exchange(1, nodes.size());
			toReturn = nodes.removeLast();
			sink(nodes, 1);
		} else if (size() > 0) {
			toReturn = nodes.removeLast();
		}
		return toReturn;
	}

	public V min() {
		return nodes.size() > 1 ? nodes.getElement(1).value : null;
	}

	public int size() {
		return nodes.size() - 1;
	}
}
