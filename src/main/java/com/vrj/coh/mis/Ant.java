package com.vrj.coh.mis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Ant {
    private TabuList memory;
    private double pheromone;

    public double pheromoneQuantity(int r, int s){
        return 0;
    }
}
