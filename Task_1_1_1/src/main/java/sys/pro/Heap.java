package sys.pro;

import java.util.NoSuchElementException;

/**
 * This class is a min binary heap.
 */
public class Heap {

    protected int[] storage;
    protected int len;

    /**
     * Builds heap from given array.
     *
     * @param array an array of ints, from which heap should be builded. Must not be null.
     */
    public Heap(int[] array) throws NullPointerException {
        if (array == null) {
            throw new NullPointerException();
        } else {
            storage = array.clone();
            len = storage.length;
            heapify();
        }
    }

    /**
     * Removes minimal element from heap and returns it.
     *
     * @return minimal element from heap.
     * @throws NoSuchElementException if this heap is empty. Must not be null.
     */
    public int pop() throws NoSuchElementException {
        if (len <= 0) {
            throw new NoSuchElementException();
        }
        final int ret = storage[0];
        len--;
        storage[0] = storage[len];
        sieve_down(0);
        return ret;
    }

    private void heapify() {
        for (int i = len / 2; i >= 0; i--) {
            sieve_down(i);
        }
    }

    private void sieve_down(int index) {
        int t;
        while (index < len) {
            t = storage[index];
            if (2 * index + 2 < len && storage[2 * index + 2] < t
                && storage[2 * index + 2] < storage[2 * index + 1]) {
                storage[index] = storage[2 * index + 2];
                index = 2 * index + 2;
            } else if (2 * index + 1 < len && storage[2 * index + 1] < t) {
                storage[index] = storage[2 * index + 1];
                index = 2 * index + 1;
            } else {
                break;
            }
            storage[index] = t;
        }
    }
}
