package com.vrj.coh.mis;

import java.util.HashMap;
import java.util.Map;
import java.util.LinkedList;
import java.util.Queue;

import lombok.Data;

/**
 * This class represents a Tabu List.
 */
@Data
public class TabuList {
    private Queue<String> tabuList;
    private Map<String, Double> tabuSet;
    private int sizeTabuList;

    /**
     * Constructs a TabuList with the specified size.
     * @param sizeTabuList The size of the Tabu List.
     */
    public TabuList(int sizeTabuList) {
        this.sizeTabuList = sizeTabuList;
        this.tabuList = new LinkedList<>();
        this.tabuSet = new HashMap<>();
    }

    /**
     * Gets the size of the Tabu List.
     * @return The size of the Tabu List.
     */
    public int getSizeTabuList() {
        return sizeTabuList;
    }

    /**
     * Sets the size of the Tabu List.
     * @param sizeTabuList The size of the Tabu List to set.
     */
    public void setSizeTabuList(int sizeTabuList) {
        this.sizeTabuList = sizeTabuList;
    }

    /**
     * Adds a solution to the Tabu List.
     * @param solution The solution to add.
     */
    public void add(String solution, Double feromone) {
        if (tabuList.size() == sizeTabuList) {
            String removed = tabuList.remove();
            tabuSet.remove(removed);
        }
        tabuList.add(solution);
        tabuSet.put(solution, feromone);
    }

    /**
     * Checks if the Tabu List contains a solution.
     * @param solution The solution to check.
     * @return True if the solution is in the Tabu List, false otherwise.
     */
    public boolean contains(String solution) {
        return tabuSet.containsKey(solution);
    }
}