package app;

import entities.Gebieden;
import services.Graph;

public class GlasvezelApp {

    public static void main(String[] args) {
        Graph graph = new Graph();
        Gebieden rainville = new Gebieden("Rainville", 3606, 58, true);
        Gebieden munder = new Gebieden("Munder", 2674, 43, true);
        Gebieden wegNaarZee = new Gebieden("Weg naar zee", 2196, 36, false);
        Gebieden blauwgrond = new Gebieden("Blauwgrond", 5247, 84, true);
        Gebieden centrum = new Gebieden("Centrum", 4879, 40, true);
        Gebieden tammenga = new Gebieden("Tammenga", 2385, 39, true);
        Gebieden welgelegen = new Gebieden("Welgelegen", 3951, 63, false);
        Gebieden flora = new Gebieden("Flora", 2716, 43, true);
        Gebieden pontbuiten = new Gebieden("Pontbuiten", 2434, 39, false);
        Gebieden beekhuizen = new Gebieden("Beekhuizen", 3297, 53, true);
        Gebieden livorno = new Gebieden("Livorno", 1089, 18, false);
        Gebieden latour = new Gebieden("Latour", 4358, 70, true);
        Gebieden koewarasan = new Gebieden("Koewarasan", 4618, 74, true);

        graph.addVertex(rainville); // 0
        graph.addVertex(munder); // 1
        graph.addVertex(wegNaarZee); // 2
        graph.addVertex(blauwgrond); // 3
        graph.addVertex(centrum); // 4
        graph.addVertex(welgelegen); // 5
        graph.addVertex(flora); // 6
        graph.addVertex(tammenga); // 7
        graph.addVertex(beekhuizen); // 8
        graph.addVertex(pontbuiten); // 9
        graph.addVertex(livorno); // 10
        graph.addVertex(latour); // 11
        graph.addVertex(koewarasan); // 12

        graph.addEdge(rainville, munder, 78);
        graph.addEdge(rainville, blauwgrond, 10);
        graph.addEdge(rainville, centrum, 30);

        graph.addEdge(munder, wegNaarZee, 91);
        graph.addEdge(munder, welgelegen, 52);

        graph.addEdge(blauwgrond, flora, 7);
        graph.addEdge(blauwgrond, beekhuizen, 20);

        graph.addEdge(centrum, munder, 49);
        graph.addEdge(centrum, tammenga, 25);

        graph.addEdge(welgelegen, wegNaarZee, 46);

        graph.addEdge(flora, beekhuizen, 12);

        graph.addEdge(tammenga, welgelegen, 30);
        graph.addEdge(tammenga, pontbuiten, 81);

        graph.addEdge(beekhuizen, livorno, 8);

        graph.addEdge(pontbuiten, koewarasan, 102);

        graph.addEdge(livorno, latour, 95);

        graph.addEdge(latour, koewarasan, 34);

        //Depth First Search
        System.out.println("Gebieden op het glasvezelnetwerk:");
        graph.dfs();
        System.out.println();

        //Fiber To The Curb
        graph.FTTC();
        System.out.println();

        //Fiber To The Home
        graph.FTTH();
        System.out.println();

        graph.displayAllVertices();
        System.out.println();


        //Breath First Search
        System.out.print("Trekken van glasvezel vanuit gebied: ");
        graph.installedFiberFrom(blauwgrond);
        System.out.println();

        //Dijkstra Shortest Path
        System.out.println("Cheapest path");
        graph.cheapestPath(rainville);
        System.out.println();

        //Dijkstra Longest Path
        System.out.println("Most expensive path");
        graph.mostExpensivePath(rainville);
        System.out.println();
    }
}