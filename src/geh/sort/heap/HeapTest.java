package geh.sort.heap;

import java.util.Arrays;

import junit.framework.TestCase;

public class HeapTest extends TestCase {
	Heap<Integer> heap;

	@Override
	protected void setUp() throws Exception {
	}

	public void testAddHeap() {
		Integer[] arr = new Integer[10];
		for (int i = 0; i < 10; i++) {
			arr[i] = i + 1;
		}
		
		System.out.println("initiate arr : " + Arrays.toString(arr));
		heap = new Heap<Integer>(arr, Heap.ASC);
		System.out.println("heap arr : " + heap);
		heap.heapSort();
		System.out.println("heap sort arr : " + heap);

	}

}