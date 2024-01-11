package com.vrj.coh.mis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Ant {
    private TabuList memory;
    
    
    public Ant(int sizeTabuList) {
        this.memory = new TabuList(sizeTabuList);
    }
}
