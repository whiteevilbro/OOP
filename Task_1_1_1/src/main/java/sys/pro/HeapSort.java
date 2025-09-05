package sys.pro;


public class HeapSort {
    static void sort(int[] array) throws NullPointerException {
        Heap heap = new Heap(array);
        for (int i = 0; i < array.length; i++) {
            array[i] = heap.pop();
        }
    }
}
