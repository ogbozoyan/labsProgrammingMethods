package org.example.hw2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * @author ogbozoyan
 * @since 05.11.2023
 */
public class Main {
    private static final long MOD = (long) (1e9 + 7);
    private static final int K = 257;
    private static long[] powers;

    public static void main(String[] args) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(System.in));
        var writer = new PrintWriter(System.out);

        String inputString = reader.readLine();
        long[] hashes = new long[inputString.length()];
        powers = new long[inputString.length()];

        hashes[0] = inputString.charAt(0) - 'a' + 1;
        powers[0] = 1;

        for (int i = 1; i < inputString.length(); i++) {
            hashes[i] = (hashes[i - 1] * K + inputString.charAt(i) - 'a' + 1) % MOD;
            powers[i] = (powers[i - 1] * K) % MOD;
        }

        writer.println(findStringBase(hashes));

        writer.close();
        reader.close();
    }


    public static int findStringBase(long[] hashes) {
        int n = hashes.length;

        for (int k = 1; k < n; ++k) {
            long prefix = getHash(0, n - k, hashes, powers);
            long suffix = getHash(k, n - k, hashes, powers);
            if (prefix == suffix) {
                return k;
            }
        }
        return n;
    }

    public static int[] prefixFunction(long[] hashes, String inputString) {
        int n = inputString.length();
        int[] pi = new int[n];

        for (int i = 1; i < n; ++i) {
            int j = pi[i - 1];
            while (j > 0 && getHash(hashes, i, i) != getHash(hashes, j, j))
                j = pi[j - 1];
            if (getHash(hashes, i, i) == getHash(hashes, j, j)) ++j;
            pi[i] = j;
        }

        return pi;
    }

    public static int[] zetFunction(long[] hashes, String inputString) {
        int n = inputString.length();
        int[] z = new int[n];
        for (int i = 0; i < n; i++) {
            int left = 1, right = n - i;
            while (left <= right) {
                int middle = (left + right) / 2;
                if (getHash(hashes, 0, middle - 1) == getHash(hashes, i, i + middle - 1)) {
                    z[i] = middle;
                    left = middle + 1;
                } else {
                    right = middle - 1;
                }
            }
        }
        z[0] = 0;
        return z;
    }

    public static long getHash(long[] h, int l, int to) {
        return getHash(l, to - l + 1, h, powers);
    }

    /**
     * return polynomial substring hash
     */
    public static long getHash(int from, int subStrLen, long[] hashes, long[] powers) {
        int to = from + subStrLen - 1;
        long res = hashes[to];
        if (from > 0) {
            res = (res - (hashes[from - 1] * powers[subStrLen])) % MOD;
            if (res < 0) {
                res = (res + MOD) % MOD; // Обработка отрицательных значений
            }
        }
        return res;
    }

    public static boolean isEqual(int length, int from1, int from2, long[] hashes, long[] powers) {
        return getHash(from1, length, hashes, powers) == getHash(from2, length, hashes, powers);
    }

    private static int toInt(String str) {
        return Integer.parseInt(str);
    }


}
