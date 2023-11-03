package org.example.warmup;

import java.util.Scanner;

/**
 * @author ogbozoyan
 * @since 02.11.2023
 */
public class Elevator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int k = Integer.parseInt(scanner.nextLine().split(" ")[0]);
        int n = Integer.parseInt(scanner.nextLine().split(" ")[0]);
        int[] aArr = new int[n];

        //fill number of humans on floor
        for (int i = 0; i < n; i++) {
            aArr[i] = Integer.parseInt(scanner.nextLine().split(" ")[0]);
        }
        int seconds = 0;


        for (int i = 0; i < aArr.length; i++) {
//            seconds += calcSec(i + 1, k, aArr[i]);
        }

        System.out.println(seconds);
    }

//    public static int calcSec(int floor, int capacity, int countPpl) {
//        int seconds = 0;
//        while (true) {
//            countPpl -= capacity;
//            seconds += floor;
//            if (countPpl <= 0) {
//                return seconds;
//            } else {
//                seconds += floor;
//            }
//        }
//        return seconds;
//    }
}
