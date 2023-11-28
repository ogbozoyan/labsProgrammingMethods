package org.example.hw3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * @author ogbozoyan
 * @since 25.11.2023
 */
public class Main {
    private static final String spaceSplit = " ";

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/main/java/org/example/hw3/input.txt"));
//        BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
//        BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/org/example/hw3/output.txt"));

//        var reader = new BufferedReader(new InputStreamReader(System.in));
        var writer = new PrintWriter(System.out);

        //init

        String[] firstLine = reader.readLine().split(spaceSplit);

        int N = Integer.parseInt(firstLine[0]);
        int K = Integer.parseInt(firstLine[1]);

        ArrayList<String> listEdges = new ArrayList<>();
        for (int i = 0; i < K; i++) {
            listEdges.add(reader.readLine());
        }

        String[] lastLine = reader.readLine().split(spaceSplit);
        String from = lastLine[0];
        String to = lastLine[1];

        Graph graph = new Graph(listEdges);
        //end init

        Long dijkstra = graph.getDijkstraFast(from, to);

        System.out.println(dijkstra);
        writer.close();
        reader.close();
    }

    static class Graph {
        private final List<List<Edge>> graph;

        static class Edge {
            public String vertex;
            public Long cost;

            public Edge(String vertex, Long cost) {
                this.vertex = vertex;
                this.cost = cost;
            }

            public String getVertex() {
                return vertex;
            }


            public Long getCost() {
                return cost;
            }

        }

        public Graph(ArrayList<String> listEdges) {
            graph = new ArrayList<>();

            for (int i = 0; i < listEdges.size() + 5; i++) {
                graph.add(new ArrayList<>());
            }

            for (String listEdge : listEdges) {

                String[] s = listEdge.split(" ");

                String from = s[0];
                String to = s[1];
                long value = Long.parseLong(s[2]);
                addEdge(from, to, value);
            }

        }

        private void addEdge(String from, String to, Long cost) {
            graph.get(Integer.parseInt(from)).add(new Edge(to, cost));
            graph.get(Integer.parseInt(to)).add(new Edge(from, cost));
        }


        //O(E logV)
        public Long getDijkstraFast(String from, String to) {
            long[] costs = new long[graph.size() + 1];
            Arrays.fill(costs, Long.MAX_VALUE);

            PriorityQueue<Edge> visited = new PriorityQueue<>(Comparator.comparingLong(Edge::getCost));

            costs[Integer.parseInt(from)] = 0L;
            visited.add(new Edge(from, 0L));
            while (!visited.isEmpty()) {
                Edge cur = visited.poll();
                int curVertex = Integer.parseInt(cur.vertex);

                Long curVertexCost = costs[curVertex];


                for (Edge kid : graph.get(curVertex)) {
                    String kidKey = kid.vertex;

                    int kidVertex = Integer.parseInt(kidKey);
                    Long kidCost = kid.cost;

                    long newCost = curVertexCost + kidCost;
                    if (costs[kidVertex] > newCost) {
                        costs[kidVertex] = newCost;
                        visited.add(new Edge(kidKey, newCost));
                    }
                }
            }

            if (costs[Integer.parseInt(to)] == Long.MAX_VALUE) {
                return -1L;
            } else
                return costs[Integer.parseInt(to)];
        }

        public Object getGraph() {
            return graph;
        }


        @Override
        public String toString() {
            return "graph=" + this.getGraph();
        }
    }

}