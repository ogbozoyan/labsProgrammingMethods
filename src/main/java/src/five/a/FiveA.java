package src.five.a;

import java.util.Arrays;

/**
 * @author ogbozoyan
 * @date 06.03.2023
 */
//        – поиск в глубину; DONE
//        – поиск в ширину; Done
//        – алгоритм Дейкстры; Done
//        – алгоритм Крускала;
//        – алгоритм Прима; Done
//        – алгоритм Флойда-Уоршалла;
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
        //DjGraph
        DjGraph djGraph = new DjGraph();
        djGraph.addVertex('0');
        djGraph.addVertex('1');
        djGraph.addVertex('2');
        djGraph.addVertex('3');
        djGraph.addVertex('4');
        djGraph.addEdge(0, 1, 50);
        djGraph.addEdge(2, 4, 60);
        djGraph.addEdge(2, 3, 90);
        djGraph.addEdge(3, 1, 20);
        System.out.println(Arrays.deepToString(djGraph.getAdjMat()));
        System.out.println("Алгоритм Дейкстры");
        djGraph.path();
        System.out.println();

        Integer[][] graphPrim = new Integer[][] {
                {0, 2, 0, 6, 0},
                {2, 0, 3, 8, 5},
                {0, 3, 0, 0, 7},
                {6, 8, 0, 0, 9},
                {0, 5, 7, 9, 0}
        };

        PrimAlg primAlg = new PrimAlg(
                graphPrim,4
                );
        primAlg.primMST();
    }
}