package src.five.a;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * @author ogbozoyan
 * @date 15.03.2023
 */
@Data
@NoArgsConstructor
public class DjGraph {
    private Integer[][] graph;
    private final Integer INF = 99999;
    private Integer vertCount;
    private List<Edge> graphEdges;

    public DjGraph(Integer[][] graph) {
        this.graph = graph;
        this.vertCount = graph.length;
        graphEdges = new ArrayList<>();
        for (int i = 0; i < vertCount; i++) {
            for (int j = 0; j < vertCount; j++) {
                graphEdges.add(new Edge(i, j, graph[i][j]));
            }
        }
        graphEdges.sort(Comparator.comparingInt(Edge::getWeight));
    }

    public void djAlg(Integer[][] graph, int src) {
        Integer[] distance = new Integer[vertCount];
        Boolean[] isVisited = new Boolean[vertCount];

        for (int i = 0; i < vertCount; i++) {
            distance[i] = INF;
            isVisited[i] = false;
        }

        distance[src] = 0;

        for (int count = 0; count < vertCount - 1; count++) {
            int u = minDistance(distance, isVisited);

            isVisited[u] = true;

            for (int v = 0; v < vertCount; v++)
                if (!isVisited[v] && graph[u][v] != 0
                        && !Objects.equals(distance[u], INF)
                        && distance[u] + graph[u][v] < distance[v])
                    distance[v] = distance[u] + graph[u][v];
        }

        outputRes(distance);
    }

    private void outputRes(Integer[] dist) {
        System.out.println(
                "Вершина \t\t Расстояние от начала");
        for (int i = 0; i < vertCount; i++)
            System.out.println(i + 1 + " \t\t " + dist[i]);
    }

    private int minDistance(Integer[] dist, Boolean[] isVisited) {
        int min = Integer.MAX_VALUE, minIndex = -1;

        for (int v = 0; v < vertCount; v++)
            if (!isVisited[v] && dist[v] <= min) {
                min = dist[v];
                minIndex = v;
            }
        return minIndex;
    }

    public void kruskAlg() {
        int j = 0;
        int noOfEdges = 0;
        Subset[] subsets = new Subset[vertCount];

        Edge[] res = new Edge[vertCount];

        for (int i = 0; i < vertCount; i++) {
            subsets[i] = new Subset(i, 0);
        }

        while (noOfEdges < vertCount - 1) {
            Edge nextEdge = graphEdges.get(j);
            Integer x = findRoot(subsets, nextEdge.getSrc());
            Integer y = findRoot(subsets, nextEdge.getDest());

            if (!x.equals(y)) {
                res[noOfEdges] = nextEdge;
                union(subsets, x, y);
                noOfEdges++;
            }
            j++;
        }
        Integer minSize = 0;
        for (int i = 0; i < noOfEdges; i++) {
            System.out.println(res[i].getSrc() + " - " + res[i].getDest() + ": " + res[i].getWeight());
            minSize += res[i].getWeight();
        }

    }

    private void union(Subset[] subsets, Integer x, Integer y) {
        int rootX = findRoot(subsets, x);
        int rootY = findRoot(subsets, y);

        if (subsets[rootY].getRank() < subsets[rootX].getRank()) {
            subsets[rootY].setParent(rootX);
        } else if (subsets[rootX].getRank() < subsets[rootY].getRank()) {
            subsets[rootX].setParent(rootY);
        } else {
            subsets[rootY].setParent(rootX);
            Integer temp = subsets[rootX].getRank();
            temp++;
            subsets[rootX].setRank(temp);
        }
    }

    private int findRoot(Subset[] subsets, int i) {
        if (subsets[i].getParent() == i)
            return subsets[i].getParent();

        subsets[i].setParent(findRoot(subsets, subsets[i].getParent()));
        return subsets[i].getParent();
    }

    public void primAlg() {

        Integer[] parent = new Integer[vertCount];

        Integer[] key = new Integer[vertCount];

        Boolean[] isVisited = new Boolean[vertCount];

        for (int i = 0; i < vertCount; i++) {
            key[i] = INF;
            isVisited[i] = false;
        }

        key[0] = 0;

        parent[0] = -1;

        for (int count = 0; count < vertCount - 1; count++) {

            Integer u = minKey(key, isVisited);

            isVisited[u] = true;

            for (int v = 0; v < vertCount; v++)

                if (graph[u][v] != 0 && !isVisited[v]
                        && graph[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
        }
        for (int i = 1; i < vertCount; i++)
            System.out.println(parent[i] + " - " + i + "\t"
                    + graph[i][parent[i]]);
    }
    int minKey(Integer[] key, Boolean[] mstSet)
    {
        // Initialize min value
        Integer min = INF, minIndex = -1;

        for (int v = 0; v < vertCount; v++)
            if (!mstSet[v] && key[v] < min) {
                min = key[v];
                minIndex = v;
            }

        return minIndex;
    }
    public void floydWarshall()
    {
        Integer i, j, k;
        for (k = 0; k < vertCount; k++) {
            for (i = 0; i < vertCount; i++) {
                for (j = 0; j < vertCount; j++) {
                    if (graph[i][k] + graph[k][j]
                            < graph[i][j])
                        graph[i][j]
                                = graph[i][k] + graph[k][j];
                }
            }
        }
        printSolution(graph);
    }

    private void printSolution(Integer[][] dist)
    {
        System.out.println("Кратчайшие пути между парами вершин");
        for (int i = 0; i < vertCount; ++i) {
            for (int j = 0; j < vertCount; ++j) {
                if (Objects.equals(dist[i][j], INF))
                    System.out.print("INF ");
                else
                    System.out.print(dist[i][j] + "   ");
            }
            System.out.println();
        }
    }

}

@Data
@NoArgsConstructor
class Edge {
    private Integer src, dest, weight;

    public Edge(Integer src, Integer dest, Integer weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }
}

@Data
@NoArgsConstructor
class Subset {
    private Integer parent, rank;

    public Subset(Integer parent, Integer rank) {
        this.parent = parent;
        this.rank = rank;
    }
}
