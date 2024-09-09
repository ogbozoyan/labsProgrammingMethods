package org.example.leetcode;

/**
 * @author ogbozoyan
 * @since 06.09.2024
 */
public class Leetcode443 {

    /*
     * aaaaabbccc -> a5b2c3
     * */
    static class Solution {
        public int compress(char[] chars) {

            int write = 0;
            int count = 1;

            for (int i = 0; i < chars.length - 1; i++) {

                if (count > 1) {
                    chars[write] = (char) (count + '0');
                    write++;
                    count = 1;
                } else {
                    chars[write] = chars[i];
                    write++;
                }

                if (chars[i] == chars[i + 1]) {
                    count++;
                }

                if (i == chars.length - 1 || chars[i] != chars[i + 1]) {
                }

            }

            return write;
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().compress(new char[]{'a', 'a', 'b', 'b', 'c', 'c', 'c'}));
    }
}
