package src.five.b;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ogbozoyan
 * @date 26.03.2023
 */
@Data
@NoArgsConstructor
//Ориентированный граф,можно сделать взвешанным,но как-то лень
public class Graph {
    private Integer vertexNumber;
    private List<List<Edge>> adjMat;

    public Graph(Integer vertexNumber) {
        this.vertexNumber = vertexNumber;
        adjMat = new ArrayList<>();
        for (int i = 0; i < vertexNumber; i++) {
            adjMat.add(new ArrayList<>());
        }
    }


    public void addEdge(Integer from, Integer to) {
        adjMat.get(from).add(new Edge(from, to));
    }

    public void remEdge(Integer from, Integer to) {
        if (isExist(from, to)) {
            Edge edge = getEdge(from, to);
            adjMat.get(from).remove(edge);
        }

    }

    public List<Edge> getAdjList(int v) { //return all edges from V
        return adjMat.get(v);
    }

    public Edge getEdge(int from, int to) { //get exact Edge
        return this.getAdjList(from).stream()
                .filter(
                        edge -> edge.wVertex.equals(to) && edge.vertex.equals(from)
                )
                .findFirst().orElse(null);
    }


    public boolean isExist(Integer from, Integer to) {
        Edge edge = getEdge(from, to);
        return edge != null && edge.wVertex.equals(to);
    }



    @Data
    @NoArgsConstructor
    public static class Edge {
        private Integer vertex;
        private Integer wVertex;

        public Edge(Integer from, Integer to) {
            this.vertex = from;
            this.wVertex = to;
        }
    }

}

