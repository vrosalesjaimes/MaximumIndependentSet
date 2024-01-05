package com.vrj.coh.mis;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Graph6Decoder {
    private String graph6;
    private int vertices;
    private String bits;
    private int[][] adjMatrix;
    private int numEdges;

    public Graph6Decoder(String graph6) {
        this.graph6 = graph6;
        this.vertices = decodeVertices();
        this.bits = decodeBits();
        this.adjMatrix = decodeAdjMatrix();
        numEdges();
    }

    private int decodeVertices() {
        if (graph6.charAt(0) == '~') {
            vertices = 0;
            int actual;
            for (int i = 1; i < 4; i++) {
                actual = (int) graph6.charAt(i) - 63;
                actual <<= (3 - i) * 6;
                vertices += actual;
            }
        } else {
            vertices = graph6.charAt(0) - 63;
        }
        return vertices;
    }

    private String decodeBits() {
        int start = vertices > 62 ? 4 : 1;
        StringBuilder bitsBuilder = new StringBuilder();
        for (int i = start; i < graph6.length(); i++) {
            bitsBuilder.append(String.format("%6s", Integer.toBinaryString(graph6.charAt(i) - 63)).replace(' ', '0'));
        }
        bits = bitsBuilder.toString();
        return bits;
    }

    private int[][] decodeAdjMatrix() {
        int index = 0;
        adjMatrix = new int[vertices][vertices];
        for (int j = 1; j < vertices; j++) {
            for (int i = 0; i < j; i++) {
                int bit = bits.charAt(index) - '0';
                adjMatrix[i][j] = bit;
                adjMatrix[j][i] = bit;
                index++;
            }
        }
        return adjMatrix;
    }

    public void numEdges(){
        for(int i = 0; i < vertices; i++){
            for(int j = i+1; j < vertices; j++){
                if (this.adjMatrix[i][j] == 1) {
                    this.numEdges++;
                }
            }
        }
    }
}
