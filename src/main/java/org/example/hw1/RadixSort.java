package org.example.hw1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author ogbozoyan
 * @since 04.11.2023
 */
public class RadixSort {
    public static void main(String[] args) throws IOException {

        BufferedReader scanner = new BufferedReader(new FileReader("src/main/java/org/example/hw1/input.txt"));
//        BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/org/example/hw1/output.txt"));
//        BufferedReader scanner = new BufferedReader(new FileReader("input.txt"));
//        BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));

        int n = toInt(scanner.readLine());
        String[] array = new String[n];
        for (int i = 0; i < n; i++) {
            array[i] = scanner.readLine();
        }

        radixSort(array);

    }


    private static void radixSort(String[] array) {
//        long startTime = System.currentTimeMillis();

        LinkedHashMap<Integer, List<String>> bucket = new LinkedHashMap<>();
        var writer = new PrintWriter(System.out);

        boolean isSorted = false;
        int phase = 1;

        writer.println("Initial array:");
        writer.println(String.join(", ", array));

        while (true) {

            for (int t = 0; t <= 9; t++) {
                bucket.put(t, new LinkedList<>());
            }


            for (String st : array) {
                String[] split = st.split("");

                int significant = split.length - phase;
                if (significant >= 0) {
                    int mapIndex = toInt(split[significant]);
                    bucket.get(mapIndex).add(st);
                } else {
                    isSorted = true;
                }
            }
            if (!isSorted) {
                writer.println("**********");
                writer.println("Phase " + phase);
            }

            int j = 0;
            if (!isSorted) {
                for (Map.Entry<Integer, List<String>> entry : bucket.entrySet()) {
                    List<String> entryValue = entry.getValue();
                    StringBuilder bucketStr = new StringBuilder("Bucket " + entry.getKey() + ": ");
                    if (!entryValue.isEmpty()) {
                        for (String s : entryValue) {
                            array[j] = s;
                            j++;
                        }
                        bucketStr.append(String.join(", ", entryValue));
                    } else {
                        bucketStr.append("empty");
                    }
                    writer.println(bucketStr);
                }
            }
            if (isSorted) {
                break;
            } else {
                phase++;
            }
        }
        writer.println("**********");
        writer.println("Sorted array:");
        writer.println(String.join(", ", array));


//        long endTime = System.currentTimeMillis();
//        writer.println("Total execution time: " + (endTime - startTime) + "ms");
        writer.close();
    }


    private static int toInt(String str) {
        return Integer.parseInt(str);
    }
}
