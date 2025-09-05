package sys.pro;

import java.util.NoSuchElementException;

public class Heap {
    protected int[] storage;
    protected int len;

    public Heap(int[] array) throws NullPointerException {
        if (array == null) {
            throw new NullPointerException();
        } else {
            storage = array.clone();
            len = storage.length;
            heapify();
        }
    }

    public int pop() throws NoSuchElementException {
        if (len <= 0) {
            throw new NoSuchElementException();
        }
        int ret = storage[0];
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
