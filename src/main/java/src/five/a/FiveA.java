package src.five.a;

/**
 * @author ogbozoyan
 * @date 06.03.2023
 */
//        – поиск в глубину; DONE
//        – поиск в ширину; Done
//        – алгоритм Дейкстры; Done
//        – алгоритм Крускала; Done
//        – алгоритм Прима; Done
//        – алгоритм Флойда-Уоршалла; Done
public class FiveA {
    public static void main(String[] args) {
        Graph graph = new Graph(20);
        graph.addVertex('A');
        graph.addVertex('B');
        graph.addVertex('C');
        graph.addVertex('D');
        graph.addVertex('E');
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(0, 3);
        graph.addEdge(3, 4);
        System.out.println("Поиск в ширину");
        graph.BFS();
        System.out.println(" ");
        System.out.println("Поиск в глубину");
        graph.DFS();
        System.out.println(" ");
        //DjGraph не должен содержать нулевой путь кроме главной diag
        Integer inf = 99999;
        Integer[][] graphDj = new Integer[][] {
                {0, 2, 1, 6, 1},
                {2, 0, 3, 8, 5},
                {1, 3, 0, 1, 7},
                {2, 8, 1, 0, 9},
                {1, 5, 7, 4, 0}
        };
        DjGraph djGraph = new DjGraph(
                graphDj
        );
        djGraph.djAlg(djGraph.getGraph(),1-1);
        System.out.println("Крускало: ");
        djGraph.kruskAlg();
        System.out.println("Прима: ");
        djGraph.primAlg();
        System.out.println("Floyd");
        Integer[][] graphFloyd =  {
                {0, inf, 1, 6, 1},
                {2, 0, inf, 8, 5},
                {1, 3, 0, 1, inf},
                {inf,inf, inf, inf, inf},
                {1, 5, 7, inf, 0}
        };
        DjGraph flGraph = new DjGraph(
                graphFloyd
        );
        flGraph.floydWarshall();

    }
}