package pro.sys;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings("DuplicateExpressions")
class GraphTest {

    static Stream<Class<? extends Graph>> get_implementations() {
        return Stream.of(AdjacencyMatrixGraph.class, EdgeListGraph.class,
            IncidenceMatrixGraph.class);
    }

    private static boolean isTopsort(Graph graph, Iterable<Integer> sort) {
        Set<Integer> set = new HashSet<>();
        for (int vertex : sort) {
            for (int neighbour : graph.getNeighbours(vertex)) {
                if (!set.contains(neighbour)) {
                    return false;
                }
            }
            set.add(vertex);
        }
        return true;
    }

    @ParameterizedTest
    @MethodSource("get_implementations")
    void testAddDirectedEdge(Class<? extends Graph> graphclass) {
        Graph graph;
        try {
            graph = graphclass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            fail();
            return;
        }
        int vertexOne = graph.addVertex();
        int vertexTwo = graph.addVertex();
        graph.addDirectedEdge(vertexOne, vertexTwo);
        String expected = """
            0 -> 1
            1 ->
            """.strip().replace("\r\n", "\n");

        // I couldn't come up with any reasonable non-string comparison
        // while testing basic constructing functionality
        assertEquals(expected, graph.toString().strip().replace("\r\n", "\n"));

        int vertexFalse = vertexOne + vertexTwo + 1;
        assertThrows(NoSuchElementException.class,
            () -> graph.addDirectedEdge(vertexFalse, vertexTwo));
    }

    @ParameterizedTest
    @MethodSource("get_implementations")
    void testAddEdge(Class<? extends Graph> graphclass) {
        Graph graph;
        Graph graphExpected;
        try {
            graph = graphclass.getDeclaredConstructor().newInstance();
            graphExpected = graphclass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            fail();
            return;
        }
        int vertex0 = graph.addVertex();
        int vertex1 = graph.addVertex();
        graph.addEdge(vertex0, vertex1);

        int evertex0 = graphExpected.addVertex();
        int evertex1 = graphExpected.addVertex();
        graphExpected.addDirectedEdge(evertex0, evertex1);
        graphExpected.addDirectedEdge(evertex1, evertex0);

        assertEquals(graphExpected, graph);
    }

    @ParameterizedTest
    @MethodSource("get_implementations")
    void testAddVertex(Class<? extends Graph> graphclass) {
        Graph graph;
        try {
            graph = graphclass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            fail();
            return;
        }
        graph.addVertex();
        String expected = """
            0 ->
            """.strip().replace("\r\n", "\n");

        // I couldn't come up with any reasonable non-string comparison
        // while testing basic constructing functionality
        assertEquals(expected, graph.toString().strip().replace("\r\n", "\n"));

        graph.addVertex();
        expected = """
            0 ->
            1 ->
            """.strip().replace("\r\n", "\n");

        // I couldn't come up with any reasonable non-string comparison
        // while testing basic constructing functionality
        assertEquals(expected, graph.toString().strip().replace("\r\n", "\n"));
    }

    @ParameterizedTest
    @MethodSource("get_implementations")
    void testBuildFrom(Class<? extends Graph> graphclass) throws IOException {
        Graph graph;
        Graph builded;
        try {
            graph = graphclass.getDeclaredConstructor().newInstance();
            builded = graphclass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            fail();
            return;
        }
        int[] vertices = new int[4];
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = graph.addVertex();
        }
        graph.addEdge(vertices[0], vertices[1]);
        graph.addEdge(vertices[1], vertices[2]);
        graph.addEdge(vertices[2], vertices[3]);
        graph.addDirectedEdge(vertices[3], vertices[0]);
        graph.addDirectedEdge(vertices[3], vertices[1]);
        graph.addDirectedEdge(vertices[2], vertices[0]);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(stream);
        Arrays.asList(graph.toString().split("\n"))
            .forEach((String s) -> printStream.println(s.substring(4)));

