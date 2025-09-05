package sys.pro;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.NoSuchElementException;

public class HeapTest {
    @Test
    void testHeap() {
        int[] array = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        Heap heap = new Heap(array);
        for (int j : array) {
            assertEquals(heap.pop(), j);
        }
    }

    @Test
    void testHeapNull() {
        try {
            Heap heap = new Heap(null);
        } catch (NullPointerException _) {
            return;
        }
        fail();
    }

    @Test
    void testHeapEmpty() {
        try {
            int[] a = new int[0];
            Heap heap = new Heap(a);
            heap.pop();
        } catch (NoSuchElementException _) {
            return;
        }
        fail();
    }
}
