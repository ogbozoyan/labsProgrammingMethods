package org.example.warmup;

import java.util.Scanner;
import java.util.Stack;

/**
 * @author ogbozoyan
 * @since 31.10.2023
 */
public class StackTask {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        char[] charArray = line.toCharArray();

        boolean b = checkCorrect(charArray);
        System.out.println(b ? "yes" : "no");
    }

    private static boolean checkCorrect(char[] charArray) {
        String openBraces = "([{";
        String closeBraces = ")]}";

        Stack<Character> stack = new Stack<>();
        for (Character c : charArray) {
            {
                if (openBraces.contains(String.valueOf(c))) {
                    stack.push(c);
                } else if (stack.isEmpty() || openBraces.indexOf(stack.pop()) != closeBraces.indexOf(c)) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }
}
