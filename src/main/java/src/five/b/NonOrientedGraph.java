package src.five.b;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * @author ogbozoyan
 * @date 26.03.2023
 * Алгоритм Флери (Fleury's Algorithm) используется для поиска эйлерова цикла в неориентированном графе. Он состоит из следующих шагов:
 * <p>
 * Выбрать произвольную вершину в графе и начать обход из нее.
 * Для каждой вершины в текущем цикле, выбрать ребро, которое не является мостом (мост - это ребро, при удалении которого граф становится несвязным). Если таких ребер нет, выбрать любое ребро.
 * Продолжить обход до тех пор, пока не будут посещены все ребра графа.
 * =============================================================================================================================================================================================================================
 * Алгоритм поиска эйлерова цикла на основе объединения циклов (также известен как алгоритм Хиерхолцера) - это эффективный алгоритм,
 * который используется для поиска эйлерова цикла в неориентированном графе. Он работает на основе принципа объединения циклов,
 * где изначально каждая вершина представляет собой отдельный цикл.
 * Алгоритм состоит из следующих шагов:
 * Выбираем любую вершину в графе и помещаем ее в цикл.
 * Для каждой вершины, смежной с выбранной вершиной, строим цикл, добавляя ее к уже существующему циклу и пройдя через
 * все смежные вершины до тех пор, пока не вернемся в исходную вершину.
 * Объединяем циклы, добавляя новые вершины только в те циклы, которые не были объединены.
 * Если все циклы объединены, то построенный цикл является эйлеровым.
 * Этот алгоритм работает за линейное время O(E), где E - количество ребер в графе. Он также может быть легко расширен для поиска эйлерова пути в графе.
 * =============================================================================================================================================================================================================================
 */
@Data
@NoArgsConstructor
public class NonOrientedGraph {
    private int V; // количество вершин
    private List<Integer>[] adjList; // списки смежности


    public NonOrientedGraph(int V) {
        this.V = V;
        adjList = new ArrayList[V];
        for (int i = 0; i < V; i++) {
            adjList[i] = new ArrayList<>();
        }
    }

    public void addEdge(int v, int w) {
        adjList[v].add(w);
        adjList[w].add(v);
    }

    private List<Integer> getAdjList(int v) {
        return adjList[v];
    }

    public void remEdge(Integer v, Integer w) {
        adjList[v].remove(w);
        adjList[w].remove(v);
    }

    public List<Integer> findEulerianCycle() {
        int numEdges = 0;
        int startVertex = 0;

        for (int i = 0; i < this.getV(); i++) {
            if (this.getAdjList(i).size() % 2 != 0) {
                return null;
            }
            numEdges += this.getAdjList(i).size();
        }
        numEdges /= 2;

        for (int i = 0; i < this.getV(); i++) {
            if (this.getAdjList(i).size() > 0) {
                startVertex = i;
                break;
            }
        }

        List<Integer> cycle = new ArrayList<>();
        dfs(startVertex, cycle);

        if (cycle.size() != numEdges + 1) {
            return null;
        }

        Collections.reverse(cycle);
        return cycle;
    }

    private void dfs(int v, List<Integer> cycle) {
        while (!this.getAdjList(v).isEmpty()) {
            int u = this.getAdjList(v).get(0);
            this.remEdge(v, u);
            dfs(u, cycle);
        }
        cycle.add(v);
    }
    public List<Integer> findEulerianCycleHierlocer() {
        int numEdges = 0;
        int startVertex = 0;

        for (int i = 0; i < this.getV(); i++) {
            if (this.getAdjList(i).size() % 2 != 0) {
                return null;
            }
            numEdges += this.getAdjList(i).size();
        }
        numEdges /= 2;

        for (int i = 0; i < this.getV(); i++) {
            if (this.getAdjList(i).size() > 0) {
                startVertex = i;
                break;
            }
        }

        List<Integer> cycle = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();
        stack.push(startVertex);

        while (!stack.isEmpty()) {
            int v = stack.peek();
            if (this.getAdjList(v).size() > 0) {
                int u = this.getAdjList(v).get(0);
                stack.push(u);
                this.remEdge(v, u);
            } else {
                cycle.add(stack.pop());
            }
        }

        if (cycle.size() != numEdges + 1) {
            return null;
        }

        Collections.reverse(cycle);
        return cycle;
    }
}

