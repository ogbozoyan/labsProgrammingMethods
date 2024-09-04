package ru.playground;

import java.util.Objects;

/**
 * @author ogbozoyan
 * @since 03.09.2024
 */
public class Leetcode144 {
    public static void main(String[] args) {

        System.out.println(new Solution().isPalindrome("aa"));


    }

    static class Solution {

        public boolean isPalindrome(String s) {
            String[] split = s.replaceAll("[^\\p{Alnum}]", "").toLowerCase().split("");
            int j = split.length - 1;
            for (int i = 0; i < split.length / 2; i++) {
                if (Objects.equals(split[i], split[j])) {
                    j--;
                } else return false;
            }
            return true;
        }
    }
}
