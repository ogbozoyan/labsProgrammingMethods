package src.six;

/**
 * @author ogbozoyan
 * @date 06.03.2023
 */
public class SubString {
    static public Integer searchKnutMorrisPratta(String source, String subString) {
        Integer n = source.length();
        Integer m = subString.length();
        Integer[] table = new Integer[m];
        table[0] = 0;
        Integer shift = 0;
        for (int q = 1; q < m; q++) {
            while (shift > 0 && subString.charAt(shift) != subString.charAt(q)) {
                shift = table[shift - 1];
                if (subString.charAt(shift) == subString.charAt(q)) shift++;
                table[q] = shift;
            }
        }
        shift = 0;
        for (int i = 0; i < n; i++) {
            while (shift > 0 && subString.charAt(shift) != source.charAt(i)) {
                shift = table[shift - 1];
            }
            if (subString.charAt(shift) == source.charAt(i)) shift++;
            if (shift.equals(m)) return i - m + 1; // подстрока найдена
        }
        return -1;
    }

    private static Integer hash(char c) {
        return c & 255;
    }

    public static Integer searchBoyerMoore(String source, String subString) {
        Integer shLen = 256;
        Integer n = source.length();
        Integer m = subString.length();
        Integer[] shifts = new Integer[shLen];

        for (int i = 0; i < shLen; i++) {
            shifts[i] = m;
        }
        for (int i = 0; i < m - 1; i++) {
            shifts[hash(subString.charAt(i))] = m - i - 1;
        }
        for (int i = 0; i <= n - m; ) {
            for (int j = m - 1; j >= 0; j--) {
                if (source.charAt(i + j) == subString.charAt(j)) {
                    if (j == 0) return i;
                } else {
                    break;
                }
            }
            i += shifts[hash(source.charAt(i + m - 1))];
        }
        return -1;
    }


    private static final int NoOfChars = 256;

    private static int getNextState(char[] pat, int M,
                                    int state, int x) {

        if (state < M && x == pat[state])
            return state + 1;

        int ns, i;

        for (ns = state; ns > 0; ns--) {
            if (pat[ns - 1] == x) {
                for (i = 0; i < ns - 1; i++)
                    if (pat[i] != pat[state - ns + 1 + i])
                        break;
                if (i == ns - 1)
                    return ns;
            }
        }
        return 0;
    }

    private static void computeTF(char[] pat, int M, int TF[][]) {
        int state, x;
        for (state = 0; state <= M; ++state) {
            for (x = 0; x < NoOfChars; ++x) {
                TF[state][x] = getNextState(pat, M, state, x);
            }
        }
    }

    public static void searchAutomate(char[] txt, char[] pat) {
        int N = txt.length;
        int M = pat.length;

        int[][] TF = new int[M + 1][NoOfChars];

        computeTF(pat, M, TF);

        int i, state = 0;
        for (i = 0; i < N; i++) {
            state = TF[state][txt[i]];
            if (state == M)
                System.out.println("Совпадение по индексу " + (i - M + 1));
        }
    }
}
