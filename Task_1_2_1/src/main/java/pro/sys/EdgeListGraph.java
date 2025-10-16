package pro.sys;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Edge list graph interface implementation.
 */
public class EdgeListGraph implements Graph {

    private final ArrayList<ArrayList<Vertex>> edges = new ArrayList<>();
    private final ArrayList<Vertex> vertices = new ArrayList<>();

    @Override
    public void addDirectedEdge(Vertex from, Vertex to) throws NoSuchElementException {
        if (!vertices.contains(from) || !vertices.contains(to)) {
            throw new NoSuchElementException();
        }
        edges.get(vertices.indexOf(from)).add(to);
    }

    @Override
    public Vertex addVertex() {
        edges.add(new ArrayList<>());
        vertices.add(new Vertex());
        return vertices.get(size() - 1);
    }

    @Override
    public void deleteDirectedEdge(Vertex from, Vertex to) throws NoSuchElementException {
        if (!vertices.contains(from) || !vertices.contains(to)) {
            throw new NoSuchElementException();
        }
        edges.get(vertices.indexOf(from)).remove(to);
    }

    @Override
    public void deleteVertex(Vertex vertex) throws NoSuchElementException {
        if (!vertices.contains(vertex)) {
            throw new NoSuchElementException();
        }
        edges.remove(vertices.indexOf(vertex));
        for (ArrayList<Vertex> vertexEdges : edges) {
            vertexEdges.remove(vertex);
        }
        vertices.remove(vertex);
    }

    @Override
    public List<Vertex> getNeighbours(Vertex vertex) throws NoSuchElementException {
        if (!vertices.contains(vertex)) {
            throw new NoSuchElementException();
        }
        return new ArrayList<>(edges.get(vertices.indexOf(vertex)));
    }

    @Override
    public List<Vertex> getVertices() {
        return new ArrayList<>(vertices);
    }

    @Override
    public int size() {
        return vertices.size();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Graph other)) {
            return false;
        }
        if (size() != other.size()) {
            return false;
        }
        List<Vertex> thisVertices = getVertices();
        List<Vertex> otherVertices = other.getVertices();
        for (int i = 0; i < size(); i++) {
            List<Vertex> thisNeighbours = getNeighbours(thisVertices.get(i));
            List<Vertex> otherNeighbours = other.getNeighbours(otherVertices.get(i));
            if (thisNeighbours.size() != otherNeighbours.size()) {
                return false;
            }
            for (Vertex thisNeighbour : thisNeighbours) {
                if (!otherNeighbours.contains(
                    otherVertices.get(thisVertices.indexOf(thisNeighbour)))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(stream);
        for (Vertex i : getVertices()) {
            out.print(vertices.indexOf(i));
            out.print(" ->");
            for (Vertex j : getNeighbours(i)) {
                out.print(' ');
                out.print(vertices.indexOf(j));
            }
            out.println();
        }
        return stream.toString();
    }
}
