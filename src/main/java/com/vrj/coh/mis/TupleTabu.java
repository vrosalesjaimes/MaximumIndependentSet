package com.vrj.coh.mis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TupleTabu {
    private String set;
    private double pheromone;

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

}
