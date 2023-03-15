package src.five.a;

import lombok.Data;
import lombok.NoArgsConstructor;

import static java.lang.Math.min;
import static java.util.Arrays.fill;

/**
 * @author ogbozoyan
 * @date 15.03.2023
 */
@Data
@NoArgsConstructor
public class PrimAlg {
    private Integer[][] adjMat;
    private Integer vertexNum;
    private Integer INFINITY = Integer.MAX_VALUE - 1000;

    public PrimAlg(Integer[][] graph, Integer vertexNum) {
        this.adjMat = graph;
        this.vertexNum = vertexNum;
    }

    public Integer mstPrimWeight() {
        boolean[] used = new boolean[vertexNum]; // массив пометок
        int[] dist = new int[vertexNum]; // массив расстояния. dist[v] = вес_ребра(MST, v)
        fill(dist, INFINITY); // устанаавливаем расстояние до всех вершин INF
        dist[0] = 0; // для начальной вершины положим 0
        for (; ; ) {
            int v = -1;
            for (int nv = 0; nv < vertexNum; nv++) // перебираем вершины
                if (!used[nv] && dist[nv] < INFINITY && (v == -1 || dist[v] > dist[nv])) // выбираем самую близкую непомеченную вершину
                    v = nv;
            if (v == -1) break; // ближайшая вершина не найдена
            used[v] = true; // помечаем ее
            for (int nv = 0; nv < vertexNum; nv++)
                if (!used[nv] && adjMat[v][nv] < INFINITY) // для всех непомеченных смежных
                    dist[nv] = min(dist[nv], adjMat[v][nv]); // улучшаем оценку расстояния (релаксация)
        }
        int ret = 0; // вес MST
        for (int v = 0; v < vertexNum; v++) {
            if (dist[v] == INFINITY) continue;
            ret += dist[v];
        }
        return ret;
    }

    private int minKey(Integer[] key, Boolean[] mstSet) {
        int min = Integer.MAX_VALUE, min_index = -1;
        for (int v = 0; v < vertexNum; v++)
            if (!mstSet[v] && key[v] < min) {
                min = key[v];
                min_index = v;
            }
        return min_index;
    }

    private void printMST(Integer[] parent, Integer[][] graph) {
        System.out.println("Путь \tВес");
        for (int i = 1; i < vertexNum; i++) {
            System.out.println(parent[i] + " - " + i + "\t" + graph[i][parent[i]]);
        }
    }

    public void primMST() {
        Integer[][] graph = adjMat;
        Integer[] parent = new Integer[vertexNum];
        Integer[] key = new Integer[vertexNum];
        Boolean[] mstSet = new Boolean[vertexNum];
        for (int i = 0; i < vertexNum; i++) {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }
        key[0] = 0;
        parent[0] = -1;
        for (int count = 0; count < vertexNum - 1; count++) {
            Integer u = minKey(key, mstSet);
            mstSet[u] = true;
            for (int v = 0; v < vertexNum; v++)
                if (graph[u][v] != 0 && !mstSet[v] && graph[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
        }
        printMST(parent, graph);
    }
}
