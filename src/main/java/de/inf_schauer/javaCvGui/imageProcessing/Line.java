package de.inf_schauer.javaCvGui.imageProcessing;

import de.inf_schauer.javaCvGui.interfaces.I_CalcOps;

public class Line<T extends Number> {

    private T a;
    private T b;
    private I_CalcOps<T> calcOps;

    public Line(T a, T b, I_CalcOps<T> calcOps) {
        super();
        this.a = a;
        this.b = b;
        this.calcOps = calcOps;
    }

    public T getY(T x) {
        return calcOps.add(calcOps.multiply(a, x), b);
    }

    public T getA() {
        return a;
    }

    public void setA(T a) {
        this.a = a;
    }

    public T getB() {
        return b;
    }

    public void setB(T b) {
        this.b = b;
    }

    public T getSlope() {
        return this.a;
    }

}
