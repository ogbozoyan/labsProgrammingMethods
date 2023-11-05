package org.example.hw1;

import java.io.*;
import java.util.Arrays;

/**
 * @author ogbozoyan
 * @since 03.11.2023
 */
public class Merge {
    public static void main(String[] args) throws IOException {

        BufferedReader scanner = new BufferedReader(new FileReader("src/main/java/org/example/hw1/input.txt"));
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/org/example/hw1/output.txt"));
//        BufferedReader scanner = new BufferedReader(new FileReader("input.txt"));
//        BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));

        int sizeFirst = Integer.parseInt(scanner.readLine());
        int[] first = new int[sizeFirst];
        String firstStringArray = scanner.readLine();
        if (firstStringArray != null && !firstStringArray.isBlank()) {
            first = Arrays.stream(firstStringArray.split(" ")).mapToInt(Integer::parseInt).toArray();
        }




        long startTime = System.currentTimeMillis();
        mergeSort(first, 0, first.length);
        for (int i : first) {
            writer.write(i + " ");
        }
        long endTime = System.currentTimeMillis();
        writer.close();
        System.out.println("Total execution time: " + (endTime - startTime) + "ms");

    }

    private static void mergeSort(int[] array, int left, int right) {
        if (left + 1 >= right) {
            return;
        }
        int mid = (left + right) / 2;


        mergeSort(array, left, mid);
        mergeSort(array, mid, right);
        merge(array, left, mid, right);

    }

    public static void merge(int[] array, int left, int mid, int right) {
        int i = 0;
        int j = 0;
        int[] buf = new int[right - left];
        //mid - middle of given array, left of array
        while ((left + i < mid) && (mid + j < right)) {
            if (array[left + i] < array[mid + j]) {
                buf[i + j] = array[left + i];
                i++;
            } else {
                buf[i + j] = array[mid + j];
                j++;
            }
        }

        while (left + i < mid) {
            buf[i + j] = array[left + i];
            i++;
        }
        while (mid + j < right) {
            buf[i + j] = array[mid + j];
            j++;
        }
        for (int k = 0; k < i + j; k++) {
            array[left + k] = buf[k];
        }
    }
}
