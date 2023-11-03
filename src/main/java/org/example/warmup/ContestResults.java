package org.example.warmup;

import java.util.Scanner;

/**
 * @author ogbozoyan
 * @since 01.11.2023
 */
public class ContestResults {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int a, b, n;
        a = Integer.parseInt(scanner.nextLine().split(" ")[0]);
        b = Math.abs(Integer.parseInt(scanner.nextLine().split(" ")[0]));
        n = Integer.parseInt(scanner.nextLine().split(" ")[0]);
        double avgB = (double) (b) / n;
        if (avgB % 1 != 0) {
            avgB += 1;
        }
        boolean isMore = a > avgB;
        System.out.println(isMore ? "Yes" : "No");
    }
}
