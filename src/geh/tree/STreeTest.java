package geh.tree;

import java.util.Iterator;
import java.util.Random;

import junit.framework.TestCase;

/**
 * 二叉排序树测试
 * 
 * @author 小e
 * 
 *         2010-4-9 下午08:32:55
 */
public class STreeTest extends TestCase {

	STree<Integer> tree = new STree<Integer>();
	int[] data = new int[] { 40, 30, 65, 25, 35, 50, 10, 26, 33, 29, 34 };

	@Override
	protected void setUp() throws Exception {
		Random r = new Random();
		int value;
		int len = data.length;
		for (int i = 0; i < len; i++) {
			value = r.nextInt(100);
			// tree.add(value);
			tree.add(data[i]);
		}

	}

	public void testSTree() {
		LNR(tree.getRoot());
	}

	public void testFind() {
		for (int i = 0; i < 15; i++) {
			System.out.println("i" + i + "\t" + tree.contains(i));
		}

	}

	/**
	 * 测试删除节点
	 */
	public void testDelete() {
		System.out.println("删除前:");
		LNR(tree.getRoot());
		tree.remove(40);
		System.out.println("删除节点30：");
		LNR(tree.getRoot());
	}

	public void testMaxAndMin() {
		System.out.println("max:" + tree.maxValue());
		System.out.println("min:" + tree.minValue());
	}

	/**
	 * 测试迭代
	 */
	public void testTreeIterator() {
		for (Iterator iterator = tree.iterator(); iterator.hasNext();) {
			Integer i = (Integer) iterator.next();
			System.out.print(i + " ");
		}
	}

	/**
	 * 中序排列
	 * 
	 * @param node
	 */
	public void LNR(STreeNode node) {
		if (node == null) {
			return;
		}
		LNR(node.getLeft());
		visit(node);
		LNR(node.getRight());
	}

	private void visit(STreeNode node) {
		System.out.print(node.getNodeValue() + " ");
	}

}