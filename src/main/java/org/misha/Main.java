package org.misha;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String... args) {
        List<IntAndDouble> cars = new ArrayList<>();
        for (int i = 0; i < 5; ++i) {
            cars.add(new IntAndDouble(10.0 + new Random().nextDouble()));
        }
        JoinNoForkImpl j = new JoinNoForkImpl(cars);
        j.main();
    }
}
