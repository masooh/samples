package com.github.masooh.samples.jacoco;

public class Calc {
    public int plus(int a, int b) {
        return a + b;
    }

    public int minus(int a, int b) {
        if (b > a) {
            throw new UnsupportedOperationException("cant handle negative values");
        }
        return a - b;
    }
}
