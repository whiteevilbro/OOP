package sys.pro;

import java.util.Scanner;

/**
 * Main entrypoint class of application.
 */
public class Main {

    /**
     * Main entrypoint of application.
     *
     * @param argv array of command line arguments
     */
    public static void main(String[] argv) {
        Scanner in = new Scanner(System.in);
        System.out.println("Input size of array:");
        int n = in.nextInt();

        System.out.println("Input array:");
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = in.nextInt();
        }

        HeapSort.sort(array);

        System.out.println("Sorted array:");
        System.out.print(array[0]);
        for (int i = 1; i < n; i++) {
            System.out.printf(" %d", array[i]);
        }
        System.out.println();
    }
}
