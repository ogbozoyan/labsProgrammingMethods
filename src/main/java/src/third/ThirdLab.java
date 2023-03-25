package src.third;

/**
 * @author ogbozoyan
 * @date 06.03.2023
 */
/*
    Задача: реализовать ассоциативный массив (контейнер btree_map) на основе B-дерева.
    Каждый узел дерева должен содержать набор пар ключ- значение.
    Пользователь должен иметь возможность: получить значение по ключу, изменить значение по ключу, | Done
    добавить в контейнер новую пару. | Done
 */
public class ThirdLab {
    static final int MINIMUM_DEGREE = 16;
    public static void main(String[] args){
        BTreeMap<String,String> bTreeMap = new BTreeMap<>();
        BTreeMap<String,String> bTreeMapMinDegre = new BTreeMap<>(MINIMUM_DEGREE);
        bTreeMap.put("1","1");
        System.out.println(bTreeMap.get("1"));
        bTreeMap.put("1","2");
        System.out.println(bTreeMap.get("1"));

        bTreeMap.put("2","2");
        bTreeMap.put("3","3");
        bTreeMap.put("4","4");
        bTreeMap.put("5","5");
        bTreeMap.put("6","6");
        bTreeMap.put("7","7");
        bTreeMap.put("8","8");
        bTreeMap.put("9","9");
        bTreeMap.clear();
        bTreeMapMinDegre.put("2","26");
        bTreeMapMinDegre.put("D","5");
        bTreeMapMinDegre.put("G","4");
        bTreeMapMinDegre.put("E","3");
        bTreeMapMinDegre.put("a","h");
        bTreeMapMinDegre.put("FG","ceasur");
        bTreeMapMinDegre.put("S","rsa");
        bTreeMapMinDegre.put("F","xzc");
        bTreeMapMinDegre.put("v","asdn");
        bTreeMapMinDegre.put("z","ljf");
        bTreeMapMinDegre.put("x","asd");
        bTreeMapMinDegre.put("h","");
        bTreeMapMinDegre.put("g","21");
        bTreeMapMinDegre.put("dsa","2");
        bTreeMapMinDegre.put("c","2ewq");
        bTreeMapMinDegre.put("d","2afd");
        bTreeMapMinDegre.put("f","2fdas");
        bTreeMapMinDegre.put("321","fsdhd2");
        bTreeMapMinDegre.put("12","wqefqw2");
        bTreeMapMinDegre.put("42","2dsa");
        bTreeMapMinDegre.put("231","dsa");
        System.out.println(bTreeMapMinDegre);
    }
}
