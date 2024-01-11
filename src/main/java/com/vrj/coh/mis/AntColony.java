package com.vrj.coh.mis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

public class AntColony {
    private static int NUM_ANTS;
    private static int SEED;
    private static int NUM_VERTICES;
    private static TabuList finalRoute = new TabuList(100000);
    private static Graph graph;
    private static Random random = new Random(SEED);
    private static int NUM_ITERATIONS = 100000;
    private static int NUM_REPEATS = 5;
    private static Ant[] colony;

    public static void getColony() {
        Ant[] colonyArray = new Ant[NUM_ANTS];

        for (int i = 0; i < NUM_ANTS; i++) {
            colonyArray[i] = new Ant(NUM_ITERATIONS / NUM_ANTS);
        }

        colony = colonyArray;
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
        Iterator<TupleTabu> iterator = finalRoute.iterator();

        while (iterator.hasNext()) {
            TupleTabu tuple = iterator.next();

            if (tuple.getPheromone() < 0.99) {
                tuple.setPheromone(tuple.getPheromone() * (9 / 10));
            }

            if (tuple.getPheromone() < 0.5) {
                iterator.remove(); // Usa el iterador para eliminar el elemento
            }
        }
    }

    public static void updateFinalRoute() {
        for (Ant ant : colony) {
            for (TupleTabu tuple : ant.getMemory()) {
                if (!finalRoute.contains(tuple)) {
                    if (tuple.getPheromone() > .5) {
                        finalRoute.add(tuple);
                    }
                }
            }
        }
    }

    public static void optimizationAntColony() {
        int seed = random.nextInt();

        for (int i = 0; i < NUM_REPEATS; i++) {
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

        getColony();

        int[][] adjacencyMatrix = {
                { 0, 1, 0, 0, 0, 1, 0, 0, 0, 1 },
                { 1, 0, 1, 0, 0, 0, 1, 0, 0, 0 },
                { 0, 1, 0, 1, 0, 0, 0, 1, 0, 0 },
                { 0, 0, 1, 0, 1, 0, 0, 0, 1, 0 },
                { 0, 0, 0, 1, 0, 0, 0, 0, 0, 1 },
                { 1, 0, 0, 0, 0, 0, 1, 0, 0, 0 },
                { 0, 1, 0, 0, 0, 1, 0, 1, 0, 0 },
                { 0, 0, 1, 0, 0, 0, 1, 0, 1, 0 },
                { 0, 0, 0, 1, 0, 0, 0, 1, 0, 1 },
                { 1, 0, 0, 0, 1, 0, 0, 0, 1, 0 }
        };
        graph = new Graph(adjacencyMatrix, 10, 15);

        optimizationAntColony();

        finalRoute.sort();

        if (finalRoute.getTabuList().isEmpty()) {
            System.out.println("No solution found");
            System.exit(1);
        }

        TupleTabu bestSolution = finalRoute.getTabuList().peek();

        System.out.println(bestSolution.getCost() == 1);
    }
}
