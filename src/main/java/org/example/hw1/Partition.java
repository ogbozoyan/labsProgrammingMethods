package org.example.hw1;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * @author ogbozoyan
 * @since 02.11.2023
 */
public class Partition {

    public static void main(String[] args) {

        try {
            Scanner scanner = new Scanner(System.in);

            String n = scanner.nextLine();

            int[] array;
            String lineWithArray = scanner.nextLine();
            if (!lineWithArray.isBlank()) {
                String[] s = lineWithArray.split(" ");
                array = Arrays.stream(s).mapToInt(Integer::parseInt).toArray();
            } else {
                array = new int[Integer.parseInt(n)];
            }

            quickSort(array, 0, array.length - 1);

            for (int i : array) {
                System.out.print(i + " ");
            }

        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    public static void quickSort(int[] array, int leftIndex, int rightIndex) {
        if (leftIndex < rightIndex) {
            int partition = partition(array, leftIndex, rightIndex);
            quickSort(array, leftIndex, partition);
            quickSort(array, partition + 1, rightIndex);
        }
    }

    private static int partition(int[] array, int leftIndex, int rightIndex) {
        int pivot = array[new Random().nextInt(rightIndex-leftIndex)+leftIndex];
        int i = leftIndex;
        int j = rightIndex;
        while (i <= j) {
            while (array[i] < pivot) {
                i++;
            }
            while (array[j] > pivot) {
                j--;
            }
            if (i >= j){
                break;
            }
            swap(array, i++, j--);
        }
        return j;
    }


    public static void swap(int[] arr, int i, int j) {
        int buf = arr[i];
        arr[i] = arr[j];
        arr[j] = buf;
    }



    public static int indexOf(int[] arr, int val) {
        return IntStream.range(0, arr.length).filter(i -> arr[i] == val).findFirst().orElse(-1);
    }

}