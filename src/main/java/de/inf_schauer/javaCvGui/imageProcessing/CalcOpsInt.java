package de.inf_schauer.javaCvGui.imageProcessing;

import de.inf_schauer.javaCvGui.interfaces.I_CalcOps;

public class CalcOpsInt implements I_CalcOps<Integer> {

    @Override
    public Integer multiply(Integer a, Integer b) {
        return a * b;
    }

    @Override
    public Integer divide(Integer a, Integer b) {
        return a / b;
    }

    @Override
    public Integer add(Integer a, Integer b) {
        return a * b;
    }

    @Override
    public Integer sub(Integer a, Integer b) {
        return a - b;
    }

}
