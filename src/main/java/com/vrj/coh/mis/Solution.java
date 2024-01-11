package com.vrj.coh.mis;

import java.util.Random;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Solution {
    private static Random random;
    private static double cost = 0;
    private static int numEdges = 0;
    private static int indexOut;
    private static int indexIn;
    private int seed;
    private Graph graph; 
    private int[] indicesVertices;
    private Set<Integer> independentSet;

    public Solution(Graph graph, int[] indicesVertices, Set<Integer> independentSet, int seed) {
        this.graph = graph;
        this.indicesVertices = indicesVertices;
        this.independentSet = independentSet;
        random = new Random(seed);
        numEdges();
        costFunction();
    }

    public void replace(){
        int aux = random.nextInt(indicesVertices.length);
        indexOut = indicesVertices[aux];
        indexIn = random.nextInt(graph.getNumVertex());

        while (independentSet.contains(indexIn) || indexIn == indexOut ) {
            indexIn = random.nextInt(graph.getNumVertex());
            
        }

        indicesVertices[aux] = indexIn;
        independentSet.remove(indexOut);
        independentSet.add(indexIn);
        modifyCost();
    }


    private void costFunction(){
        cost = (graph.getNumEdges() - numEdges)/graph.getNumEdges();
    }

    private void modifyCost(){
        int n = 0;
        int m = 0;
        for(int i = 0; i < indicesVertices.length-1; i++){
            if(graph.getAdjMatrix()[indexOut][indicesVertices[i]] == 1){
                n++;
            }
                
            if(graph.getAdjMatrix()[indexIn][indicesVertices[i]] == 1)
                m++;
        }
        cost -= (n/graph.getNumEdges());
        cost += (m/graph.getNumEdges());
    }

    private void numEdges(){
        int n = 0;
        for(int i = 0; i < indicesVertices.length-1; i++){
            for(int j = 0; j < indicesVertices.length; j++){
                if(this.graph.getAdjMatrix()[i][j] == 1)
                    n++;
            }
        }
        numEdges = n;
    }
}
