package org.example.warmup;

import java.util.Scanner;

/**
 * @author ogbozoyan
 * @since 31.10.2023
 */
public class GroupProjectTask {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int testTimes = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < testTimes; i++) {
            int n, a, b;
            String[] s = scanner.nextLine().split(" ");
            n = Integer.parseInt(s[0]);
            a = Integer.parseInt(s[1]);
            b = Integer.parseInt(s[2]);
            System.out.println(isCorrect(n, a, b));
        }
    }

    public static String isCorrect(int n, int a, int b) {
        if (n % a == 0 || n % b == 0) {
            return "YES";
        } else if (a + b == n) {
            return "YES";
        } else return "NO";
    }
}
