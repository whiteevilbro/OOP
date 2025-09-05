package sys.pro;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

public class HeapSortTest {
    private static void sortTest(int[] array) {
        int[] sorted = array.clone();
        Arrays.sort(sorted);
        HeapSort.sort(array);
        assertArrayEquals(array, sorted);
    }

    @Test
    public void testSort() {
        int[] array = new int[]{5, 7, 8, 2, 1, 6, 3, 9, 4};
        sortTest(array);
    }

    @Test
    public void testSortEmpty() {
        int[] array = new int[]{};
        sortTest(array);
    }

    @Test
    public void testSortSingleElement() {
        int[] array = new int[]{1};
        sortTest(array);
    }

    @Test
    public void testSortNull() {
        try {
            HeapSort.sort(null);
        } catch (NullPointerException _) {
            return;
        }
        fail();
    }

    @Test
    public void testSortFullRange() {
        int[] array = new int[]{-2147483648, 2147483647, -1, 0, 1, 65535, -65536};
        sortTest(array);
    }

    @Test
    public void testSortMultiple() {
        int[] array = new int[]{3, 1, 2, 1, 2, 1, 2, 3};
        sortTest(array);
    }

    @Test
    public void testSortRandom() {
        int[] array = generateArray(431136);
        sortTest(array);
    }

    private static int[] generateArray(int size) {
        int[] array = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt();
        }
        return array;
    }
}
