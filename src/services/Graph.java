package services;

import adt.Queue;
import adt.Stack;
import entities.Gebieden;
import entities.Vertex;

public class Graph {
    private final int MAX_VERTS = 13;
    private final int INFINITY = 10000;
    private Vertex[] vertexList;
    private int[][] adjMat;
    private int nVerts;
    private int nTree;
    private DistPar[] path;
    private int currentVert;
    private int startToCurrent;
    private Stack stack;
    private Queue queue;

    public Graph() {
        vertexList = new Vertex[MAX_VERTS];
        adjMat = new int[MAX_VERTS][MAX_VERTS];
        nVerts = 0;
        for (int row = 0; row < MAX_VERTS; row++) {
            for (int column = 0; column < MAX_VERTS; column++) {
                adjMat[row][column] = INFINITY;
            }
        }
        path = new DistPar[MAX_VERTS];
        stack = new Stack();
        queue = new Queue();
    }

    public void addVertex(Gebieden gebied) {
        vertexList[nVerts++] = new Vertex(gebied);
    }

    public void addEdge(Gebieden start, Gebieden end, int weight) {
        int starting = findIndexOf(start);
        int ending = findIndexOf(end);
        adjMat[starting][ending] = weight;
    }

    public void displayVertexName(int vertex) {
        System.out.print(vertexList[vertex].gebieden.getNaam());
    }

//    public void displayVertexInfo(int vertex) {
//        System.out.print(vertexList[vertex].gebieden.getNaam() + " ");
//        System.out.print("met " + vertexList[vertex].gebieden.getnStraten() + " straten ");
//        System.out.print("en ⩲" + vertexList[vertex].gebieden.getnGebouwen() + " gebouwen.");
//        System.out.println();
//    }

    public void displayVertexInfo(int vertex) {
        System.out.print(vertexList[vertex].gebieden.toString());
        System.out.println();
    }

    public void dfs() {
        vertexList[0].wasVisited = true;
        displayVertexInfo(0);
        System.out.println();
        stack.push(0);
        int countVertex = 1;
        while (!stack.isEmpty()) {
            int vertex = getAdjUnvisitedVertex(stack.peek());
            if (vertex == -1) {
                stack.pop();
            } else {
                vertexList[vertex].wasVisited = true;
                displayVertexInfo(vertex);
                System.out.println();
                stack.push(vertex);
                countVertex++;
            }
        }
        for (int vertex = 0; vertex < nVerts; vertex++) {
            vertexList[vertex].wasVisited = false;
        }
        System.out.println("Aantal gebieden: " + countVertex);
    }

    public void FTTC() {
        vertexList[0].wasVisited = true;
        stack.push(0);
        int countVertex = 0;
        System.out.println("Gebieden met Fiber To The Curb:");
        if (vertexList[0].gebieden.isADSL()) {
            displayVertexName(0);
            System.out.println();
            countVertex = 1;
        }

        while (!stack.isEmpty()) {
            int vertex = getAdjUnvisitedVertex(stack.peek());
            if (vertex == -1) {
                stack.pop();
            } else {
                vertexList[vertex].wasVisited = true;

                if (vertexList[vertex].gebieden.isADSL()) {
                    displayVertexName(vertex);
                    System.out.println();
                    countVertex++;
                }
                stack.push(vertex);
            }
        }
        for (int vertex = 0; vertex < nVerts; vertex++) {
            vertexList[vertex].wasVisited = false;
        }
        System.out.println("Aantal gebieden: " + countVertex);
    }

    public void FTTH() {
        vertexList[0].wasVisited = true;
        stack.push(0);
        int countVertex = 0;

        System.out.println("Gebieden met Fiber To The Home:");
        if (!vertexList[0].gebieden.isADSL()) {
            displayVertexName(0);
            System.out.println();
            countVertex = 1;
        }

        while (!stack.isEmpty()) {
            int vertex = getAdjUnvisitedVertex(stack.peek());
            if (vertex == -1) {
                stack.pop();
            } else {
                vertexList[vertex].wasVisited = true;

                if (!vertexList[vertex].gebieden.isADSL()) {
                    displayVertexName(vertex);
                    System.out.println();
                    countVertex++;
                }
                stack.push(vertex);
            }
        }
        for (int vertex = 0; vertex < nVerts; vertex++) {
            vertexList[vertex].wasVisited = false;
        }
        System.out.println("Aantal gebieden: " + countVertex);
    }

