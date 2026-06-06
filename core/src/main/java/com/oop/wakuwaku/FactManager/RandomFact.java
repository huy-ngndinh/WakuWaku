package com.oop.wakuwaku.FactManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomFact {
    private final List<Integer> facts = new ArrayList<>();

    public RandomFact() {
        // Add fact and suffle
        for (int i = 0; i <= 5; i++) {facts.add(i);}
            Collections.shuffle(facts);
    }
    public int getRandomFact() {

            // Check
            if (facts.isEmpty()) {return 99;}

            // get Fact and remove it
            return facts.remove(0);
    }

}

