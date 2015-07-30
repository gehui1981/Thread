package geh.sort.heap;

import java.util.Arrays;
import java.util.Comparator;

public class Heap<T> {
	private T[] arr;
	private int heapSize;
	public static int DESC = 0, ASC = 1;
	public Comparator<T> comp;

	public Heap(T[] arr, int sort) {
		this.arr = arr;
		heapSize = arr.length;
		if (sort == DESC) {
			comp = less;
		} else {
			comp = greater;
		}
		makeHeap();
	}

	public Heap(T[] arr) {
		this.arr = arr;
		heapSize = arr.length;
		comp = less;
		makeHeap();
	}

	public T popHeap(int i) {
		T t = arr[0];
		arr[0] = arr[i];
		arr[i] = t;
		adjustHeap(0, i);
		return t;
	}

	// 调整堆
	public void adjustHeap(int first, int last) {
		int curr, child;
		T target;
		curr = first;
		child = curr * 2 + 1;
		target = arr[first];
		while (child < last) {
			
			System.out.println("adjustHeap : last=" + last + " child = " + child + " arr : " + Arrays.toString(arr));
			// 选出子节点较大者
			if ((child + 1) < last
					&& (comp.compare(arr[child + 1], arr[child]) > 0)) {
				child++;
			}
			if (comp.compare(arr[child], target) > 0) {
				// 如果子节点大于父节点，交换
				arr[curr] = arr[child];
				curr = child;
				child = child * 2 + 1;
			} else {
				break;
			}
		}
		arr[curr] = target;
	}

	/**
	 * 构造堆
	 */
	public void makeHeap() {
		int last, curr;
		last = heapSize;
		curr = (heapSize - 2) / 2;
		// 最后一个节点
		while (curr >= 0) {
			System.out.println("makeHeap : curr = " + curr + " arr : " + Arrays.toString(arr));
			adjustHeap(curr, last);
			curr--;
		}
	}

	/**
	 * 堆排序
	 */
	public void heapSort() {
		int len = heapSize;
		for (int i = len - 1; i >= 0; i--) {
			popHeap(i);
		}
	}

	@Override
	public String toString() {
		return Arrays.toString(arr);
	}

	private Comparator<T> greater = new Comparator<T>() {
		@Override
		public int compare(T o1, T o2) {
			return ((Comparable<T>) o1).compareTo(o2);
		}
	};
	private Comparator<T> less = new Comparator<T>() {
		@Override
		public int compare(T o1, T o2) {
			return ((Comparable<T>) o2).compareTo(o1);
		}
	};
}