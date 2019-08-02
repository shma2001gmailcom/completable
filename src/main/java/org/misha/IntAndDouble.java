package org.misha;

import java.util.Random;
import java.util.UUID;

public class IntAndDouble implements Modifiable<Double, Integer> {
    private volatile Double doubleProperty;
    private Integer intProperty;
    private final String id;

    IntAndDouble(Double doubleProperty) {
        this.doubleProperty = doubleProperty;
        this.id = UUID.randomUUID().toString();
        this.intProperty = new Random().nextInt();
    }

    @Override
    public Double getDoubleProperty() {
        return doubleProperty;
    }

    @Override
    public void modifyUsing(Integer intProperty) {
        for (int i = 0; i < (intProperty < 0 ? -10000 * intProperty : 10000 * intProperty); ++i) {
            System.err.println("modify");
            doubleProperty = doubleProperty + new Random().nextDouble();
        }
    }

    @Override
    public String toString() {
        return "IntAndDouble{" + "doubleProperty=" + doubleProperty + ", intProperty=" + intProperty + ", id='" + id + '\'' +
               '}';
    }

    public void setDoubleProperty(Double doubleProperty) {
        this.doubleProperty = doubleProperty;
    }
}
