package com.colorpalettegenerator.model;

import org.apache.commons.math3.ml.clustering.Clusterable;

public class Pixel implements Clusterable {

    private final double[] point;
    private final int[] values;

    public Pixel(int[] values) {
        this.point = new double[]{values[0], values[1], values[2]};
        this.values = values;
    }

    @Override
    public double[] getPoint() {
        return point;
    }

    public int getRed() {
        return values[0];
    }

    public int getGreen() {
        return values[1];
    }

    public int getBlue() {
        return values[2];
    }
    @Override
    public String toString() {
        return "Pixel{" +
                "red=" + values[0] +
                ", green=" + values[1] +
                ", blue=" + values[2] +
                '}';
    }
}
