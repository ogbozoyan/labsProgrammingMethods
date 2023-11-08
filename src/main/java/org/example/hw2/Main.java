package org.example.hw2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * @author ogbozoyan
 * @since 05.11.2023
 */
public class Main {
    private static final long MOD = (long) (1e9 + 7);
    private static final int K = 257;

    public static void main(String[] args) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(System.in));
        var writer = new PrintWriter(System.out);

        String inputString = reader.readLine();
        int Q = toInt(reader.readLine());

        long[] hashes = new long[inputString.length()];
        long[] powers = new long[inputString.length()];
        hashes[0] = inputString.charAt(0) - 'a' + 1;
        powers[0] = 1;
        for (int i = 1; i < inputString.length(); i++) {
            hashes[i] = (hashes[i - 1] * K + inputString.charAt(i) - 'a' + 1) % MOD;
            powers[i] = (powers[i - 1] * K) % MOD;
        }
        while (Q > 0) {
            int[] inputs = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            if (isEqual(inputs[0], inputs[1], inputs[2], hashes, powers)) {
                writer.println("yes");
            } else {
                writer.println("no");
            }
            Q--;
        }

    }

    public static boolean isEqual(int length, int from1, int from2, long[] hashes, long[] powers) {
        return getHash(from1, length, hashes, powers) == getHash(from2, length, hashes, powers);
    }

    /**
     * return polynomial substring hash
     * */
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

    private static int toInt(String str) {
        return Integer.parseInt(str);
    }


}
