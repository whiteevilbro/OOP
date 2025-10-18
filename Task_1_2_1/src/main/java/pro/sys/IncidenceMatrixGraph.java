package pro.sys;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Incidence matrix graph interface implementation.
 */
@SuppressWarnings("SequencedCollectionMethodCanBeUsed")
public class IncidenceMatrixGraph implements Graph {

    private final ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();
    private final ArrayList<Vertex> vertices = new ArrayList<>();

    @Override
    public void addDirectedEdge(Vertex from, Vertex to) throws NoSuchElementException {
        if (!vertices.contains(from) || !vertices.contains(to)) {
            throw new NoSuchElementException();
        }
        for (int i = 0; i < size(); i++) {
            if (vertices.get(i) == from) {
                matrix.get(i).add(-1);
            } else if (vertices.get(i) == to) {
                matrix.get(i).add(1);
            } else {
                matrix.get(i).add(0);
            }
        }
    }

    @Override
    public Vertex addVertex() {
        ArrayList<Integer> vertex = new ArrayList<>();
        matrix.add(vertex);
        for (int i = 0; i < matrix.get(0).size(); i++) {
            vertex.add(0);
        }
        vertices.add(new Vertex());
        return vertices.get(size() - 1);
    }

    @Override
    public void deleteDirectedEdge(Vertex from, Vertex to) throws NoSuchElementException {
        if (!vertices.contains(from) || !vertices.contains(to)) {
            throw new NoSuchElementException();
        }
        for (int i = 0; i < matrix.get(0).size(); i++) {
            if (matrix.get(vertices.indexOf(from)).get(i) == -1
                && matrix.get(vertices.indexOf(to)).get(i) == 1) {
                for (int j = 0; j < size(); j++) {
                    matrix.get(j).remove(i);
                }
            }
        }
    }

    @Override
    public void deleteVertex(Vertex vertex) throws NoSuchElementException {
        if (!vertices.contains(vertex)) {
            throw new NoSuchElementException();
        }
        matrix.remove(vertices.indexOf(vertex));
        vertices.remove(vertex);
    }

    @Override
    public List<Vertex> getNeighbours(Vertex vertex) throws NoSuchElementException {
        if (!vertices.contains(vertex)) {
            throw new NoSuchElementException();
        }
        ArrayList<Vertex> result = new ArrayList<>();
        for (int i = 0; i < matrix.get(vertices.indexOf(vertex)).size(); i++) {
            if (matrix.get(vertices.indexOf(vertex)).get(i) == -1) {
                for (int j = 0; j < matrix.size(); j++) {
                    if (matrix.get(j).get(i) == 1) {
                        result.add(vertices.get(j));
                    }
                }
            }
        }
        return result;
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
