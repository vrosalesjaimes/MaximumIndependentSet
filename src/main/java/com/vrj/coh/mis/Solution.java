package com.vrj.coh.mis;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Solution {
    private static Random random;
    private double cost = 0;
    private int numEdges = 0;
    private static int indexOut;
    private static int indexIn;
    private int seed;
    private Graph graph; 
    private Integer[] indicesVertices;
    private Set<Integer> independentSet;

    public Solution(Graph graph, Integer[] indicesVertices, int seed) {
        this.graph = graph;
        this.indicesVertices = indicesVertices;
        this.independentSet = arrayToSet(indicesVertices);
        random = new Random(seed);
        numEdges();
        costFunction();
    }

    public void neighbor(){
        int aux = random.nextInt(indicesVertices.length);
        indexOut = indicesVertices[aux];
        indexIn = random.nextInt(graph.getNumVertex());

        while (independentSet.contains(indexIn) || indexIn == indexOut ) {
            indexIn = random.nextInt(graph.getNumVertex());
            
        }

        indicesVertices[aux] = indexIn;
        independentSet.remove(indexOut);
        independentSet.add(indexIn);
        numEdges();
        costFunction();
    }


    private void costFunction(){
        double numerador = graph.getNumEdges() - numEdges;
        cost = numerador/graph.getNumEdges();
    }

    private void numEdges(){
        int n = 0;
        for(int i = 0; i < indicesVertices.length; i++){
            for(int j = 0; j < indicesVertices.length; j++){
                if(this.graph.getAdjMatrix()[indicesVertices[i]][indicesVertices[j]] == 1)
                    n++;
            }
        }
        numEdges = n;
    }

    private Set<Integer> arrayToSet(Integer[] array){
        Set<Integer> set = new HashSet<Integer>();
        for(int i = 0; i < array.length; i++){
            set.add(array[i]);
        }
        return set;
    }
}
