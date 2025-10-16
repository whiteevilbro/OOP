package pro.sys;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import org.junit.jupiter.api.Test;
import pro.sys.Graph.Vertex;

@SuppressWarnings({"DuplicateExpressions", "SequencedCollectionMethodCanBeUsed"})
abstract class GraphTest {

    private static boolean isTopsort(Graph graph, Iterable<Vertex> sort) {
        Set<Vertex> set = new HashSet<>();
        for (Vertex vertex : sort) {
            for (Vertex neighbour : graph.getNeighbours(vertex)) {
                if (!set.contains(neighbour)) {
                    return false;
                }
            }
            set.add(vertex);
        }
        return true;
    }

    protected abstract Graph createGraph();

    @Test
    void testAddDirectedEdge() {
        Graph graph = createGraph();
        Vertex vertexOne = graph.addVertex();
        Vertex vertexTwo = graph.addVertex();
        graph.addDirectedEdge(vertexOne, vertexTwo);
        String expected = """
            0 -> 1
            1 ->
            """.strip().replace("\r\n", "\n");

        // I couldn't come up with any reasonable non-string comparison
        // while testing basic constructing functionality
        assertEquals(expected, graph.toString().strip().replace("\r\n", "\n"));

        Vertex vertexFalse = new Vertex();
        assertThrows(NoSuchElementException.class,
            () -> graph.addDirectedEdge(vertexFalse, vertexTwo));
    }

    @Test
    void testAddEdge() {
        Graph graph = createGraph();
        Graph graphExpected = createGraph();
        Vertex vertex0 = graph.addVertex();
        Vertex vertex1 = graph.addVertex();
        graph.addEdge(vertex0, vertex1);

        Vertex evertex0 = graphExpected.addVertex();
        Vertex evertex1 = graphExpected.addVertex();
        graphExpected.addDirectedEdge(evertex0, evertex1);
        graphExpected.addDirectedEdge(evertex1, evertex0);

        assertEquals(graphExpected, graph);
    }

    @Test
    void testAddVertex() {
        Graph graph = createGraph();
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

    @Test
    void testBuildFrom() throws IOException {
        Graph graph = createGraph();
        Graph builded = createGraph();
        Vertex[] vertices = new Vertex[4];
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

    @Test
    void testDeleteDirectedEdge() {
        Graph graph = createGraph();
        Vertex vertexOne = graph.addVertex();
        Vertex vertexTwo = graph.addVertex();
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
        graph.deleteDirectedEdge(vertexOne, vertexTwo);
        assertEquals(expected, graph.toString().strip().replace("\r\n", "\n"));

        Vertex vertexFalse = new Vertex();
        assertThrows(NoSuchElementException.class,
            () -> graph.deleteDirectedEdge(vertexFalse, vertexTwo));
    }

    @Test
    void testDeleteEdge() {
        Graph graph = createGraph();
        Graph expectedGraph = createGraph();
        Vertex vertex0 = graph.addVertex();
        Vertex vertex1 = graph.addVertex();
        Vertex vertex2 = graph.addVertex();
        graph.addEdge(vertex0, vertex1);
        graph.addEdge(vertex0, vertex2);
        graph.deleteEdge(vertex0, vertex1);

        Vertex evertex0 = expectedGraph.addVertex();
        expectedGraph.addVertex();
        Vertex evertex2 = expectedGraph.addVertex();
        expectedGraph.addEdge(evertex0, evertex2);

        assertEquals(expectedGraph, graph);
    }

    @Test
    void testDeleteVertex() {
        Graph graph = createGraph();
        Vertex vertex = graph.addVertex();
        Vertex vertexTwo = graph.addVertex();
        graph.addDirectedEdge(vertexTwo, vertex);
        String expected = """
            0 ->
            1 -> 0
            """.strip().replace("\r\n", "\n");

        assertEquals(expected, graph.toString().strip().replace("\r\n", "\n"));

        expected = """
            0 ->
            """.strip().replace("\r\n", "\n");
        graph.deleteVertex(graph.getVertices().get(0));
        assertEquals(expected, graph.toString().strip().replace("\r\n", "\n"));

        Vertex vertexFalse = new Vertex();
        assertThrows(NoSuchElementException.class, () -> graph.deleteVertex(vertexFalse));
    }

    @Test
    void testEquals() {
        Graph graph = createGraph();
        Graph graphTwo = createGraph();
        Vertex vertex00 = graph.addVertex();
        Vertex vertex01 = graph.addVertex();
        graph.addDirectedEdge(vertex00, vertex01);

        Vertex vertex10 = graphTwo.addVertex();
        Vertex vertex11 = graphTwo.addVertex();
        graphTwo.addDirectedEdge(vertex10, vertex11);

        assertEquals(graph, graphTwo);
        final Vertex vertex02 = graph.addVertex();

        assertNotEquals(graph, graphTwo);

        Vertex vertex12 = graphTwo.addVertex();
        graphTwo.addDirectedEdge(vertex10, vertex12);
        assertNotEquals(graph, graphTwo);

        graph.addDirectedEdge(vertex00, vertex02);
        assertEquals(graph, graphTwo);

        graph.addDirectedEdge(vertex02, vertex01);
        graphTwo.addDirectedEdge(vertex12, vertex10);
        assertNotEquals(graph, graphTwo);
    }

    @Test
    void testGetNeighbours() {
        Graph graph = createGraph();
        Vertex vertex = graph.addVertex();
        Vertex neighbourOne = graph.addVertex();
        Vertex neighbourTwo = graph.addVertex();
        graph.addDirectedEdge(vertex, neighbourOne);
        graph.addDirectedEdge(vertex, neighbourTwo);

        List<Vertex> neighbours = graph.getNeighbours(vertex);
        assertTrue(neighbours.contains(neighbourOne));
        assertTrue(neighbours.contains(neighbourTwo));

        Vertex vertexFalse = new Vertex();
        assertThrows(NoSuchElementException.class, () -> graph.getNeighbours(vertexFalse));
    }

    @Test
    void testGetVertices() {
        Graph graph = createGraph();
        Vertex vertexOne = graph.addVertex();
        Vertex vertexTwo = graph.addVertex();
        Vertex vertexThree = graph.addVertex();

        List<Vertex> neighbours = graph.getVertices();
        assertTrue(neighbours.contains(vertexOne));
        assertTrue(neighbours.contains(vertexTwo));
        assertTrue(neighbours.contains(vertexThree));
    }

    @Test
    void testSize() {
        Graph graph = createGraph();
        assertEquals(0, graph.size());
        graph.addVertex();
        assertEquals(1, graph.size());
        graph.addVertex();
        assertEquals(2, graph.size());
        graph.addVertex();
        assertEquals(3, graph.size());
    }

    @Test
    void testToString() {
        Graph graph = createGraph();
        final Vertex vertexOne = graph.addVertex();
        String expected = """
            0 ->
            """.strip().replace("\r\n", "\n");

        assertEquals(expected, graph.toString().strip().replace("\r\n", "\n"));

        final Vertex vertexTwo = graph.addVertex();
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

    @Test
    void testTopologicalSort() {
        Graph graph = createGraph();

        Vertex[] vertices = new Vertex[4];
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