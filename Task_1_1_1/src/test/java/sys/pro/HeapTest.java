package sys.pro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

/**
 * Heap tests class.
 */
public class HeapTest {
    /**
     * Minimal element extraction test case.
     */
    @Test
    void testHeap() {
        int[] array = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        Heap heap = new Heap(array);
        for (int j : array) {
            assertEquals(j, heap.pop());
        }
    }

    /**
     * Null pointer test case.
     */
    @Test
    void testHeapNull() {
        try {
            Heap heap = new Heap(null);
        } catch (NullPointerException ex) {
            return;
        }
        fail();
    }

    /**
     * Empty heap building and extracting test case.
     */
    @Test
    void testHeapEmpty() {
        try {
            int[] a = new int[0];
            Heap heap = new Heap(a);
            heap.pop();
        } catch (NoSuchElementException ex) {
            return;
        }
        fail();
    }
}
