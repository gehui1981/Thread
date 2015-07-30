package geh.tree;

public class STreeNode<T> {

	private T nodeValue;
	private STreeNode<T> left;
	private STreeNode<T> right;
	private STreeNode<T> parent;

	public STreeNode(T value) {
		this.nodeValue = value;
	}

	public STreeNode(T nodeValue, STreeNode<T> parent) {
		this.nodeValue = nodeValue;
		this.parent = parent;
	}

	public T getNodeValue() {
		return nodeValue;
	}

	public void setNodeValue(T nodeValue) {
		this.nodeValue = nodeValue;
	}

	public STreeNode<T> getLeft() {
		return left;
	}

	public void setLeft(STreeNode<T> left) {
		this.left = left;
	}

	public STreeNode<T> getRight() {
		return right;
	}

	public void setRight(STreeNode<T> right) {
		this.right = right;
	}

	public STreeNode<T> getParent() {
		return parent;
	}

	public void setParent(STreeNode<T> parent) {
		this.parent = parent;
	}

}