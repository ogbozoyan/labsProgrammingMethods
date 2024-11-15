package org.example.leetcode;

import java.util.regex.Pattern;

/**
 * @author ogbozoyan
 * @since 04.09.2024
 */
public class Leetcode468 {

    static class Solution {

        Pattern IPV_4_REGEX = Pattern.compile("(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])");
        Pattern IPV_6_REGEX = Pattern.compile("((([0-9a-fA-F]){1,4}):){7}([0-9a-fA-F]){1,4}");

        public String validIPAddress(String queryIP) {
            if (IPV_4_REGEX.matcher(queryIP).matches()) {
                return "IPv4";
            } else if (IPV_6_REGEX.matcher(queryIP).matches()) {
                return "IPv6";
            } else return "Neither";
        }

    }

    public static void main(String[] args) {
        System.out.println(new Solution().validIPAddress("2F33:12a0:3Ea0:0302"));
    }
}