    public void bfs() {
        vertexList[0].wasVisited = true;
        displayVertexName(0);
        System.out.println();
        queue.insert(0);
        int v2;
        while (!queue.isEmpty()) {
            int v1 = queue.remove();
            while ((v2 = getAdjUnvisitedVertex(v1)) != -1 && v1 != 0) {
                vertexList[v2].wasVisited = true;
                System.out.print("Indirect verbonden: ");
                displayVertexName(v2);
                System.out.println();
                queue.insert(v2);
            }
            while ((v2 = getAdjUnvisitedVertex(v1)) != -1 && v1 == 0) {
                vertexList[v2].wasVisited = true;
                displayVertexName(v2);
                System.out.println();
                queue.insert(v2);
            }
        }
        for (int vertex = 0; vertex < nVerts; vertex++) {
            vertexList[vertex].wasVisited = false;
        }
    }

    public void installedFiberFrom(Gebieden startGebied) {
        int start = findIndexOf(startGebied);
        vertexList[start].wasVisited = true;
        displayVertexName(start);
        System.out.println();
        int adjacentUnvisitedVertex;

        if (getAdjUnvisitedVertex(start) == -1) {
            System.out.println("Geen getrokken glasvezel gevonden!");
        }

        while ((adjacentUnvisitedVertex = getAdjUnvisitedVertex(start)) != -1) {
            vertexList[adjacentUnvisitedVertex].wasVisited = true;
            System.out.print("--> ");
            displayVertexName(adjacentUnvisitedVertex);
            System.out.println();
        }

        for (int vertex = start; vertex < nVerts; vertex++) {
            vertexList[vertex].wasVisited = false;
        }
    }

    public int getAdjUnvisitedVertex(int row) {
        for (int column = 0; column < nVerts; column++) {
            if (adjMat[row][column] != INFINITY && !vertexList[column].wasVisited) {
                return column;
            }
        }
        return -1;
    }

    public void cheapestPath(Gebieden startTree) {
        int start = findIndexOf(startTree);
        System.out.println("Starting from " + vertexList[start].gebieden.getNaam());
        vertexList[start].isInTree = true;
        nTree = 1;

        for (int column = 0; column < nVerts; column++) {
            int tempDist = adjMat[start][column];
            path[column] = new DistPar(start, tempDist);
        }

        while (nTree < nVerts) {
            int indexMin = getMin();
            int minDist = path[indexMin].distance;

            if (minDist == INFINITY) {
                System.out.println("There are unreachable areas!");
                break;
            } else {
                currentVert = indexMin;
                startToCurrent = path[indexMin].distance;
            }
            vertexList[currentVert].isInTree = true;
            nTree++;
            adjust_sPath();
        }
        displayPaths(startTree);
        System.out.println();
        nTree = 0;
        for (int vertex = 0; vertex < nVerts; vertex++)
            vertexList[vertex].isInTree = false;
    }

    public int getMin() {
        int minDist = INFINITY;
        int indexMin = 0;
        for (int vertex = 1; vertex < nVerts; vertex++) {
            if (!vertexList[vertex].isInTree && path[vertex].distance < minDist) {
                minDist = path[vertex].distance;
                indexMin = vertex;
            }
        }
        return indexMin;
    }

    public void adjust_sPath() {
        int column = 1;
        while (column < nVerts) {
            if (vertexList[column].isInTree) {
                column++;
                continue;
            }
            int currentToFringe = adjMat[currentVert][column];
            int startToFringe = startToCurrent + currentToFringe;
            int sPathDist = path[column].distance;
            if (startToFringe < sPathDist) {
                path[column].parentVert = currentVert;
                path[column].distance = startToFringe;
            }
            column++;
        }
    }

