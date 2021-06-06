package model.data_structures;

public class NodoRBT<K extends Comparable<K>, V extends Comparable<V>> extends NodoTS<K, V> {

	private enum Color {
		RED, BLACK, ROOT,
	}

	public Color color;
	NodoRBT<K, V> right;
	NodoRBT<K, V> left;

	public NodoRBT(K key, V value) {
		super(key, value);
		color = Color.RED;
		// TODO Auto-generated constructor stub
	}
	public int getHeight() {
		int heightLeft = 0;
		int heightRight = 0;
		if (left != null) {
			heightLeft = left.getHeight();
		}
		if (right != null) {
			heightRight = right.getHeight();
		}
		int maxHeight = (heightLeft > heightRight) ? heightLeft : heightRight;
		return maxHeight + 1;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}

	public NodoRBT<K, V> getRight() {
		return right;
	}

	public void setRight(NodoRBT<K, V> right) {
		this.right = right;
	}

	public NodoRBT<K, V> getLeft() {
		return left;
	}

	public void setLeft(NodoRBT<K, V> left) {
		this.left = left;
	}

	public NodoRBT<K, V> rotateLeft() {
		NodoRBT<K, V> newRoot = this.right;

		Color currentNode = this.color;
		this.color = newRoot.color;
		newRoot.setColor(currentNode);

		this.right = newRoot.left;
		newRoot.left = this;
		return newRoot;
	}

	public NodoRBT<K, V> rotateRight() {
		NodoRBT<K, V> newRoot = this.left;

		Color currentNode = this.color;
		this.color = newRoot.color;
		newRoot.setColor(currentNode);

		this.left = newRoot.right;
		newRoot.right = this;
		return newRoot;
	}

	public NodoRBT<K, V> evaluateAndCorect() {
		NodoRBT<K, V> newRoot = this;
		if (right != null && right.color == Color.RED) {
			if (left != null && left.color == Color.RED) {
				right.setColor(Color.BLACK);
				left.setColor(Color.BLACK);
				this.setColor(Color.RED);
			} else {
				newRoot = rotateLeft();
			}
		}
		if (left != null && left.color == Color.RED && left.left != null && left.left.color == Color.RED) {
			newRoot = rotateRight();
		}
		return newRoot;
	}

	public NodoRBT<K, V> putRBT(K key, V value) {
		int comparation = key.compareTo(this.key);
		if (comparation == 0) {
			// error
		}
		else {
			if (comparation < 0) {
				if (left != null) {
					left = left.putRBT(key, value);
				} else {
					left = new NodoRBT<K, V>(key, value);
				}
			} else {
				if (right != null) {
					right = right.putRBT(key, value);
				} else {
					right = new NodoRBT<K, V>(key, value);
				}
			}
		}
		NodoRBT<K, V> newRoot = evaluateAndCorect();
		newRoot = newRoot.evaluateAndCorect();
		return newRoot;
	}
}
