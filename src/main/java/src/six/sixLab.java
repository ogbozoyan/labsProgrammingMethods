package src.six;

import static src.six.SubString.*;

/**
 * @author ogbozoyan
 * @date 06.03.2023
 */

public class sixLab {
//     Бойера-Мура; Done
//     Кнута-Морриса-Пратта; DONE
//     с помощью конечного автомата. Done


    public static void main(String[] args) {
        System.out.println(searchBoyerMoore("Hello world!", "rl"));
        System.out.println(searchBoyerMoore("abababa","ab"));
        System.out.println(searchKnutMorrisPratta("Hello world!", "rl"));
        searchAutomate("Hello world!".toCharArray(), "rl".toCharArray());

    }
}
