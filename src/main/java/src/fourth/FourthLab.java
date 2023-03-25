package src.fourth;

/**
 * @author ogbozoyan
 * @date 15.03.2023
 */
/*
Задача: реализовать ассоциативный массив (контейнер hash_map) на основе хеш-таблицы
с разрешением коллизий по методу цепочек.
GET  | Done
PUT   | Done
DELETE   | Done
 */
public class FourthLab {
    public static void main(String[] args) {
        MyHashMap<Integer,String> hashMap = new MyHashMap<>();
        hashMap.put(1,"1");
        hashMap.get(1);
        hashMap.remove(1);
        hashMap.getNodeCount(0);
    }
}
