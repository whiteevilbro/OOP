package pro.sys;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

/**
 * Edge list graph interface implementation.
 */
public class EdgeListGraph implements Graph {

    private final ArrayList<ArrayList<Integer>> edges = new ArrayList<>();

    @Override
    public void addDirectedEdge(int from, int to) throws NoSuchElementException {
        if ((size() <= from) || (size() <= to)) {
            throw new NoSuchElementException();
        }
        edges.get(from).add(to);
    }

    @Override
    public int addVertex() {
        edges.add(new ArrayList<>());
        return size() - 1;
    }

    @Override
    public void deleteDirectedEdge(int from, int to) throws NoSuchElementException {
        if (from < 0 || from >= size() || to < 0 || to >= size()) {
            throw new NoSuchElementException();
        }
        edges.get(from).remove(Integer.valueOf(to));
    }

    @Override
    public void deleteVertex(int vertex) throws NoSuchElementException {
        if (vertex < 0 || vertex >= size()) {
            throw new NoSuchElementException();
        }
        edges.remove(vertex);
        for (ArrayList<Integer> vertexEdges : edges) {
            vertexEdges.remove(Integer.valueOf(vertex));
        }
    }

    @Override
    public List<Integer> getNeighbours(int vertex) throws NoSuchElementException {
        if (size() <= vertex) {
            throw new NoSuchElementException();
        }
        return edges.get(vertex);
    }

    @Override
    public List<Integer> getVertices() {
        return IntStream.range(0, size()).boxed().toList();
    }

    @Override
    public int size() {
        return edges.size();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Graph other)) {
            return false;
        }
        if (size() != other.size()) {
            return false;
        }
        List<Integer> thisVertices = getVertices();
        List<Integer> otherVertices = other.getVertices();
        for (int i = 0; i < size(); i++) {
            List<Integer> thisNeighbours = getNeighbours(thisVertices.get(i));
            List<Integer> otherNeighbours = other.getNeighbours(otherVertices.get(i));
            if (thisNeighbours.size() != otherNeighbours.size()) {
                return false;
            }
            for (Integer thisNeighbour : thisNeighbours) {
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
        for (int i : getVertices()) {
            out.print(i);
            out.print(" ->");
            for (var j : getNeighbours(i)) {
                out.print(' ');
                out.print(j);
            }
            out.println();
        }
        return stream.toString();
    }
}
