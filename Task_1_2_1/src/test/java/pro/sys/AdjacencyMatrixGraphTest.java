package pro.sys;

class AdjacencyMatrixGraphTest extends GraphTest {

    @Override
    protected Graph createGraph() {
        return new AdjacencyMatrixGraph();
    }
}