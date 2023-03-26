package src.five.b;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.util.Arrays.fill;

@Data
/**
 *  @author ogbozoyan
 *   @date 26.03.2023
 *   Мини глосарий
 *     Алгоритм Тарьяна (Tarjan's algorithm) - это алгоритм нахождения топологической сортировки в
 *     ориентированном графе с использованием поиска в глубину (DFS). Он позволяет обрабатывать ориентированные графы с циклами.
 * Алгоритм Тарьяна состоит из следующих шагов:
 * Инициализировать массивы visited, onStack и order, где visited хранит информацию о том, посещалась ли вершина,
 * onStack указывает, находится ли вершина на стеке, используемом для обнаружения циклов, а order содержит порядок,в котором вершины будут добавляться в результирующий список.
 * Для каждой непосещенной вершины v, вызвать метод dfs(v).
 * В методе dfs(v):
 * Пометить v как посещенную вершину и добавить ее на стек onStack.
 * Для каждой смежной с v вершины w:
 * Если w еще не посещена, вызвать рекурсивно метод dfs(w).
 * Если w находится на стеке onStack, то в графе есть цикл. Обработать его соответствующим образом (например, вывести сообщение об ошибке).
 * Убрать v из стека onStack и добавить ее в начало списка order.
 * После завершения всех вызовов dfs(v), вернуть список order. Этот список будет содержать вершины в топологически отсортированном порядке.
 */
public class TarjanSort {
    private Graph graph;
    private List<Integer> order; // результирующий список
    private final boolean[] visited; // массив для хранения информации о посещенных вершинах
    private final boolean[] onStack; // массив для хранения информации о вершинах, находящихся на стеке

    public TarjanSort(Graph graph) {
        this.graph = graph;
        order = new ArrayList<>();
        visited = new boolean[graph.getVertexNumber()];
        onStack = new boolean[graph.getVertexNumber()];
        fill(visited, FALSE);
        fill(onStack, FALSE);
    }

    public List<Integer> getOrder() {
        List<Integer> order = new ArrayList<>(); // initialize the order list
        for (int v = 0; v < graph.getVertexNumber(); v++) {
            if (!visited[v]) {
                DFS(v, order);
            }
        }
        Collections.reverse(order);
        return order;
    }

    private void DFS(int v, List<Integer> order) {
        visited[v] = true;
        onStack[v] = true;

        for (Graph.Edge edge : graph.getAdjList(v)) {
            if (!visited[edge.getWVertex()]) {
                DFS(edge.getWVertex(), order);
            } else if (onStack[edge.getWVertex()]) {
                throw new RuntimeException("Граф имеет циклы");
            }
        }
        order.add(v);
        onStack[v] = false;
    }
}
