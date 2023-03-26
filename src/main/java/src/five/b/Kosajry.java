package src.five.b;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * @author ogbozoyan
 * @date 26.03.2023
 * Мини глосарий
 *     Алгоритм Косарайю (Kosaraju's algorithm) — алгоритм нахождения компонент сильной связности в ориентированном графе.
 *     Алгоритм состоит из двух этапов:
 *     Обход графа в глубину (DFS) и сохранение порядка выхода из вершин (т.е. порядка добавления вершин в стек)
 *      в порядке уменьшения временных меток.
 *     Обход транспонированного графа в порядке, определенном на первом этапе, и нахождение компонент сильной связности.
 *     Транспонированный граф получается из исходного графа путем инвертирования направления всех его ребер.
 *     Алгоритм Косарайю работает за время O(|V| + |E|), где |V| и |E| - число вершин и ребер в графе соответственно.
 */
@Data
@NoArgsConstructor
public class Kosajry {
    private Graph graph;
    private boolean[] visited;
    private Stack<Integer> stack;
    private List<List<Integer>> components;

    public Kosajry(Graph graph) {
        this.graph = graph;
        this.visited = new boolean[graph.getVertexNumber()];
        this.stack = new Stack<>();
        this.components = new ArrayList<>();
    }

    public List<List<Integer>> getStronglyConnectedComponents() {
        // первый обход в глубину и заполнение стека
        for (int i = 0; i < graph.getVertexNumber(); i++) {
            if (!visited[i]) {
                dfs1(i);
            }
        }

        // обход в глубину в порядке обратном порядку на стеке
        Arrays.fill(visited, false);
        while (!stack.isEmpty()) {
            int v = stack.pop();
            if (!visited[v]) {
                List<Integer> component = new ArrayList<>();
                dfs2(v, component);
                components.add(component);
            }
        }

        return components;
    }

    private void dfs1(int v) {
        visited[v] = true;
        for (Graph.Edge e : graph.getAdjList(v)) {
            if (!visited[e.getWVertex()]) {
                dfs1(e.getWVertex());
            }
        }
        stack.push(v);
    }

    private void dfs2(int v, List<Integer> component) {
        visited[v] = true;
        component.add(v);
        for (Graph.Edge e : graph.getAdjList(v)) {
            if (!visited[e.getWVertex()]) {
                dfs2(e.getWVertex(), component);
            }
        }
    }

}
