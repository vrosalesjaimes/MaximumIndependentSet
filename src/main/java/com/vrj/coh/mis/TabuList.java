package com.vrj.coh.mis;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import lombok.Data;

/**
 * This class represents a Tabu List.
 */
@Data
public class TabuList {
    private Queue<TupleTabu> tabuList;
    private Set<TupleTabu> tabuSet;
    private int sizeTabuList;

    /**
     * Constructs a TabuList with the specified size.
     * @param sizeTabuList The size of the Tabu List.
     */
    public TabuList(int sizeTabuList) {
        this.sizeTabuList = sizeTabuList;
        this.tabuList = new LinkedList<>();
        this.tabuSet = new HashSet<>();
    }

    /**
     * Adds a solution to the Tabu List.
     * @param solution The solution to add.
     */
    public void add(TupleTabu solution) {
        if (tabuList.size() == sizeTabuList) {
            TupleTabu removed = tabuList.remove();
            tabuSet.remove(removed);
        }
        tabuList.add(solution);
        tabuSet.add(solution);
    }

    /**
     * Checks if the Tabu List contains a solution.
     * @param solution The solution to check.
     * @return True if the solution is in the Tabu List, false otherwise.
     */
    public boolean contains(TupleTabu solution) {
        return tabuSet.contains(solution);
    }
}