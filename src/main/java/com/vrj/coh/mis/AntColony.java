package com.vrj.coh.mis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;


public class AntColony {
    private static int NUM_ANTS;
    private static int SEED;
    private static int NUM_VERTICES;
    private static TabuList finalRoute = new TabuList(1000000);
    private static Graph graph;
    private static Random random;
    private static int NUM_ITERATIONS = 500000;
    private static int NUM_REPEATS = 10;
    private static Ant[] colony;

    public static void getColony() {
        Ant[] colonyArray = new Ant[NUM_ANTS];

        for (int i = 0; i < NUM_ANTS; i++) {
            colonyArray[i] = new Ant(NUM_ITERATIONS / NUM_ANTS);
        }

        colony = colonyArray;
    }

    public static void antRoute(Ant ant, int seed, int iterations, Integer[] initialSet) {
        Solution solution = new Solution(graph, initialSet, seed);
        for (int i = 0; i < iterations; i++) {
            solution.neighbor();
            String sol = Arrays.toString(solution.getIndicesVertices());

            TupleTabu tuple = new TupleTabu(sol, solution.getCost(), solution.getCost());

            if (!ant.getMemory().contains(tuple)) {
                ant.getMemory().add(tuple);
            }else{
                i--;
            }
        }

    }

    public static Integer[] initialSet(int seed) {
        Random random = new Random(seed);
        Set<Integer> set = new HashSet<>(NUM_VERTICES);

        while(set.size() < NUM_VERTICES) {
            set.add(random.nextInt(NUM_VERTICES));
        }
        return set.toArray(new Integer[0]);
    }

    public static void updatePheromone() {
        Iterator<TupleTabu> iterator = finalRoute.iterator();

        while (iterator.hasNext()) {
            TupleTabu tuple = iterator.next();

            if (tuple.getPheromone() < 0.99) {
                tuple.setPheromone(tuple.getPheromone()*(99/100));
            } else {
                tuple.setPheromone(Math.sqrt(tuple.getPheromone()));
            }
        }
    }

    public static void updateFinalRoute() {
        for (Ant ant : colony) {
            for (TupleTabu tuple : ant.getMemory()) {
                if (!finalRoute.contains(tuple)) {
                    if (tuple.getPheromone() >= .9) {
                        finalRoute.add(tuple);
                    }
                }
            }
        }
    }

    public static void optimizationAntColony() {
        for (int i = 0; i < NUM_REPEATS; i++) {
            for (Ant ant : colony) {
                int seed = random.nextInt();
                Integer[] initialSet = initialSet(seed);
                antRoute(ant, seed, NUM_ITERATIONS / NUM_ANTS, initialSet);
            }
            updateFinalRoute();
            updatePheromone();
        }
    }

    private static Graph readFile(String filePath) throws IOException {
        try  {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            int numVertices = 0;
            int numAristas = 0;
    
            while ((line = br.readLine()) != null) {
                numVertices++;
            }
    
            int[][] matrix = new int[numVertices][numVertices];
            
            br.close();
            br = new BufferedReader(new FileReader(filePath));
            
            int i = 0;
    
            while ((line = br.readLine()) != null) {
                String[] values = line.split(" ");
                for (int j = 0; j < values.length; j++) {
                    matrix[i][j] = Integer.parseInt(values[j]);
                    numAristas += Integer.parseInt(values[j]);
                }
                i++;
            }
            
            return new Graph(matrix, numVertices, numAristas / 2); 
    
        } catch (IOException e) {
            e.printStackTrace();
            return null;
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
        graph = readFile(args[0]);
        random = new Random(SEED);
        getColony();

        optimizationAntColony();

        finalRoute.sort();

        if (finalRoute.getTabuList().isEmpty()) {
            System.out.println("No solution found");
            System.exit(1);
        }

        TupleTabu bestSolution = finalRoute.getTabuList().peek();

        System.out.println((bestSolution.getCost() == 1) + ", " + bestSolution.getCost());
    }
}
