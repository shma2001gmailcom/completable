package org.misha;

import java.util.List;

import static java.lang.Math.floor;

public class JoinNoForkImpl extends JoinNoFork<Double, Integer, IntAndDouble> {
    public JoinNoForkImpl(List<IntAndDouble> entities) {
        super(entities);
    }

    @Override
    Integer by(Double aDouble, IntAndDouble e) {
        while (aDouble > 0.0) {
            synchronized (this) {
                if (aDouble > 0.0) {
                    System.err.println("\n" + e + ": " + aDouble);
                    aDouble = aDouble - 1;
                    e.setDoubleProperty(aDouble);
                    System.err.println(e + ": " + aDouble);
                }
            }
        }
        return (int) floor(aDouble);
    }
}
