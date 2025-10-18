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
     * @param from vertex from where edge will be going.
     * @param to   vertex to where edge will be going.
     * @throws NoSuchElementException if there's no {@code from} or {@code to} vertices in graph.
     */
    void addDirectedEdge(Vertex from, Vertex to) throws NoSuchElementException;

    /**
     * Adds undirected edge from {@code from} to {@code to}.
     *
     * @param from first vertex.
     * @param to   second vertex.
     * @throws NoSuchElementException if there's no {@code from} or {@code to} vertices in graph.
     */
    default void addEdge(Vertex from, Vertex to) throws NoSuchElementException {
        addDirectedEdge(from, to);
        addDirectedEdge(to, from);
    }

    /**
     * Adds vertex to graph.
     *
     * @return vertex unique for that graph.
     */
    Vertex addVertex();

    /**
     * Builds graph from {@code in} expression and adds it to graph.
     *
     * @param in BufferedReader containig string edge list graph representation.
     * @throws IllegalArgumentException if {@code in} is invalid graph representation.
     * @throws IOException              if IO error occurs.
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
        ArrayList<Vertex> vertices = new ArrayList<>(edges.size());
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
     * @param from vertex from where edge will be going.
     * @param to   vertex to where edge will be going.
     * @throws NoSuchElementException if there's no {@code from} or {@code to} vertices in graph.
     */
    void deleteDirectedEdge(Vertex from, Vertex to) throws NoSuchElementException;

    /**
     * Removes undirected edge from {@code from} to {@code to}.
     *
     * @param from first vertex.
     * @param to   second vertex.
     * @throws NoSuchElementException if there's no {@code from} or {@code to} vertices in graph.
     */
    default void deleteEdge(Vertex from, Vertex to) throws NoSuchElementException {
        deleteDirectedEdge(from, to);
        deleteDirectedEdge(to, from);
    }

    /**
     * Removes vertex from graph.
     *
     * @param vertex vertex to remove.
     * @throws NoSuchElementException if there's no {@code vertex} vertex in graph.
     */
    void deleteVertex(Vertex vertex) throws NoSuchElementException;

    /**
     * Gets neighbours of vertex.
     *
     * @param vertex vertex to get neighbours from.
     * @return List&lt;Vertex&gt; neighbours of given vertex {@code vertex}.
     * @throws NoSuchElementException if there's no {@code vertex} vertex in graph.
     */
    List<Vertex> getNeighbours(Vertex vertex) throws NoSuchElementException;

    /**
     * Gets all vertices in graph.
     *
     * @return List&lt;Vertex&gt; all vertices in graph.
     */
    List<Vertex> getVertices();

    /**
     * Size of graph, number of vertices.
     *
     * @return int number of vertices.
     */
    int size();

    /**
     * Topological sorting of graph.
     *
     * @return List&lt;Vertex&gt; topological sorting of graph.
     * @throws IllegalStateException if graph has to topological sorting.
     */
    default Iterable<Vertex> topologicalSort() throws IllegalStateException {
        Map<Vertex, Integer> color = new HashMap<>();
        ArrayList<Vertex> vertices = new ArrayList<>(getVertices());
        Stack<Vertex> stack = new Stack<>();
        ArrayList<Vertex> result = new ArrayList<>();
        for (Vertex vertex : vertices) {
            color.put(vertex, 0);
        }
        for (Vertex vertex : vertices) {
            if (color.get(vertex) == 0) {
                stack.push(vertex);
            }
            while (!stack.empty()) {
                Vertex stackVertex = stack.peek();
                if (color.get(stackVertex) == 0) {
                    color.put(stackVertex, 1);
                    for (Vertex neighbourVertex : getNeighbours(stackVertex)) {
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

    /**
     * Graph Vertex class.
     */
    class Vertex {

    }
}
