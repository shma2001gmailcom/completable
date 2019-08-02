package org.misha;

public interface Modifiable<Q1, Q2> {

    Q1 getDoubleProperty();

    void modifyUsing(Q2 propertyTwo);

}