    public void adjustAdjMatrixForLongestPath(boolean adjust) {
        if (adjust) {
            for (int row = 0; row < MAX_VERTS; row++) {
                for (int column = 0; column < MAX_VERTS; column++) {
                    if (adjMat[row][column] == INFINITY) {
                        adjMat[row][column] = 0;
                    }
                }
            }
        } else {
            for (int row = 0; row < MAX_VERTS; row++) {
                for (int column = 0; column < MAX_VERTS; column++) {
                    if (adjMat[row][column] == 0) {
                        adjMat[row][column] = INFINITY;
                    }
                }
            }
        }
    }

    public void mostExpensivePath(Gebieden startTree) {
        adjustAdjMatrixForLongestPath(true);
        System.out.println("Starting from " + startTree.getNaam());
        int start = findIndexOf(startTree);
        vertexList[start].isInTree = true;
        nTree = 1;
        for (int vertex = 0; vertex < nVerts; vertex++) {
            int tempDist = adjMat[start][vertex];
            path[vertex] = new DistPar(start, tempDist);
        }
        while (nTree < nVerts) {
            int indexMax = getMax();
            int maxDist = path[indexMax].distance;
            if (maxDist == 0) {
                System.out.println("There are unreachable areas!");
                break;
            } else {
                currentVert = indexMax;
                startToCurrent = path[indexMax].distance;
            }
            vertexList[currentVert].isInTree = true;
            nTree++;
            adjust_lPath();
        }
        displayPaths(startTree);
        nTree = 0;
        for (int vertex = 0; vertex < nVerts; vertex++) {
            vertexList[vertex].isInTree = false;
        }
        adjustAdjMatrixForLongestPath(false);
    }

    public int getMax() {
        int maxDist = 0;
        int indexMax = 0;
        for (int vertex = 1; vertex < nVerts; vertex++) {
            if (!vertexList[vertex].isInTree && path[vertex].distance > maxDist) {
                maxDist = path[vertex].distance;
                indexMax = vertex;
            }
        }
        return indexMax;
    }

    public void adjust_lPath() {
        int column = 1;
        while (column < nVerts) {
            if (vertexList[column].isInTree) {
                column++;
                continue;
            }
            int currentToFringe = adjMat[currentVert][column];
            int startToFringe = startToCurrent + currentToFringe;
            int lPathDist = path[column].distance;
            if (startToFringe > lPathDist && currentToFringe != 0) {
                path[column].parentVert = currentVert;
                path[column].distance = startToFringe;
            }
            column++;
        }
    }

    public void displayPaths(Gebieden startVert) {
        for (int vertex = 0; vertex < nVerts; vertex++) {
            System.out.println();
            System.out.print(startVert.getNaam() + " towards ");
            stack.push(vertex);
            System.out.print(vertexList[vertex].gebieden.getNaam() + " costs ");

            if (path[vertex].distance == INFINITY || path[vertex].distance == 0) {
                System.out.print("inf ");
            } else {
                System.out.print(path[vertex].distance + "K ");
            }
            int parentVert = path[vertex].parentVert;
            int startIndex = findIndexOf(startVert);

            if (startIndex != parentVert) {
                stack.push(parentVert);
            }
            int parentOf = parentVert;
            boolean startingVert = false;

            while (!startingVert) {
                int temp = path[parentOf].parentVert;
                String parentOfParent = vertexList[temp].gebieden.getNaam();
                stack.push(temp);
                parentOf = temp;

                if (parentOfParent.equals(startVert.getNaam()))
                    startingVert = true;
            }
            System.out.print("(Path: ");

            while (!stack.isEmpty()) {
                System.out.print(vertexList[stack.pop()].gebieden.getNaam());

                if (!stack.isEmpty()) {
                    System.out.print("--> ");
                }
            }
            System.out.println(")");
        }
    }

    public void displayAllVertices() {
        int countVertex = 0;
        System.out.println("Displaying all vertices");
        for (int vertex = 0; vertex < nVerts; vertex++) {
            System.out.print(vertexList[vertex].gebieden.getNaam());
            System.out.println();
            countVertex++;
        }
        System.out.println("Aantal gebieden: " + countVertex);
        System.out.println();
    }

    public int findIndexOf(Gebieden gebied) {
        if (vertexList == null) {
            return -1;
        }
        int vertex = 0;

        while (vertex < nVerts) {
            if (vertexList[vertex].gebieden.equals(gebied)) {
                return vertex;
            } else {
                vertex++;
            }
        }
        return -1;
    }
}
