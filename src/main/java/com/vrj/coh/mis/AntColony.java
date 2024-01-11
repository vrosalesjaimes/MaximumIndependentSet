package com.vrj.coh.mis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class AntColony {
    private static int NUM_ANTS;
    private static int SEED;
    private static int NUM_VERTICES;
    private static TabuList finalRoute = new TabuList(1000);
    private static Graph graph;
    private static Random random = new Random(SEED);
    private static int NUM_ITERATIONS = 100000;
    private static int NUM_REPEATS = 10;
    private static Ant[] colony = getColony();

    public static Ant[] getColony() {
        Ant[] colony = new Ant[NUM_ANTS];

        for (int i = 0; i < NUM_ANTS; i++) {
            colony[i] = new Ant(NUM_ITERATIONS / NUM_ANTS);
        }

        return colony;
    }

    public static void antRoute(Ant ant, int seed, int iterations, int[] initialSet) {
        Solution solution = new Solution(graph, initialSet, seed);

        for (int i = 0; i < iterations; i++) {
            solution.neighbor();

            String sol = Arrays.toString(solution.getIndicesVertices());

            TupleTabu tuple = new TupleTabu(sol, solution.getCost(), solution.getCost());

            if (!ant.getMemory().contains(tuple)) {
                ant.getMemory().add(tuple);
            }
        }

    }

    public static int[] initialSet(int seed) {
        int[] initialSet = new int[NUM_VERTICES];
        Random random = new Random(seed);

        for (int i = 0; i < NUM_VERTICES; i++) {
            initialSet[i] = random.nextInt(NUM_VERTICES);
        }
        return initialSet;
    }

    public static void updatePheromone() {
        for (TupleTabu tuple : finalRoute) {
            if (tuple.getPheromone() < .99) {
                tuple.setPheromone(tuple.getPheromone() / 2);
            }

            if (tuple.getPheromone() < .8) {
                finalRoute.remove(tuple);
            }
        }
    }

    public static void updateFinalRoute() {
        for (Ant ant : colony) {
            for (TupleTabu tuple : ant.getMemory()) {
                if (!finalRoute.contains(tuple)) {
                    if (tuple.getPheromone() > .8) {
                        finalRoute.add(tuple);
                    }
                }
            }
        }
    }

    public static void optimizationAntColony() {
        for (int i = 0; i < NUM_REPEATS; i++) {
            int seed = random.nextInt();
            for (Ant ant : colony) {
                int[] initialSet = initialSet(seed);
                antRoute(ant, seed, NUM_ITERATIONS / NUM_ANTS, initialSet);
            }
            updateFinalRoute();
            updatePheromone();
        }
    }

    private static String readFile(String nameFile) throws IOException {
        File file = new File(nameFile);

        if (!file.exists()) {
            throw new IOException("File not found");
        }

        try (FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            return stringBuilder.toString();
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 4) {
            System.out.println("Usage: java -jar target/*.jar <graph_document> <numAnts> <seed> <numVertices>");
            System.exit(1);
        }

        NUM_ANTS = Integer.parseInt(args[1]);
        SEED = Integer.parseInt(args[2]);
        NUM_VERTICES = Integer.parseInt(args[3]);
        String graphFile = readFile(args[0]);

        graph = new Graph(graphFile);

        optimizationAntColony();

        finalRoute.sort();

        TupleTabu bestSolution = finalRoute.getTabuList().peek();

        System.out.println(bestSolution.getCost() == 1);
    }
}
