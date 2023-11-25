package org.example.contest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ogbozoyan
 * @since 18.11.2023
 */
public class Main {
    private static final long MOD = (long) (1e9 + 7);
    private static final int K = 257;
    private static long[] hourPowers;
    private static long[] minutePowers;
    private static long[] hoursHashes;
    private static long[] minuteHashes;
    static int bit1 = 0;
    static int bit2 = 0;
    static int bit3 = 0;

    public static void main(String[] args) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(System.in));
        var writer = new PrintWriter(System.out);

        String[] inputString = reader.readLine().split(" ");
        int hours = Integer.parseInt(inputString[0]);
        int minutes = Integer.parseInt(inputString[1]);

        String hStr = "0000";
        String mStr = "0000";

        hoursHashes = new long[hours];
        minuteHashes = new long[minutes];

        List<Long> hashes = new ArrayList<>();
        List<String> subStr = new ArrayList<>();
        int count = 0;
        if (hours < minutes) {
            for (int i = 0; i < hours; i++) {
                hStr = String.format("%04d", i);

                for (int j = 0; j < minutes; j++) {
                    mStr = String.format("%04d", j);

                    String time = hStr + ":" + mStr;
                    subStr.add(time);
                }
            }
            for (String st : subStr) {
                if (isPalindrome(st))
                    count++;
            }
        } else {
        }

        System.out.println(subStr);
        System.out.println(count);
    }

    private static boolean isPalindrome(String time) {
        int index = time.indexOf(":");
        int i = index - 1, j = index + 1;

        char[] charArray = time.toCharArray();
        while (i >= 0 && j < time.length()-1) {
            char cI = charArray[i];
            char cJ = charArray[j];
            if (cI != cJ) {
                return false;
            }
            i--;
            j++;
        }
        return true;
    }

    //    static boolean isPalindrome(String strL,String strR){
//        strR
//    }
    static long getHash(String str) {
        int hash_so_far = 0;
        final char[] s = str.toCharArray();
        long p_pow = 1;
        final int n = s.length;
        for (int i = 0; i < n; i++) {
            hash_so_far = (int) ((hash_so_far
                    + (s[i] - 'a' + 1) * p_pow)
                    % MOD);
            p_pow = (p_pow * K) % MOD;
        }
        return hash_so_far;
    }

    // Function to return the count of common elements
    static int count_common(int[] a, int n, int[] b, int m) {

        // Traverse the first array
        for (int i = 0; i < n; i++) {
            // Set 1 at (index)position a[i]
            bit1 = bit1 | (1 << a[i]);
        }
        // Traverse the second array
        for (int i = 0; i < m; i++) {

            // Set 1 at (index)position b[i]
            bit2 = bit2 | (1 << b[i]);
        }

        // Bitwise AND of both the bitsets
        bit3 = bit1 & bit2;

        // Find the count of 1's
        int count = Integer.toBinaryString(bit3).split("1").length - 1;
        return count;
    }


}
