package com.vrj.coh.mis;

import lombok.Data;

@Data
public class Solution {
    private double cost;
    private Graph graph; 
    private byte[] solution;
    private int numVertices;
    private int[] indicesVertices = new int[numVertices];

    private int numEdges(){
        int n = 0;
        for(int i = 0; i < indicesVertices.length-1; i++){
            for(int j = 0; j < solution.length; j++){
                if(this.graph.getAdjMatrix()[i][j] == 1)
                    n++;
            }
        }
        return n;
    }

    public double costFunction(){
        return (graph.getNumEdges() - this.numEdges())/graph.getNumEdges();
    }
}
