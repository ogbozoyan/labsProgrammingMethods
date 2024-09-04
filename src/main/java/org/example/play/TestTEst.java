package org.example.play;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ogbozoyan
 * @since 02.09.2024
 */
public class TestTEst {

    public static void main(String[] args) {
//        Test obj = new Test();
//        obj.x = 1;
//        test(obj);
//        System.out.println(obj);
        /*
          Для синглтона
          Для многопоточки, чтобы потокобезопасно изменять можно было
        */
//        int arr[] = {9, 4, 9, -1, 6, 7, 4, 5 };
//        int n = method(arr);
//        System.out.println(n);

    }

    private static int method(int[] arr) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();

        // Подсчет частоты появления каждого элемента
        for (int num : arr) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1); // вот тут он берет количество раз встретивщихся чисел и +1
        }

        // Поиск первого не повторяющегося элемента
        for (int num : arr) {
            if (frequencyMap.get(num) == 1) {
                return num;
            }
        }

        // Если нет не повторяющихся элементов, возвращаем -1 СПРОСИ
        return -1;
    }

    static final class Test {
        public int x;

        @Override
        public String toString() {
            return "Test{" +
                    "x=" + x +
                    '}';
        }
    }

    private static void test(Test param) {
        param.x = 2;
        param = new Test();
        param.x = 3;
    }

}





