package pro.sys;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

/**
 * Graph interface.
 */
public interface Graph {

    /**
     * Adds directed edge from {@code from} to {@code to}.
     *
     * @param from vertex integer from where edge will be going.
     * @param to vertex integer to where edge will be going.
     * @throws NoSuchElementException if there's no {@code from} or {@code to} vertices in graph.
     */
    void addDirectedEdge(int from, int to) throws NoSuchElementException;

    /**
     * Adds undirected edge from {@code from} to {@code to}.
     *
     * @param from first vertex integer.
     * @param to second vertex integer.
     * @throws NoSuchElementException if there's no {@code from} or {@code to} vertices in graph.
     */
    default void addEdge(int from, int to) throws NoSuchElementException {
        addDirectedEdge(from, to);
        addDirectedEdge(to, from);
    }

    /**
     * Adds vertex to graph.
     *
     * @return vertex Integer unique for that graph.
     */
    int addVertex();

    /**
     * Builds graph from {@code in} expression and adds it to graph.
     *
     * @param in BufferedReader containig string edge list graph representation.
     * @throws IllegalArgumentException if {@code in} is invalid graph representation.
     * @throws IOException if IO error occurs.
     */
    default void buildFrom(BufferedReader in) throws IllegalArgumentException, IOException {
        ArrayList<String> edges = new ArrayList<>();

        while (true) {
            String line = in.readLine();
            if (line == null) {
                break;
            }
            if (!line.isBlank()) {
                edges.add(line);
            }
        }
        ArrayList<Integer> vertices = new ArrayList<>(edges.size());
        for (int i = 0; i < edges.size(); i++) {
            vertices.add(addVertex());
        }
        for (int i = 0; i < edges.size(); i++) {
            Scanner scanner = new Scanner(edges.get(i));
            while (scanner.hasNextInt()) {
                int to = scanner.nextInt();
                if (to < 0 || to >= edges.size()) {
                    throw new IllegalArgumentException();
                }
                addDirectedEdge(vertices.get(i), vertices.get(to));
            }
        }
    }

    /**
     * Removes directed edge from {@code from} to {@code to}.
     *
     * @param from vertex integer from where edge will be going.
     * @param to vertex integer to where edge will be going.
     * @throws NoSuchElementException if there's no {@code from} or {@code to} vertices in graph.
     */
    void deleteDirectedEdge(int from, int to) throws NoSuchElementException;

    /**
     * Removes undirected edge from {@code from} to {@code to}.
     *
     * @param from first vertex integer.
     * @param to second vertex integer.
     * @throws NoSuchElementException if there's no {@code from} or {@code to} vertices in graph.
     */
    default void deleteEdge(int from, int to) throws NoSuchElementException {
        deleteDirectedEdge(from, to);
        deleteDirectedEdge(to, from);
    }

    /**
     * Removes vertex from graph.
     *
     * @param vertex integer, vertex to remove.
     * @throws NoSuchElementException if there's no {@code vertex} vertex in graph.
     */
    void deleteVertex(int vertex) throws NoSuchElementException;

    /**
     * Gets neighbours of vertex.
     *
     * @param vertex integer, vertex to remove.
     * @return List&lt;Integer&gt; neighbours of given vertex {@code vertex}.
     * @throws NoSuchElementException if there's no {@code vertex} vertex in graph.
     */
    List<Integer> getNeighbours(int vertex) throws NoSuchElementException;

    /**
     * Gets all vertices in graph.
     *
     * @return List&lt;Integer&gt; all vertices in graph.
     */
    List<Integer> getVertices();

    /**
     * Size of graph, number of vertices.
     *
     * @return int number of vertices.
     */
    int size();

    /**
     * Topological sorting of graph.
     *
     * @return List&lt;Integer&gt; topological sorting of graph.
     * @throws IllegalStateException if graph has to topological sorting.
     */
    default Iterable<Integer> topologicalSort() throws IllegalStateException {
        Map<Integer, Integer> color = new HashMap<>();
        ArrayList<Integer> vertices = new ArrayList<>(getVertices());
        Stack<Integer> stack = new Stack<>();
        ArrayList<Integer> result = new ArrayList<>();
        for (int vertex : vertices) {
            color.put(vertex, 0);
        }
        for (int vertex : vertices) {
            if (color.get(vertex) == 0) {
                stack.push(vertex);
            }
            while (!stack.empty()) {
                int stackVertex = stack.peek();
                if (color.get(stackVertex) == 0) {
                    color.put(stackVertex, 1);
                    for (int neighbourVertex : getNeighbours(stackVertex)) {
                        switch (color.get(neighbourVertex)) {
                            case 0:
                                stack.push(neighbourVertex);
                                break;
                            case 1:
                                throw new IllegalStateException();
                            case 2: // FALLTHROUGH
                            default:
                                break;
                        }
                    }
                } else if (color.get(stackVertex) == 1) {
                    color.put(stackVertex, 2);
                    result.add(stackVertex);
                    stack.pop();
                }
            }
        }

        return result;
    }
}
