package org.example.finalcontest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ogbozoyan
 * @since 26.11.2023
 */
public class Main {

    public static void main(String[] args) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(System.in));
        var writer = new PrintWriter(System.out);
        String[] s = reader.readLine().split(" ");
        String[] s2 = reader.readLine().split(" ");

        int n = Integer.parseInt(s[0]);
        int m = Integer.parseInt(s[1]);

        int[] bricks = new int[m * 2];

        int j = 0;
        for (int i = 0; i <= m; i += 2) {
            bricks[i] = Integer.parseInt(s2[j]);
            bricks[i + 1] = Integer.parseInt(s2[j]);
            j++;
        }

        int split = Arrays.stream(bricks).max().getAsInt() / 2;

        List<List<Integer>> combinations = findCombinations(bricks, split, Integer.parseInt(s[0]));

        if (combinations.isEmpty()) {
            writer.write("-1");
        }
        reader.close();
        writer.close();
    }

    public static List<List<Integer>> findCombinations(int[] array, int splitBrick, int borderSum) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(array);
        findCombinationsHelper(array, splitBrick, borderSum, 0, new ArrayList<>(), result);
        return result;
    }

    private static void findCombinationsHelper(int[] array, int splitBrick, int remainingSum,
                                               int start, List<Integer> currentCombination, List<List<Integer>> result) {
        if (remainingSum == 0 && currentCombination.contains(splitBrick)) {
            result.add(new ArrayList<>(currentCombination));
            return;
        }

        for (int i = start; i < array.length; i++) {
            if (i > start && array[i] == array[i - 1]) {
                // Skip duplicates to avoid duplicate combinations
                continue;
            }

            if (array[i] <= remainingSum) {
                currentCombination.add(array[i]);
                findCombinationsHelper(array, splitBrick, remainingSum - array[i], i + 1, currentCombination, result);
                currentCombination.remove(currentCombination.size() - 1);
            }
        }
    }

}
