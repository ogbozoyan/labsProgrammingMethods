package src.five.b;

import java.util.List;

/**
 * @author ogbozoyan
 * @date 06.03.2023
 */
/*
    – алгоритм Тарьяна для топологической сортировки; | Done
    – алгоритм Флёри; Done
    – алгоритм поиска эйлерова цикла на основе объединения циклов(Хиерхолцера); | Done
    – алгоритм Косарайю; | Done
 */
public class FiveB {
    public static void main(String[] args) {
        Graph graph = new Graph(5);
        graph.addEdge(0,1);
        graph.addEdge(0,2);
        graph.addEdge(0,3);
        graph.addEdge(0,4);
        graph.addEdge(1,2);
        graph.addEdge(1,3);
        graph.addEdge(1,4);
    //==========================Нахождения смльноного компонента связности=====================================//
        Kosajry kosajry = new Kosajry(graph);
        System.out.println("Косайрю "+kosajry.getStronglyConnectedComponents());
    //==========================Топологическая сортировка=====================================//
        TarjanSort sort = new TarjanSort(graph);
        System.out.println("Тарьян "+sort.getOrder());
    //================================Поиск цикла эйлерового===============================//
        NonOrientedGraph nonOrientedGraph = new NonOrientedGraph(5);
        nonOrientedGraph.addEdge(0,1);
        nonOrientedGraph.addEdge(0,2);
        nonOrientedGraph.addEdge(0,3);
        nonOrientedGraph.addEdge(0,4);
        nonOrientedGraph.addEdge(1,2);
        nonOrientedGraph.addEdge(1,3);
        nonOrientedGraph.addEdge(1,4);
        List<Integer> flyery = nonOrientedGraph.findEulerianCycle();
        System.out.println("Флёри "+flyery);
        List<Integer> eulerianCycleHierlocer = nonOrientedGraph.findEulerianCycleHierlocer();
        System.out.println("Хиерлоцера "+eulerianCycleHierlocer);
    //================================Поиск цикла эйлерового===============================//
    }
}
