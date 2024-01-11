package com.vrj.coh.mis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import lombok.Data;

/**
 * This class represents a Tabu List.
 */
@Data
public class TabuList implements Iterable<TupleTabu>{
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

    public void remove(TupleTabu solution) {
        tabuList.remove(solution);
        tabuSet.remove(solution);
    }

    /**
     * Checks if the Tabu List contains a solution.
     * @param solution The solution to check.
     * @return True if the solution is in the Tabu List, false otherwise.
     */
    public boolean contains(TupleTabu solution) {
        return tabuSet.contains(solution);
    }

    @Override
    public Iterator<TupleTabu> iterator() {
        return tabuList.iterator();
    }

    public void sort() {
        List<TupleTabu> listTabu = new ArrayList<>(tabuList);
        Collections.sort(listTabu);
        Queue<TupleTabu> sortList = new LinkedList<>(listTabu);
        this.tabuList = sortList;
    }
}