package org.example.warmup;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author ogbozoyan
 * @since 01.11.2023
 */
public class StarostaPidoras {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine().split("")[0]);
        int[] students = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            int studentI = students[i];
            int sum = 0;

            for (int j = students.length - 1; j >= 0; j--) {
                int studentJ = students[j];
                int buf = sum;
                buf += Math.abs(studentJ - studentI);
                sum = buf;
            }
            sb.append(sum).append(" ");
        }
        System.out.println(sb);
    }
}
