package sys.pro;

/**
 * This class is for implementataion of heap sorting algorithm.
 */
public class HeapSort {
    /**
     * Implementation of heap sorting algorithm.
     * Have O(n * log(n)) time complexity and O(n) size complexity.
     *
     * @param array array of ints to be sorted. Must not be null.
     */
    static void sort(int[] array) throws NullPointerException {
        Heap heap = new Heap(array);
        for (int i = 0; i < array.length; i++) {
            array[i] = heap.pop();
        }
    }
}
