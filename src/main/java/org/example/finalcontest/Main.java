package org.example.finalcontest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.TreeSet;

/**
 * @author ogbozoyan
 * @since 26.11.2023
 */
public class Main {

    public static void main(String[] args) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(System.in));
        var writer = new PrintWriter(System.out);
        int sizeAndIndex = Integer.parseInt(reader.readLine());

        TreeSet<Long> res = new TreeSet<>();

        for (int a = 1; a < sizeAndIndex; a++) {
            long scPow = (long) a * a;
            long trPow = scPow * a;
            res.add(scPow);
            res.add(trPow);
        }
        if (sizeAndIndex == 1) {
            writer.print(1);

        } else if (sizeAndIndex == 2) {
            writer.print(4);

        } else if (sizeAndIndex > 10_000_000) {
            writer.print(1);
        } else {
            int count = 0;
            long result = 0;
            for (long value : res) {
                if (++count == sizeAndIndex) {
                    result = value;
                    break;
                }
            }
            writer.print(result);
        }
        reader.close();
        writer.close();
    }

}
