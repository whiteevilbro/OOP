package sys.pro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

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
        assertThrows(NullPointerException.class, () -> new Heap(null));
    }

    /**
     * Empty heap building and extracting test case.
     */
    @Test
    void testHeapEmpty() {
        assertThrows(NoSuchElementException.class, () -> (new Heap(new int[0])).pop());
    }
}
