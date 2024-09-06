package org.example.leetcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

/**
 * Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.
 * <p>
 * Example 1:
 * <p>
 * Input: n = 3
 * Output: ["((()))","(()())","(())()","()(())","()()()"]
 * Example 2:
 * <p>
 * Input: n = 1
 * Output: ["()"]
 * <a href="https://arc.net/e/91F70AEC-58D4-493E-921E-3B2085ABC644">...</a>
 */
public class Leetcode22 {
    public static void main(String[] args) {

        class Solution {


            public List<String> generateParenthesis(int n) {
                HashSet<String> result = new HashSet<>();
                Stack<String> stack = new Stack<>();

                backTract(stack, result, n, 0, 0);
                return new ArrayList<>(result);
            }

            public void backTract(Stack<String> stack, HashSet<String> result, int n, int openN, int closeN) {

                if (openN == closeN && openN == n) {
                    result.add(String.join("", stack));
                    return;
                }

                if (openN < n) {
                    stack.push("(");
                    backTract(stack, result, n, openN + 1, closeN);
                    stack.pop();
                }

                if (closeN < openN) {
                    stack.push(")");
                    backTract(stack, result, n, openN, closeN + 1);
                    stack.pop();
                }
            }


        }

        System.out.println(new Solution().generateParenthesis(20).size());
    }
}
