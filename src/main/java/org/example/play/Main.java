package org.example.play;


public class Main {
    public static void main(String[] args) {
        int a = 1;
        System.out.println(fact(-2));
    }

    public static int fact(int n) {
        if (n == 1) {
            return 1;
        } else {
            return n * fact(n - 1);
        }
    }
}
