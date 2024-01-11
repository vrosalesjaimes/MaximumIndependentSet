package com.vrj.coh.mis;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TupleTabu implements Comparable<TupleTabu> {
    private String set;
    private double pheromone;
    private double cost;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        TupleTabu otherTuple = (TupleTabu) obj;

        return this.set.equals(otherTuple.set);
    }

    @Override
    public int hashCode() {
        return Objects.hash(set);
    }

    public int[] toArray(){
        String str = set.replaceAll("\\[|\\]|\\s", "");
        String[] tokens = str.split(",");

        int[]  vertex = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            vertex[i] = Integer.parseInt(tokens[i]);
        }

        return vertex;
    }

    @Override
    public int compareTo(TupleTabu other) {
        return Double.compare(other.cost, this.cost);
    }

}