        InputStream inputStream = new ByteArrayInputStream(stream.toByteArray());
        builded.buildFrom(new BufferedReader(new InputStreamReader(inputStream)));

        assertEquals(graph, builded);
    }

    @ParameterizedTest
    @MethodSource("get_implementations")
    void testDeleteDirectedEdge(Class<? extends Graph> graphclass) {
        Graph graph;
        try {
            graph = graphclass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            fail();
            return;
        }
        int vertexOne = graph.addVertex();
        int vertexTwo = graph.addVertex();
        graph.addDirectedEdge(vertexOne, vertexTwo);
        String expected = """
            0 -> 1
            1 ->
            """.strip().replace("\r\n", "\n");
        assertEquals(expected, graph.toString().strip().replace("\r\n", "\n"));

        expected = """
            0 ->
            1 ->
            """.strip().replace("\r\n", "\n");
        graph.deleteDirectedEdge(0, 1);
        assertEquals(expected, graph.toString().strip().replace("\r\n", "\n"));

        int vertexFalse = vertexOne + vertexTwo + 1;
        assertThrows(NoSuchElementException.class,
            () -> graph.deleteDirectedEdge(vertexFalse, vertexTwo));
    }

    @ParameterizedTest
    @MethodSource("get_implementations")
    void testDeleteEdge(Class<? extends Graph> graphclass) {
        Graph graph;
        Graph expectedGraph;
        try {
            graph = graphclass.getDeclaredConstructor().newInstance();
            expectedGraph = graphclass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            fail();
            return;
        }
        int vertex0 = graph.addVertex();
        int vertex1 = graph.addVertex();
        int vertex2 = graph.addVertex();
        graph.addEdge(vertex0, vertex1);
        graph.addEdge(vertex0, vertex2);
        graph.deleteEdge(vertex0, vertex1);

        int evertex0 = expectedGraph.addVertex();
        expectedGraph.addVertex();
        int evertex2 = expectedGraph.addVertex();
        expectedGraph.addEdge(evertex0, evertex2);

        assertEquals(expectedGraph, graph);
    }

    @ParameterizedTest
    @MethodSource("get_implementations")
    void testDeleteVertex(Class<? extends Graph> graphclass) {
        Graph graph;
        try {
            graph = graphclass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            fail();
            return;
        }
        int vertex = graph.addVertex();
        int vertexTwo = graph.addVertex();
        graph.addDirectedEdge(vertexTwo, vertex);
        String expected = """
            0 ->
            1 -> 0
            """.strip().replace("\r\n", "\n");

        assertEquals(expected, graph.toString().strip().replace("\r\n", "\n"));

        expected = """
            0 ->
            """.strip().replace("\r\n", "\n");
        graph.deleteVertex(graph.getVertices().getFirst());
        assertEquals(expected, graph.toString().strip().replace("\r\n", "\n"));

        int vertexFalse = vertex + vertexTwo + 1;
        assertThrows(NoSuchElementException.class, () -> graph.deleteVertex(vertexFalse));
    }

    @ParameterizedTest
    @MethodSource("get_implementations")
    void testEquals(Class<? extends Graph> graphclass) {
        Graph graph;
        Graph graphTwo;
        try {
            graph = graphclass.getDeclaredConstructor().newInstance();
            graphTwo = graphclass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            fail();
            return;
        }
        int vertex00 = graph.addVertex();
        int vertex01 = graph.addVertex();
        graph.addDirectedEdge(vertex00, vertex01);

        int vertex10 = graphTwo.addVertex();
        int vertex11 = graphTwo.addVertex();
        graphTwo.addDirectedEdge(vertex10, vertex11);

        assertEquals(graph, graphTwo);
        int vertex02 = graph.addVertex();

        assertNotEquals(graph, graphTwo);

        int vertex12 = graphTwo.addVertex();
        graphTwo.addDirectedEdge(vertex10, vertex12);
        assertNotEquals(graph, graphTwo);

        graph.addDirectedEdge(vertex00, vertex02);
        assertEquals(graph, graphTwo);

        graph.addDirectedEdge(vertex02, vertex01);
        graphTwo.addDirectedEdge(vertex12, vertex10);
        assertNotEquals(graph, graphTwo);
    }

    @ParameterizedTest
    @MethodSource("get_implementations")
    void testGetNeighbours(Class<? extends Graph> graphclass) {
        Graph graph;
        try {
            graph = graphclass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            fail();
            return;
        }
        int vertex = graph.addVertex();
        int neighbourOne = graph.addVertex();
        int neighbourTwo = graph.addVertex();
        graph.addDirectedEdge(vertex, neighbourOne);
        graph.addDirectedEdge(vertex, neighbourTwo);

        List<Integer> neighbours = graph.getNeighbours(vertex);
        assertTrue(neighbours.contains(neighbourOne));
        assertTrue(neighbours.contains(neighbourTwo));

        int vertexFalse = vertex + neighbourTwo + neighbourOne + 1;
        assertThrows(NoSuchElementException.class, () -> graph.getNeighbours(vertexFalse));
    }

    @ParameterizedTest
    @MethodSource("get_implementations")
    void testGetVertices(Class<? extends Graph> graphclass) {
        Graph graph;
        try {
            graph = graphclass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            fail();
            return;
        }
        int vertexOne = graph.addVertex();
        int vertexTwo = graph.addVertex();
        int vertexThree = graph.addVertex();

        List<Integer> neighbours = graph.getVertices();
        assertTrue(neighbours.contains(vertexOne));
        assertTrue(neighbours.contains(vertexTwo));
        assertTrue(neighbours.contains(vertexThree));
    }

    @ParameterizedTest
    @MethodSource("get_implementations")
    void testSize(Class<? extends Graph> graphclass) {
        Graph graph;
        try {
            graph = graphclass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            fail();
            return;
        }
        assertEquals(0, graph.size());
        graph.addVertex();
        assertEquals(1, graph.size());
        graph.addVertex();
        assertEquals(2, graph.size());
        graph.addVertex();
        assertEquals(3, graph.size());
    }

    @ParameterizedTest
    @MethodSource("get_implementations")
    void testToString(Class<? extends Graph> graphclass) {
        Graph graph;
        try {
            graph = graphclass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            fail();
            return;
        }
        int vertexOne = graph.addVertex();
        String expected = """
            0 ->
            """.strip().replace("\r\n", "\n");

        assertEquals(expected, graph.toString().strip().replace("\r\n", "\n"));

        int vertexTwo = graph.addVertex();
        expected = """
            0 ->
            1 ->
            """.strip().replace("\r\n", "\n");

        assertEquals(expected, graph.toString().strip().replace("\r\n", "\n"));

        graph.addDirectedEdge(vertexOne, vertexTwo);
        expected = """
            0 -> 1
            1 ->
            """.strip().replace("\r\n", "\n");

        assertEquals(expected, graph.toString().strip().replace("\r\n", "\n"));

        graph.addDirectedEdge(vertexTwo, vertexOne);
        expected = """
            0 -> 1
            1 -> 0
            """.strip().replace("\r\n", "\n");

        assertEquals(expected, graph.toString().strip().replace("\r\n", "\n"));
    }

    @ParameterizedTest
    @MethodSource("get_implementations")
    void testTopologicalSort(Class<? extends Graph> graphclass) {
        Graph graph;
        try {
            graph = graphclass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            fail();
            return;
        }

        int[] vertices = new int[4];
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = graph.addVertex();
        }
        graph.addDirectedEdge(vertices[0], vertices[1]);
        graph.addDirectedEdge(vertices[0], vertices[2]);
        graph.addDirectedEdge(vertices[1], vertices[3]);
        graph.addDirectedEdge(vertices[2], vertices[3]);

        assertTrue(isTopsort(graph, graph.topologicalSort()));

        graph.addDirectedEdge(vertices[3], vertices[0]);
        assertThrows(IllegalStateException.class, graph::topologicalSort);
    }
}