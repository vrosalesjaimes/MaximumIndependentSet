
package com.vrj.coh.mis;

import java.util.List;

import lombok.Data;

@Data
public class Graph {
    private int[][] adjMatrix;
    private int numEdges;

    public Graph(String graph6) {
        Graph6Decoder decoder = new Graph6Decoder(graph6);
        this.adjMatrix = decoder.getAdjMatrix();
        this.numEdges = decoder.getNumEdges();
    }

    public String toString(List<Integer> vertexSet){
        StringBuilder dot = new StringBuilder();
        dot.append("graph G {\n");

        for (int i = 0; i < adjMatrix.length; i++) {
            dot.append(String.format("  %d [color=\"%s\"]\n", i, vertexSet.contains(i) ? "blue" : "white"));
        }

        for (int i = 0; i < adjMatrix.length; i++) {
            for (int j = i + 1; j < adjMatrix.length; j++) {
                if (adjMatrix[i][j] == 1) {
                    String edgeColor = (vertexSet.contains(i) && vertexSet.contains(j)) ? "yellow" : "black";
                    dot.append(String.format("  %d -- %d [color=\"%s\"]\n", i, j, edgeColor));
                }
            }
        }

        dot.append("}\n");
        return dot.toString();
    }
}
