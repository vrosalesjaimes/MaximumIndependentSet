
package com.vrj.coh.mis;

import lombok.Data;

@Data
public class Graph {
    private int[][] adjMatrix;
    private int numEdges;

    public Graph(String graph6){
        Graph6Decoder decoder = new Graph6Decoder(graph6);
        this.adjMatrix = decoder.getAdjMatrix();
        this.numEdges = decoder.getNumEdges();
    }

    public Graph subGraph(int[][] bitMap){
        return null;
    }
}
