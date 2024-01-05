package com.vrj.coh.mis;

public class AntColony {
    private static int NUM_ANTS;
    private static int SEED;
    private static int NUM_VERTICES;

    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Usage: java AntColony <graph_document> <numAnts> <seed> <numVertices>");
            System.exit(1);
        }

        NUM_ANTS = Integer.parseInt(args[1]);
        SEED = Integer.parseInt(args[2]);
        NUM_VERTICES = Integer.parseInt(args[3]);

        Graph graph = new Graph(args[0]);
    }
}
