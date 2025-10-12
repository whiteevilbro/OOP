package pro.sys;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

/**
 * Adjacency matrix graph interface implementation.
 */
@SuppressWarnings("SequencedCollectionMethodCanBeUsed")
public class AdjacencyMatrixGraph implements Graph {

    final ArrayList<ArrayList<Boolean>> matrix = new ArrayList<>();

    @Override
    public void addDirectedEdge(int from, int to) throws NoSuchElementException {
        if (from < 0 || from >= size() || to < 0 || to >= size()) {
            throw new NoSuchElementException();
        }
        matrix.get(from).set(to, true);
    }

    @Override
    public int addVertex() {
        for (int i = 0; i < size(); i++) {
            matrix.get(i).add(false);
        }
        matrix.add(new ArrayList<>());
        for (int i = 0; i < size(); i++) {
            matrix.get(matrix.size() - 1).add(false);
        }
        return size() - 1;
    }

    @Override
    public void deleteDirectedEdge(int from, int to) throws NoSuchElementException {
        if (from < 0 || from >= size() || to < 0 || to >= size()) {
            throw new NoSuchElementException();
        }
        matrix.get(from).set(to, false);
    }

    @Override
    public void deleteVertex(int vertex) throws NoSuchElementException {
        if (size() <= vertex || vertex < 0) {
            throw new NoSuchElementException();
        }
        matrix.remove(vertex);
        for (int i = 0; i < size(); i++) {
            matrix.get(i).remove(vertex);
        }
    }

    @Override
    public List<Integer> getNeighbours(int vertex) throws NoSuchElementException {
        if (size() <= vertex || vertex < 0) {
            throw new NoSuchElementException();
        }
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < size(); i++) {
            if (matrix.get(vertex).get(i)) {
                result.add(i);
            }
        }
        return result;
    }

    @Override
    public List<Integer> getVertices() {
        return IntStream.range(0, size()).boxed().toList();
    }

    @Override
    public int size() {
        return matrix.size();
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