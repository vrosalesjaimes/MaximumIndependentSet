
package com.vrj.coh.mis;

import java.util.Set;

import lombok.Data;

@Data
public class Graph {
    private int[][] adjMatrix;
    private int numEdges;
    private int numVertex;

    public Graph(String graph6) {
        Graph6Decoder decoder = new Graph6Decoder(graph6);
        this.numVertex = decoder.getVertices();
        this.adjMatrix = decoder.getAdjMatrix();
        this.numEdges = decoder.getNumEdges();
    }

    public Graph(int[][] adjMatrix, int numVertex, int numEdges) {
        this.adjMatrix = adjMatrix;
        this.numVertex = numVertex;
        this.numEdges = numEdges;
    }

    public String toString(Set<Integer> vertexSet){
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
