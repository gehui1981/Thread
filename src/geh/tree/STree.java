package geh.tree;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * ����������
 * 
 * @author Сe
 * 
 *         2010-4-9 ����08:09:14
 */
public class STree<T> implements Collection<T> {
	private int treeSize;
	private STreeNode<T> root;

	public STree() {
		this.treeSize = 0;
		root = null;
	}

	@Override
	public boolean add(T t) {
		STreeNode<T> node = root, newNode, parent = null;
		int result = 0;
		while (node != null) {
			parent = node;
			result = ((Comparable<T>) node.getNodeValue()).compareTo(t);
			if (result == 0) {
				return false;
			}
			if (result > 0) {// ����
				node = node.getLeft();
			} else {
				node = node.getRight();
			}
		}
		newNode = new STreeNode<T>(t);
		if (parent == null) {// ��һ��������Ԫ��
			root = newNode;
		} else if (result > 0) {
			parent.setLeft(newNode);
		} else {
			parent.setRight(newNode);
		}
		newNode.setParent(parent);
		treeSize++;
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		boolean flag = false;
		for (Iterator iterator = c.iterator(); iterator.hasNext();) {
			T t = (T) iterator.next();
			add(t);
			flag = true;
		}
		return flag;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean contains(Object o) {
		if (findNode((T) o) != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for (Iterator iterator = c.iterator(); iterator.hasNext();) {
			T t = (T) iterator.next();
			if (findNode(t) == null) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isEmpty() {
		return treeSize == 0;
	}

	@Override
	public Iterator<T> iterator() {
		return new IteratorImpl();
	}

	@Override
	public boolean remove(Object o) {
		STreeNode<T> node = findNode((T) o);
		if (node == null) {
			return false;
		}
		treeSize--;
		return removeNode(node);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		return treeSize;
	}

	@Override
	public Object[] toArray() {
		Object[] array = new Object[treeSize];
		int index = 0;
		for (Iterator iterator = this.iterator(); iterator.hasNext();) {
			T o = (T) iterator.next();
			array[index++] = o;
		}
		return array;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	private STreeNode<T> findNode(T item) {
		STreeNode<T> node = root;
		int value = 0;
		while (node != null) {
			value = ((Comparable<T>) node.getNodeValue()).compareTo(item);
			if (value == 0) {
				return node;
			} else if (value > 0) {
				node = node.getLeft();
			} else {
				node = node.getRight();
			}
		}
		return null;
	}

	/**
	 * ɾ���ڵ�
	 * 
	 * @param node
	 * @return
	 */
	private boolean removeNode(STreeNode<T> node) {
		STreeNode<T> pNode, rNode;// node�ĸ��ڵ㣬��node���ӽڵ�
		/**
		 * ���һ�����ڵ�������һ�ÿ�����
		 */
		if (node.getLeft() == null || node.getRight() == null) {
			pNode = node.getParent();

			if (node.getLeft() == null) {
				rNode = node.getRight();
			} else {
				rNode = node.getLeft();
			}

			if (rNode != null) {
				rNode.setParent(pNode);// ��r�ĸ��ڵ�ָ��p
			}

			if (pNode == null) {// nodeΪroot�ڵ�
				root = rNode;
			} else if (((Comparable<T>) pNode.getNodeValue()).compareTo(node
					.getNodeValue()) < 0) {
				pNode.setRight(node);
			} else {
				pNode.setLeft(node);
			}
		} else {
			rNode = node.getRight();
			pNode = node;

			/**
			 * �ҵ�node���������������node����Сֵ
			 */
			while (rNode.getLeft() != null) {
				pNode = rNode;
				rNode = rNode.getLeft();
			}
			/**
			 * ����ֵ
			 */
			node.setNodeValue(rNode.getNodeValue());

			if (pNode == node) {// node ����һ��� û�нڵ�
				node.setRight(rNode.getRight());//
			} else {
				pNode.setLeft(rNode.getRight());
			}
			/**
			 * ��rNode�������� �� parent �ӵ�pNode��
			 */
			if (rNode.getRight() != null) {
				rNode.getRight().setParent(pNode);
			}

		}
		return true;
	}

	/**
	 * ȡ���ֵ
	 * 
	 * @return
	 */
	public T maxValue() {
		STreeNode<T> node = root;
		while (node.getRight() != null) {
			node = node.getRight();
		}

		return node == null ? null : node.getNodeValue();
	}

	public T minValue() {
		STreeNode<T> node = root;
		while (node.getLeft() != null) {
			node = node.getLeft();
		}
		return node == null ? null : node.getNodeValue();
	}

	public STreeNode<T> getRoot() {
		return root;
	}

	public void setRoot(STreeNode<T> root) {
		this.root = root;
	}

	private class IteratorImpl implements Iterator<T> {
		STreeNode<T> nextNode, returnNode, pNode;

		public IteratorImpl() {
			nextNode = root;
			// ѡ����С�ڵ�
			if (nextNode != null) {
				while (nextNode.getLeft() != null) {
					nextNode = nextNode.getLeft();
				}
			}

		}

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return nextNode != null;
		}

		@Override
		public T next() {
			if (nextNode == null) {
				throw new NoSuchElementException("û�и�Ԫ��");
			}
			returnNode = nextNode;
			if (nextNode.getRight() != null) {
				nextNode = nextNode.getRight();
				while (nextNode.getLeft() != null) {
					nextNode = nextNode.getLeft();
				}
			} else {
				/**
				 * ���������Ϊ�գ����Ÿ��ڵ�����ã�ֱ���鵽��ǰ�ڵ�nextNode��ΪpNode����ڵ���ֹͣ
				 * pNode��Ϊ��Ϊ��һ��Ҫ���ʵĵ�
				 */

				pNode = nextNode.getParent();
				// ��pNode���Ǹ���� �� ��ǰ�ڵ�Ϊ pNode���ҽڵ�
				while (pNode != null && nextNode == pNode.getRight()) {
					nextNode = pNode;
					pNode = pNode.getParent();
				}

				nextNode = pNode;
			}

			return returnNode.getNodeValue();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("��֧�ָ÷��� ");
		}

	}
}