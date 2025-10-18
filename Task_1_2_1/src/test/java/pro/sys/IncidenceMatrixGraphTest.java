package pro.sys;

class IncidenceMatrixGraphTest extends GraphTest {

    @Override
    protected Graph createGraph() {
        return new IncidenceMatrixGraph();
    }
}