package entities;

public class Vertex {
    public Gebieden gebieden;
    public boolean wasVisited;
    public boolean isInTree;

    public Vertex(Gebieden gebied) {
        this.gebieden = gebied;
        wasVisited = false;
        isInTree = false;
    }
}
