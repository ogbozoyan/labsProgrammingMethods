package org.example.hw3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

/**
 * @author ogbozoyan
 * @since 25.11.2023
 */
public class Main {
    private static final String spaceSplit = " ";

    public static void main(String[] args) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(System.in));
        var writer = new PrintWriter(System.out);
        //init
        String[] firstLine = reader.readLine().split(spaceSplit);

        int n = Integer.parseInt(firstLine[0]);
        String from = firstLine[1];
        String to = firstLine[2];

        List<List<String>> identMatrix = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            identMatrix.add(List.of(reader.readLine().split(spaceSplit)));
        }
        Graph graph = new Graph(identMatrix);
        //end init

        String dijkstra = graph.getDijkstraPath(from, to);

        System.out.println(dijkstra);

        writer.close();
        reader.close();
    }

    static class Graph {
        private final HashMap<String, HashMap<String, Integer>> graph;
        private final Set<String> vertexes;


        public Graph(List<List<String>> identMatrix) {
            graph = new HashMap<>();
            for (int i = 0; i < identMatrix.size(); i++) {
                String vertex = String.valueOf(i + 1);
                HashMap<String, Integer> edge = new HashMap<>();

                List<String> row = identMatrix.get(i);
                for (int j = 0; j < row.size(); j++) {
                    String destinationVertex = String.valueOf(1 + j);
                    int cost = Integer.parseInt(row.get(j));

                    if (cost < 0) {
                        continue;
                    }

                    edge.put(destinationVertex, cost);
                }

                graph.put(vertex, edge);
            }
            vertexes = graph.keySet();

        }

        public int getDijkstra(String from, String to) {

            HashMap<String, Integer> costsHashMap = initBuildCosts(from);
            HashMap<String, String> parentsHashMap = initBuildParents(from);

//            if (from.equals(to)) {
//                return costsHashMap.get(from);
//            }
            List<String> visited = new ArrayList<>();
            visited.add(from);

            String lowestCostNode = findLowestCostNode(costsHashMap, visited);
            while (lowestCostNode != null) {
                Integer cost = costsHashMap.get(lowestCostNode);

                HashMap<String, Integer> children = graph.get(lowestCostNode);
                Set<Map.Entry<String, Integer>> entries = children.entrySet();

                for (Map.Entry<String, Integer> kid : entries) {
                    String kidKey = kid.getKey();
                    Integer kidCost = kid.getValue();
                    if (Objects.equals(kidKey, from) || Objects.equals(kidKey, lowestCostNode)) {
                        continue;
                    }
                    Integer newCost = cost + kidCost;
                    if (costsHashMap.get(kidKey) > newCost) {
                        costsHashMap.put(kidKey, newCost);
                        parentsHashMap.put(kidKey, lowestCostNode);
                    }
                }
                visited.add(lowestCostNode);
                lowestCostNode = findLowestCostNode(costsHashMap, visited);
            }

            if (costsHashMap.get(to).equals(Integer.MAX_VALUE)) {
                return -1;
            } else
                return costsHashMap.get(to);
        }

        public String getDijkstraPath(String from, String to) {

            HashMap<String, Integer> costsHashMap = initBuildCosts(from);
            HashMap<String, String> parentsHashMap = initBuildParents(from);

            if (from.equals(to)) {
                return from;
            }
            List<String> visited = new ArrayList<>();
            visited.add(from);

            String lowestCostNode = findLowestCostNode(costsHashMap, visited);
            while (lowestCostNode != null) {
                Integer cost = costsHashMap.get(lowestCostNode);

                HashMap<String, Integer> children = graph.get(lowestCostNode);
                Set<Map.Entry<String, Integer>> entries = children.entrySet();

                for (Map.Entry<String, Integer> kid : entries) {
                    String kidKey = kid.getKey();
                    Integer kidCost = kid.getValue();
                    if (Objects.equals(kidKey, from) || Objects.equals(kidKey, lowestCostNode)) {
                        continue;
                    }
                    Integer newCost = cost + kidCost;
                    if (costsHashMap.get(kidKey) > newCost) {
                        costsHashMap.put(kidKey, newCost);
                        parentsHashMap.put(kidKey, lowestCostNode);
                    }
                }
                visited.add(lowestCostNode);
                lowestCostNode = findLowestCostNode(costsHashMap, visited);
            }

            if (costsHashMap.get(to).equals(Integer.MAX_VALUE)) {
                return String.valueOf(-1);
            } else {
                String node = to;
                List<String> path = new ArrayList<>();
                path.add(node);
                while (node != null) {
                    node = parentsHashMap.get(node);
                    if (node != null) {
                        path.add(node);
                    }
                }

                StringBuilder sb = new StringBuilder();
                for (int i = path.size() - 1; i >= 0; i--) {
                    sb.append(path.get(i)).append(" ");
                }
                return sb.toString();
            }
        }


        private String findLowestCostNode(HashMap<String, Integer> costs, List<String> visited) {
            Integer lowestCost = Integer.MAX_VALUE;
            String lowestCostNode = null;
            Set<Map.Entry<String, Integer>> nodes = costs.entrySet();
            for (Map.Entry<String, Integer> node : nodes) {
                Integer cost = node.getValue();
                if (cost < lowestCost && !visited.contains(node.getKey())) {
                    lowestCost = cost;
                    lowestCostNode = node.getKey();
                }
            }
            return lowestCostNode;
        }

        /**
         * Initializes the build costs for each vertex in the graph.
         *
         * @param from the starting vertex
         * @return a HashMap containing the build costs for each vertex
         */
        private HashMap<String, Integer> initBuildCosts(String from) {
            HashMap<String, Integer> costs = new HashMap<>(graph.get(from));
            Set<String> graphKeys = graph.keySet();
            for (String graphKey : graphKeys) {
                if (!costs.containsKey(graphKey)) {
                    costs.put(graphKey, Integer.MAX_VALUE);
                }
            }
            return costs;
        }

        /**
         * Initializes and builds the parent mapping for each vertex in the graph.
         *
         * @param from the starting vertex
         * @return a HashMap containing the parent mapping for each vertex
         */
        private HashMap<String, String> initBuildParents(String from) {
            HashMap<String, Integer> stringIntegerHashMap = graph.get(from);
            HashMap<String, String> res = new HashMap<>();

            Set<String> fromKeys = graph.get(from).keySet();
            for (String key : fromKeys) {
                if (stringIntegerHashMap.get(key) > 0) { //if way exist from "from",put as parent
                    res.put(key, from);
                } else { //else put null
                    res.put(key, null);
                }
            }
            Set<String> keys = graph.keySet();
            for (String key : keys) {
                if (!stringIntegerHashMap.containsKey(key)) { //if way exist from "from",put as parent
                    res.put(key, null);
                }
            }
            return res;
        }

        /**
         * Retrieves all vertices in the graph without the specified 'from' vertex.
         *
         * @param from the 'from' vertex to exclude from the result
         * @return a HashMap containing all vertices except the 'from' vertex
         */
        private HashMap<String, Integer> getAllWithoutFromVertex(String from) {
            HashMap<String, Integer> stringIntegerHashMap = graph.get(from);
            stringIntegerHashMap.remove(from);
            return stringIntegerHashMap;
        }

        public Object getGraph() {
            return graph;
        }


        public Set<String> getVertexes() {
            return vertexes;
        }

        @Override
        public String toString() {
            return "graph=" + this.getGraph();
        }
    }

}
