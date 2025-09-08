package sys.pro;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Test;


/**
 * Heap sort tests class.
 */
public class HeapSortTest {

    private static void sortTest(int[] array) {
        int[] sorted = array.clone();
        Arrays.sort(sorted);
        HeapSort.sort(array);
        assertArrayEquals(array, sorted);
    }

    private static int[] generateArray(int size) {
        int[] array = new int[size];
        Random random = new Random(size);
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt();
        }
        return array;
    }

    /**
     * Basic sorting test case.
     */
    @Test
    public void testSort() {
        int[] array = new int[]{5, 7, 8, 2, 1, 6, 3, 9, 4};
        sortTest(array);
    }

    /**
     * Empty array sorting test case.
     */
    @Test
    public void testSortEmpty() {
        int[] array = new int[]{};
        sortTest(array);
        assertEquals(0, array.length);
    }

    /**
     * Single element array sorting test case.
     */
    @Test
    public void testSortSingleElement() {
        int[] array = new int[]{1};
        sortTest(array);
    }

    /**
     * Null pointer test case.
     */
    @Test
    public void testSortNull() {
        assertThrows(NullPointerException.class, () -> HeapSort.sort(null));
    }

    /**
     * Big negative and positive numbers sorting test case.
     */
    @Test
    public void testSortFullRange() {
        int[] array = new int[]{-2147483648, 2147483647, -1, 0, 1, 65535, -65536};
        sortTest(array);
    }

    /**
     * Multiple equal elements sorting test case.
     */
    @Test
    public void testSortMultiple() {
        int[] array = new int[]{3, 1, 2, 1, 2, 1, 2, 3};
        sortTest(array);
    }

    /**
     * Big random generated test case.
     */
    @Test
    public void testSortRandom() {
        int[] array = generateArray(431136);
        sortTest(array);
    }

    /**
     * Constructor test case.
     */
    @Test
    public void testConstructor() {
        HeapSort heapSort = new HeapSort();
    }
}
